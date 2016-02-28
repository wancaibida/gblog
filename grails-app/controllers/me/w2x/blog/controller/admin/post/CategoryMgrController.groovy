package me.w2x.blog.controller.admin.post

import grails.converters.JSON
import me.w2x.blog.command.CategoryCommand
import me.w2x.blog.controller.common.BaseController
import me.w2x.blog.domain.Category

import javax.servlet.http.HttpServletResponse

class CategoryMgrController extends BaseController {

    def categoryMgrService

    def index() {
        if (request.xhr) {
            render(getGrid(Category.class.getSimpleName()) as JSON)
        } else {
            render(view: '/admin/post/category')
        }
    }

    def add(CategoryCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }
        def category = categoryMgrService.addCategory(command)
        render(status: HttpServletResponse.SC_OK, contentType: 'application/json') {
            id = category.id
        }
    }

    def update(CategoryCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }
        categoryMgrService.updateCategory(command)
        render(status: HttpServletResponse.SC_OK, contentType: 'application/json') {
        }
    }

    def delete() {
        def categoryId = params.long('categoryId')
        def category = Category.get(categoryId)
        if (!category) {
            render(status: HttpServletResponse.SC_NOT_FOUND)
        }

        categoryMgrService.deleteCategory(category)
        render(status: HttpServletResponse.SC_NO_CONTENT)
    }

    def parents() {
        render categoryMgrService.listParentCategorys(params.long('categoryId')) as JSON
    }
}
