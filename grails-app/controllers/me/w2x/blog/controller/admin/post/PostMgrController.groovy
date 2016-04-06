package me.w2x.blog.controller.admin.post

import grails.converters.JSON
import me.w2x.blog.command.PostCommand
import me.w2x.blog.controller.common.BaseController
import me.w2x.blog.domain.Category
import me.w2x.blog.domain.Post
import me.w2x.blog.service.PostMgrService

import javax.servlet.http.HttpServletResponse

class PostMgrController extends BaseController {

    PostMgrService postMgrService

    def index() {
        if (request.xhr) {
            def rawWhere = params.where as String
            def page = params.int('page', 1)
            def pageSize = params.int('pagesize', 10)
            def sort = params.sortname as String
            def sortOrder = params.sortorder as String
            def result = postMgrService.getPosts(rawWhere, page, pageSize, sort, sortOrder)

            //DIAO BAO LE
            JSON.use('deep') {
                render result as JSON
            }
        } else {
            render(view: '/admin/post/post')
        }
    }

    def view() {
        def categorys = Category.list()
        Long postId = params.long('postId')
        def post = null
        if (postId) {
            post = Post.get(postId)
        }

        render(view: '/admin/post/postView', model: [categorys: categorys, post: post])
    }

    def add(PostCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }

        postMgrService.add(command)
        render(status: HttpServletResponse.SC_OK, contentType: CONTENT_TYPE_JSON) {
        }
    }

    def update(PostCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }

        if (!Post.get(command.id)) {
            render(status: HttpServletResponse.SC_NOT_FOUND)
            return
        }

        postMgrService.update(command)
        render(status: HttpServletResponse.SC_OK, contentType: CONTENT_TYPE_JSON) {
        }
    }

    def delete() {
        def id = params.long('postId')
        def post = Post.get(id)
        if (post) {
            postMgrService.delete(post)
            render(status: HttpServletResponse.SC_NO_CONTENT)
        } else {
            render(status: HttpServletResponse.SC_NOT_FOUND)
        }
    }
}
