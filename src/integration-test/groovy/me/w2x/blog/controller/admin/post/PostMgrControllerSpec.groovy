package me.w2x.blog.controller.admin.post

import grails.test.mixin.TestFor
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import me.w2x.blog.command.PostCommand
import me.w2x.blog.domain.Post
import me.w2x.blog.enu.PostStatus
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

@Integration
@TestFor(PostMgrController)
class PostMgrControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test add"() {
        def postCommand = new PostCommand()
        given:
        controller.request.method = 'post'
        postCommand.with {
            title = 'test post 000'
            categoryId = 1L
            postStatus = PostStatus.PUBLISH.key
            raw = 'raw text'
            content = 'content text'
        }

        when:
        controller.add(postCommand)

        then:
        controller.response.status == HttpServletResponse.SC_OK
        Post.findByTitle('test post 000')
        Post.findByTitle('test post 000')?.postStatus == PostStatus.PUBLISH.key
    }
}
