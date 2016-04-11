package me.w2x.blog

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import me.w2x.blog.command.PostCommand
import me.w2x.blog.domain.Post
import me.w2x.blog.enu.PostStatus
import me.w2x.blog.service.PostMgrService
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PostMgrServiceSpec extends Specification {

    @Autowired
    PostMgrService postMgrService

    void "test add post"() {
        setup:
        def command = new PostCommand()
        command.with {
            title = 'test title 000'
            content = 'content 000'
            raw = 'raw 000'
            categoryId = 1L
            postStatus = PostStatus.PUBLISH.key
        }
        when:
        postMgrService.add(command)

        then:
        Post.findByTitle('test title 000')?.content == 'content 000'
    }
}
