package blog

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
        controller.add(postCommand)

        then:
        controller.response.status == HttpServletResponse.SC_OK
        Post.findByTitle('test post 000')
        Post.findByTitle('test post 000')?.status as String == PostStatus.PUBLISH.key
    }
}
