package me.w2x.blog.controller.front.post

import me.w2x.blog.bean.PostFilter
import me.w2x.blog.controller.common.BaseController
import me.w2x.blog.domain.Category
import me.w2x.blog.domain.Post
import me.w2x.blog.enu.PostStatus

import javax.servlet.http.HttpServletResponse

class PostController extends BaseController {
    static layout = 'postLayout'

    def postMgrService

    def categoryMgrService

    def index() {
        def (total, posts, pageCount) = postMgrService.getPosts(searchFilter)
        def title = 'W2X'
        if (searchFilter.category) {
            title = searchFilter.category?.name
        } else if (searchFilter.year && searchFilter.month) {
            title = "${searchFilter.year}-${searchFilter.month}"
        }

        render(view: '/front/post/postList',
                model: [
                        title      : title,
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
        if (!post || post.status != PostStatus.PUBLISH.key) {
            return render(status: HttpServletResponse.SC_NOT_FOUND)
        }

        render(view: '/front/post/postView', model: [post: post])
    }

    private getSearchFilter() {
        def filter = new PostFilter()
        filter.with {
            page = params.int('page', 1)
            category = Category.findByAlias(params['alias'])
            year = params.long('year')
            month = params.long('month')
            pageSize = (category || (year && month)) ? Integer.MAX_VALUE : 5
        }
        filter
    }

    private getRecentPosts() {
        postMgrService.getRecentPosts(6)
    }

    private getCategorys() {
        categoryMgrService.allCategorys()
    }

    private getArchives() {
        postMgrService.archives
    }
}
