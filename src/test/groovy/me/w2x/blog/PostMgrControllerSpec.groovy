package me.w2x.blog

import grails.buildtestdata.mixin.Build
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import me.w2x.blog.controller.admin.post.PostMgrController
import me.w2x.blog.domain.Category
import me.w2x.blog.domain.Post
import me.w2x.blog.enu.PostStatus
import me.w2x.blog.service.PostMgrService
import spock.lang.Shared
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestFor(PostMgrController)
@TestMixin([GrailsUnitTestMixin])
@Mock([PostMgrService, Category, Post])
@Build([Post, Category])
class PostMgrControllerSpec extends Specification {

    static doWithSpring = {
        postMgrService(PostMgrService)
    }

    @Shared
    def category

    def setup() {
        category = new Category()
        category.name = 'ddfa'
        category.alias = 'default'
        category.id = 1L
        category.save()
    }

    void "test add post"() {
        when:
        controller.request.method = 'POST'
        controller.params.title = 'title000'
        controller.params.categoryId = category.id
        controller.params.postStatus = PostStatus.PUBLISH.key
        controller.params.raw = 'raw000'
        controller.params.content = 'content000'
        controller.add()

        then:
        controller.response.status == HttpServletResponse.SC_OK
    }

    void "test update post"() {
        setup:
        Post post = Post.build(title: 'title001', category: category)

        when:
        controller.request.method = 'POST'
        controller.params.title = 'title002'
        controller.params.id = post.id
        controller.params.categoryId = category.id
        controller.params.postStatus = PostStatus.PUBLISH.key
        controller.params.raw = 'raw001'
        controller.params.content = 'content001'
        controller.update()

        then:
        controller.response.status == HttpServletResponse.SC_OK
        Post.get(post.id)?.title == 'title002'
    }
}