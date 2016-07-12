package me.w2x.blog

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import me.w2x.blog.domain.Category
import me.w2x.blog.domain.Post
import me.w2x.blog.service.CategoryMgrService
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

/**
 * Created by charles.chen on 7/12/16.
 */
@Integration
@Rollback
class CategoryMgrServiceSpec extends Specification {

    @Autowired
    CategoryMgrService service

    void 'test delete category'() {
        setup:
        if (!Category.get(1L)) {
            Category.build(id: 1L, name: 'default', alias: 'alias')
        }

        def category = Category.build(name: 'all', alias: 'bbb')
        def post = Post.build(category: category)

        when:
        service.deleteCategory(category)

        then:
        Category.get(category.id)?.isDeleted
        Post.get(post.id)?.category?.id == 1L
    }

    void 'test list parent category ids'() {
        setup:
        def parent = Category.build(name: '11', alias: '11')
        def parent1 = Category.build(name: '00', alias: '00', isDeleted: true)
        def child = Category.build(name: '22', alias: '22', parentId: parent.id)
        def subChild = Category.build(name: '33', alias: '33', parentId: child.id)

        when:
        def result = service.listParentCategorys(child.id)

        then:
        result.size() == 1

        when:
        result = service.listParentCategorys(subChild.id)

        then:
        result.size() == 2
    }

}
