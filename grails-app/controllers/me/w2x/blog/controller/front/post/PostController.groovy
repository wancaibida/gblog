package me.w2x.blog.controller.front.post

import me.w2x.blog.bean.PostFilter
import me.w2x.blog.controller.common.BaseController
import me.w2x.blog.domain.Category
import me.w2x.blog.domain.Post
import me.w2x.blog.jobs.UploadBackupJob

import javax.servlet.http.HttpServletResponse

class PostController extends BaseController {
    static layout = 'postLayout'

    def postMgrService

    def categoryMgrService

    def index() {
        def (total, posts, pageCount) = postMgrService.getPosts(searchFilter)
        render(view: '/front/post/postList',
                model: [
                        total      : total,
                        posts      : posts,
                        pageCount  : pageCount,
                        recentPosts: recentPosts,
                        categorys  : categorys,
                        archives   : archives
                ])
    }

    def view() {
        def post = Post.get(params.long('postId'))
        if (!post) {
            return render(status: HttpServletResponse.SC_NOT_FOUND)
        }

        render(view: '/front/post/postView', model: [post: post])
    }

    def getSearchFilter() {
        def filter = new PostFilter()
        filter.with {
            page = params.int('page', 1)
            pageSize = params.int('pageSize', 10)
            category = Category.findByAlias(params['alias'])
            year = params.long('year')
            month = params.long('month')
        }
        filter
    }

    def getRecentPosts() {
        postMgrService.getRecentPosts(6)
    }

    def getCategorys() {
        categoryMgrService.allCategorys()
    }

    def getArchives() {
        postMgrService.archives
    }
}
