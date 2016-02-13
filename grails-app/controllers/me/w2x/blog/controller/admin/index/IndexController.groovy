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

    def menus() {
        render(contentType: 'application/json', {
            Menu.list(sort: '_sort', order: 'asc')
        })
    }

    def buttons() {
        String menuAlias = params.alias
        render(contentType: 'application/json', {
            Button.findAllByMenuAlias(menuAlias)
        })
    }
}
