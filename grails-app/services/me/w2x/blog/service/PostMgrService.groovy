package me.w2x.blog.service

import me.w2x.blog.domain.Category
import grails.transaction.Transactional
import me.w2x.blog.domain.Post
import me.w2x.blog.bean.PostFilter
import me.w2x.blog.command.PostCommand
import org.joda.time.DateTime

@Transactional
class PostMgrService {

    def baseService

    def serviceMethod() {

    }

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
        Post.listOrderByDateCreated([max: count])
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
            excerpt = command.title
            raw = command.raw
            content = command.content
            status = command.postStatus as Integer
            commentCount = 0
            category = Category.get(command.categoryId)
        }
        post.save()
    }

    def update(PostCommand command) {
        def category = Category.get(command.categoryId);
        def post = Post.get(command.id)
        post.with {
            title = command.title
            excerpt = command.title
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
}