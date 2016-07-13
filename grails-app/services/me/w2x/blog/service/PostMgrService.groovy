package me.w2x.blog.service

import grails.transaction.Transactional
import me.w2x.blog.bean.PostFilter
import me.w2x.blog.command.DraftCommand
import me.w2x.blog.command.PostCommand
import me.w2x.blog.domain.Category
import me.w2x.blog.domain.Draft
import me.w2x.blog.domain.Post
import me.w2x.blog.enu.ActionTypes
import me.w2x.blog.enu.PostStatus
import org.apache.commons.lang3.StringUtils
import org.joda.time.DateTime

@Transactional
class PostMgrService {

    BaseService baseService

    StaticService staticService

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
                DateTime end = new DateTime(filter.year, filter.month ?: 12, start.dayOfMonth().maximumValue, 23, 59, 59)
                between('dateCreated', start.toDate(), end.toDate())
            }

            ne('isDeleted', true)

            if (isCount) {
                projections {
                    count('id')
                }
            } else {
                maxResults filter.pageSize
                firstResult(((filter.page - 1) < 0 ? 0 : filter.page - 1) * filter.pageSize)
                order('lastUpdated', 'desc')
            }
        }
    }

    def getRecentPosts(Integer count) {
        Post.withCriteria {
            'eq'('status', PostStatus.PUBLISH.key)
            'ne'('isDeleted', true)
            maxResults count
            order('dateCreated', 'desc')
        }
    }

    def getArchives() {
        Post.executeQuery("""
            select
            max(p.dateCreated) as dateCreated,
            count(p.id) as count
            from Post p
            where p.status = ${PostStatus.PUBLISH.key} and p.isDeleted = false
            group by concat(year(p.dateCreated),'-',month(p.dateCreated))
            order by concat(year(p.dateCreated),'-',month(p.dateCreated)) desc
""")
    }

    def getPosts(String where, int page, int pageSize, String sort, String sortOrder) {
        def queryHql = 'from Post as p left join fetch p.category where p.isDeleted = false'
        def countHql = 'select count(*) from Post where isDeleted = false'
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
        staticService.triggerPostEvent(post, ActionTypes.ADD)
        post
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
        staticService.triggerPostEvent(post, ActionTypes.EDIT)
        post
    }

    def delete(Post post) {
        post.with {
            isDeleted = true
            save()
        }

        staticService.triggerPostEvent(post, ActionTypes.DELETE)
        post
    }

    def saveOrUpdateDraft(DraftCommand command) {

        def draft = null

        if (command.id) {
            draft = Draft.get(command.id)
        }

        if (!draft) {
            if (command.postId) {
                draft = Draft.findByPost(Post.findByIdAndIsDeleted(command.postId, false))
            }
        }

        draft = draft ?: new Draft()
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
