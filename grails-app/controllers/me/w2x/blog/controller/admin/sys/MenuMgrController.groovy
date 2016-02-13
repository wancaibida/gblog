package me.w2x.blog.controller.admin.sys

import me.w2x.blog.domain.Button
import me.w2x.blog.domain.Menu
import me.w2x.blog.command.ButtonCommand
import me.w2x.blog.command.MenuCommand
import me.w2x.blog.controller.common.BaseController

import javax.servlet.http.HttpServletResponse

class MenuMgrController extends BaseController {

    def menuMgrService


    def icons() {
        def icons = []
        def iconPath = servletContext.getRealPath('/') + 'static/icons/32X32/'
        new File(iconPath)?.listFiles()?.each {
            icons << it.name
        }
        render(contentType: 'application/json', {
            icons
        })
    }

    def index() {
        if (request.isXhr()) {
            render(contentType: 'application/json', {
                getGrid('Menu')
            })
        } else {
            render(view: '/admin/sys/menu')
        }
    }

    def add(MenuCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }
        def menu = menuMgrService.addMenu(command)
        render(status: HttpServletResponse.SC_OK, contentType: 'application/json') {
            id = menu.id
        }
    }

    def update(MenuCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }
        menuMgrService.updateMenu(command)
        render(status: HttpServletResponse.SC_OK, contentType: 'application/json') {
        }
    }

    def parents() {
        def menuId = params.long('menuId')
        def list = menuMgrService.listParentMenus(menuId)
        render(status: HttpServletResponse.SC_OK, contentType: 'application/json') {
            list
        }
    }

    def delete() {
        def id = params.long('menuId')
        def menu = Menu.get(id)
        if (!menu) {
            render(status: HttpServletResponse.SC_NOT_FOUND)
        } else {
            menuMgrService.deleteMenu(menu)
            render(status: HttpServletResponse.SC_NO_CONTENT)
        }
    }

    def buttons() {
        if (request.isXhr()) {
            render(contentType: 'application/json', {
                getGrid('Button')
            })
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
        render(status: HttpServletResponse.SC_OK, contentType: 'application/json') {
        }
    }

    def addButtons() {
        def menu = Menu.get(params.long('menuId'))
        menuMgrService.addButtons(menu.alias)
        render(status: HttpServletResponse.SC_OK, contentType: 'application/json') {
        }
    }

    def updateButton(ButtonCommand command) {
        if (command.hasErrors()) {
            return handleValidation(command)
        }
        menuMgrService.updateButton(command)
        render(status: HttpServletResponse.SC_OK, contentType: 'application/json') {
        }
    }

    def deleteButton() {
        def id = params.long('buttonId')
        def button = Button.get(id)
        if (!button) {
            render(status: HttpServletResponse.SC_NOT_FOUND)
        } else {
            menuMgrService.deleteButton(button)
            render(status: HttpServletResponse.SC_NO_CONTENT)
        }
    }

}
