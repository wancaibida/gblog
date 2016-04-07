package blog

import grails.buildtestdata.mixin.Build
import grails.test.mixin.TestFor
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import me.w2x.blog.command.PostCommand
import me.w2x.blog.controller.admin.post.PostMgrController
import me.w2x.blog.domain.Post
import me.w2x.blog.enu.PostStatus
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

@Integration
@Rollback
@Build(Post)
@TestFor(PostMgrController)
class PostMgrControllerSpec extends Specification {

    @Autowired
    PostMgrController controller

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
        postCommand.validate()
        controller.add(postCommand)

        then:
        controller.response.status == HttpServletResponse.SC_OK
        Post.findByTitle('test post 000')
        Post.findByTitle('test post 000')?.status as String == PostStatus.PUBLISH.key
    }

    void "test add with error"() {
        def postCommand = new PostCommand()
        given:
        controller.request.method = 'POST'
        postCommand.with {
            postStatus = PostStatus.PUBLISH.key
            raw = 'raw text'
            content = 'content text'
        }

        when:
        postCommand.validate()
        controller.add(postCommand)

        then:
        controller.response.status == HttpServletResponse.SC_BAD_REQUEST
    }

    void "test update"() {
        setup:
        Post.build(title: 'post000', content: 'content000').save()

        when:
        PostCommand command = new PostCommand()
        command.id = Post.findByTitle('post000')?.id
        command.title = 'post001'
        command.content = 'content001'
        command.categoryId = 1L
        command.postStatus = PostStatus.DRAFT.key
        command.raw = 'content001'

        command.validate()
        controller.update(command)

        then:
        controller.response.status == HttpServletResponse.SC_OK
        Post.findByTitle('post001')?.content == 'content001'
    }
}
