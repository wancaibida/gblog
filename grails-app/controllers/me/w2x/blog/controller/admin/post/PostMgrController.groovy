package me.w2x.blog.controller.admin.post

import grails.converters.JSON
import me.w2x.blog.command.DraftCommand
import me.w2x.blog.command.PostCommand
import me.w2x.blog.controller.common.BaseController
import me.w2x.blog.domain.Category
import me.w2x.blog.domain.Draft
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

        def drafts = []
        def post = null
        if (postId) {
            post = Post.get(postId)
        }

        if (post) {
            def draft = Draft.findByPost(post)
            if (draft) {
                drafts << draft
            }
        } else {
            drafts = Draft.findAllByPostIsNull()
        }
        render(view: '/admin/post/postView', model: [categorys: categorys, post: post, drafts: drafts])
    }

    def add(PostCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }

        def draft = Draft.get(params.long('draftId'))
        if (draft) {
            draft.delete()
        }

        def post = postMgrService.add(command)
        render(status: HttpServletResponse.SC_OK, contentType: CONTENT_TYPE_JSON) {
            id post.id
        }
    }

    def update(PostCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }

        def draft = Draft.get(params.long('draftId'))
        if (draft) {
            draft.delete()
        }

        if (!Post.get(command.id)) {
            render(status: HttpServletResponse.SC_NOT_FOUND)
            return
        }

        def post = postMgrService.update(command)
        render(status: HttpServletResponse.SC_OK, contentType: CONTENT_TYPE_JSON) {
            id post.id
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

    def saveDraft(DraftCommand command) {
        if (command.hasErrors()) {
            return handleCommandException(command)
        }

        def draft = postMgrService.saveOrUpdateDraft(command)
        render(status: HttpServletResponse.SC_OK, contentType: CONTENT_TYPE_JSON) {
            id draft.id
        }
    }

    def getDraft() {
        def draft = Draft.get(params.long('draftId'))
        if (!draft) {
            return render(status: HttpServletResponse.SC_NOT_FOUND)
        }

        render draft as JSON
    }
}
