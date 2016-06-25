package me.w2x.blog

import grails.buildtestdata.mixin.Build
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import me.w2x.blog.command.PostCommand
import me.w2x.blog.domain.Category
import me.w2x.blog.domain.Post
import me.w2x.blog.enu.PostStatus
import me.w2x.blog.service.PostMgrService
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
@Build([Post, Category])
class PostMgrServiceSpec extends Specification {

    @Autowired
    PostMgrService postMgrService

    void "test add post"() {
        setup:
        def category = Category.build(alias: 'category000')

        def command = new PostCommand()
        command.with {
            title = 'test title 000'
            content = 'content 000'
            raw = 'raw 000'
            categoryId = category.id
            postStatus = PostStatus.PUBLISH.key
        }
        when:
        postMgrService.add(command)

        then:
        Post.findByTitle('test title 000')?.content == 'content 000'
    }

    void "test update post"() {
        setup:
        def category = Category.build(alias: 'category000')
        Post post = Post.build(title: 'title001', category: category, status: PostStatus.DRAFT.key)
        PostCommand postCommand = new PostCommand()
        postCommand.with {
            id = post.id
            title = 'title002'
            content = post.content
            excerpt = post.excerpt
            categoryId = Category.findByName('category000')?.id
            raw = post.raw
            postStatus = PostStatus.PUBLISH.key
        }

        when:
        postMgrService.update(postCommand)

        then:
        Post.get(postCommand.id)?.title == 'title002'
    }
}
