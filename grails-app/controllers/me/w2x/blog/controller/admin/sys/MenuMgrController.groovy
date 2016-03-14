package me.w2x.blog.controller.admin.sys

import grails.converters.JSON
import me.w2x.blog.command.ButtonCommand
import me.w2x.blog.command.MenuCommand
import me.w2x.blog.controller.common.BaseController
import me.w2x.blog.domain.Button
import me.w2x.blog.domain.Menu

import javax.servlet.http.HttpServletResponse

class MenuMgrController extends BaseController {

    def menuMgrService

    static allIcons = []

    def icons() {
        if (!allIcons) {
            synchronized (MenuMgrController) {
                if (!allIcons) {
                    def iconPath = servletContext.getRealPath('/') + '/WEB-INF/classes/static/icons/32X32/'
                    new File(iconPath).listFiles()?.each {
                        allIcons << it.name
                    }
                }
            }
        }

        render allIcons as JSON
    }

    def index() {
        if (request.isXhr()) {
            render(getGrid(Menu) as JSON)
        } else {
            render(view: '/admin/sys/menu')
        }
    }

    def add(MenuCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }
        def menu = menuMgrService.addMenu(command)
        render(status: HttpServletResponse.SC_OK, contentType: CONTENT_TYPE_JSON) {
            id = menu.id
        }
    }

    def update(MenuCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }
        menuMgrService.updateMenu(command)
        render(status: HttpServletResponse.SC_OK, contentType: CONTENT_TYPE_JSON) {
        }
    }

    def parents() {
        def menuId = params.long('menuId')
        def list = menuMgrService.listParentMenus(menuId)
        render list as JSON
    }

    def delete() {
        def id = params.long('menuId')
        def menu = Menu.get(id)
        if (menu) {
            menuMgrService.deleteMenu(menu)
            render(status: HttpServletResponse.SC_NO_CONTENT)
        } else {
            render(status: HttpServletResponse.SC_NOT_FOUND)
        }
    }

    def buttons() {
        if (request.isXhr()) {
            render getGrid(Button) as JSON
        } else {
            def menu = Menu.get(params.long('menuId'))
            render(view: '/admin/sys/button', model: [alias: menu?.alias])
        }
    }

    def addButton(ButtonCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }
        menuMgrService.addButton(command)
        render(status: HttpServletResponse.SC_OK, contentType: CONTENT_TYPE_JSON) {
        }
    }

    def addButtons() {
        def menu = Menu.get(params.long('menuId'))
        menuMgrService.addButtons(menu.alias)
        render(status: HttpServletResponse.SC_OK, contentType: CONTENT_TYPE_JSON) {
        }
    }

    def updateButton(ButtonCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }
        menuMgrService.updateButton(command)
        render(status: HttpServletResponse.SC_OK, contentType: CONTENT_TYPE_JSON) {
        }
    }

    def deleteButton() {
        def id = params.long('buttonId')
        def button = Button.get(id)
        if (button) {
            menuMgrService.deleteButton(button)
            render(status: HttpServletResponse.SC_NO_CONTENT)
        } else {
            render(status: HttpServletResponse.SC_NOT_FOUND)
        }
    }

}
