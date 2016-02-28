package me.w2x.blog.controller.admin.index

import grails.converters.JSON
import me.w2x.blog.domain.Button
import me.w2x.blog.domain.Menu
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class IndexController {
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class)


    def index() {
        def menus = Menu.findAll()
        def menuJson = menus as JSON
        render(view: '/admin/index/index', model: [menus: menuJson])
    }

    def allMenus() {
        def menus = Menu.list(sort: '_sort', order: 'asc')
        render menus as JSON
    }

    def buttons() {
        String menuAlias = params.alias
        render Button.findAllByMenuAlias(menuAlias) as JSON
    }
}
