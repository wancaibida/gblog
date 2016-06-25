package me.w2x.blog.service

import grails.transaction.Transactional
import me.w2x.blog.bean.PostFilter
import me.w2x.blog.command.DraftCommand
import me.w2x.blog.command.PostCommand
import me.w2x.blog.domain.Category
import me.w2x.blog.domain.Draft
import me.w2x.blog.domain.Post
import me.w2x.blog.enu.PostStatus
import org.apache.commons.lang3.StringUtils
import org.joda.time.DateTime

@Transactional
class PostMgrService {

    BaseService baseService

    def getPosts(PostFilter filter) {
        def countResult = getPosts(filter, true)
        def total = countResult[0] as Long
        if (total == 0L) {
            return [total, Collections.emptyList(), 0]
        }

        def listResult = getPosts(filter, false)
        def pageCount = (total / filter.pageSize).intValue() + (total % filter.pageSize == 0L ? 0 : 1)

        [total, listResult, pageCount]
    }

    def getPosts(PostFilter filter, boolean isCount) {
        Post.createCriteria().list {
            eq('status', PostStatus.PUBLISH.key)

            if (filter.category) {
                eq('category', filter.category)
            }

            if (filter.year) {
                DateTime start = new DateTime(filter.year, filter.month ?: 1, 1, 0, 0, 0)
                DateTime end = new DateTime(filter.year, filter.month ?: 12, start.dayOfMonth().minimumValue, 23, 59, 59)
                between('dateCreated', start.toDate(), end.toDate())
            }

            if (isCount) {
                projections {
                    count('id')
                }
            } else {
                maxResults filter.pageSize
                firstResult(((filter.page - 1) < 0 ? 0 : filter.page - 1) * filter.pageSize)
                order('dateCreated', 'desc')
            }
        }
    }

    def getRecentPosts(Integer count) {
        Post.withCriteria {
            'eq'('status', PostStatus.PUBLISH.key)
            maxResults count
            order('dateCreated', 'desc')
        }
    }

    def getArchives() {
        Post.executeQuery('''
            select
            max(p.dateCreated) as dateCreated,
            count(p.id) as count
            from Post p
            group by concat(year(p.dateCreated),'-',month(p.dateCreated))
            order by concat(year(p.dateCreated),'-',month(p.dateCreated)) desc
''')
    }

    def getPosts(String where, int page, int pageSize, String sort, String sortOrder) {
        def queryHql = 'from Post as p left join fetch p.category'
        def countHql = 'select count(*) from Post'
        baseService.getGridData(countHql, queryHql, where, page, pageSize, "p.${sort}", sortOrder)
    }

    def add(PostCommand command) {
        def post = new Post()
        post.with {
            title = command.title
            excerpt = command.excerpt ?: getExcerpt(command.content)
            raw = command.raw
            content = command.content
            status = command.postStatus as Integer
            commentCount = 0
            category = Category.get(command.categoryId)
        }
        post.save()
    }

    def update(PostCommand command) {
        def category = Category.get(command.categoryId)
        def post = Post.get(command.id)
        post.with {
            title = command.title
            excerpt = command.excerpt ?: getExcerpt(command.content)
            raw = command.raw
            content = command.content
            status = command.postStatus as Integer
            category = Category.get(command.categoryId)
        }
        post.save()
    }

    def delete(Post post) {
        post.delete()
    }

    def saveOrUpdateDraft(DraftCommand command) {

        def draft = null

        if (command.id) {
            draft = Draft.get(command.id)
        }

        if (!draft) {
            if (command.postId) {
                draft = Draft.findByPost(Post.get(command.postId))
            }
        }

        if (!draft) {
            draft = new Draft()
        }
        draft.with {
            post = command.postId ? Post.get(command.postId) : null
            title = command.title ?: 'unamed'
            raw = command.raw
            content = command.content
            excerpt = command.excerpt
            category = Category.get(command.categoryId)
        }
        draft.save()
    }

    def getExcerpt(String content) {
        StringUtils
                .substring(content.replaceAll('<.*?>', '').replaceAll(' ', '').replaceAll('<.*?', ''), 0, 200)
    }
}
