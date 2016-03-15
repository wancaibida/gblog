package me.w2x.blog.service

import grails.transaction.Transactional
import me.w2x.blog.bean.MenuFilter
import me.w2x.blog.bean.QueryResult
import me.w2x.blog.command.ButtonCommand
import me.w2x.blog.command.MenuCommand
import me.w2x.blog.domain.Button
import me.w2x.blog.domain.Menu

@Transactional
class MenuMgrService {

    def sessionFactory

    def getMenus(MenuFilter filter) {
        def result = Menu.createCriteria().list {
            if (filter.name) {
                ilike('name', filter.name)
            }

            if (filter.alias) {
                eq('alias', filter.alias)
            }

            if (filter.parentId) {
                eq('parentId', filter.parentId)
            }

            setMaxResults filter.pageSize
            setFirstResult((filter.page - 1 > 0 ? filter.page : 0) * filter.pageSize)

            if (filter.sort && filter.sortOrder) {
                order(filter.sort, filter.sortOrder)
            }
        }

        def count = Menu.createCriteria().list {
            if (filter.name) {
                ilike('name', filter.name)
            }

            if (filter.alias) {
                eq('alias', filter.alias)
            }

            if (filter.parentId) {
                eq('parentId', filter.parentId)
            }

            projections { count('id') }
        }
        new QueryResult<>(((Long) count[0]) as int, result)
    }

    def addMenu(MenuCommand menuCommand) {
        def menu = new Menu()
        menu.with {
            name = menuCommand.name
            alias = menuCommand.alias
            icon = menuCommand.icon
            url = menuCommand.url
            parentId = menuCommand.parentId
            sortBy = 0L
        }
        menu.save(flush: true)
    }

    def updateMenu(MenuCommand menuCommand) {
        def menu = Menu.get(menuCommand.id)
        menu.with {
            name = menuCommand.name
            alias = menuCommand.alias
            icon = menuCommand.icon
            url = menuCommand.url
            parentId = menuCommand.parentId
            save()
        }
    }

    def deleteMenu(Menu menu) {
        menu.delete()
    }

    def listParentMenus(Long menuId) {
        if (menuId) {
            def session = sessionFactory.currentSession
            def query = session.createSQLQuery(
                    '''
                    SELECT
                     ID,
                     s_name
                    FROM
                     t_menu
                    WHERE
                     ID NOT IN (
                      SELECT
                       res. ID
                      FROM
                       (
                        WITH RECURSIVE r AS (
                         SELECT
                          *
                         FROM
                          t_menu
                         WHERE
                          ID = :id
                         UNION ALL
                          SELECT
                           t_menu.*
                          FROM
                           t_menu,
                           r
                          WHERE
                           t_menu.n_parent_id = r. ID
                        ) SELECT
                         *
                        FROM
                         r
                        ORDER BY
                         ID
                       ) AS res
                     )
                '''
            )
            query.setLong('id', menuId)

            return query.list().collect {
                [id: it[0], text: it[1], value: it[0]]
            }
        }

        Menu.list().collect {
            [id: it.id, text: it.name, value: it.id]
        }
    }

    def addButton(ButtonCommand command) {
        Button button = new Button()
        button.with {
            name = command.name
            alias = command.alias
            icon = command.icon
            menuAlias = command.menuAlias
            viewSort = command.viewSort
        }
        button.save()
    }

    @SuppressWarnings('TrailingComma')
    def addButtons(String menuAlias) {
        [
                [alias: 'add', name: '增加', icon: 'add.gif', viewSort: 1],
                [alias: 'modify', name: '修改', icon: 'modify.gif', viewSort: 2],
                [alias: 'delete', name: '删除', icon: 'delete.gif', viewSort: 3]
        ].each { btn ->
            Button button = new Button()
            button.with {
                name = btn.name
                alias = btn.alias
                icon = btn.icon
                it.menuAlias = menuAlias
                viewSort = btn.viewSort
            }
            button.save()
        }
    }

    def updateButton(ButtonCommand command) {
        def button = Button.get(command.id)
        button.with {
            name = command.name
            alias = command.alias
            icon = command.icon
            menuAlias = command.menuAlias
            viewSort = command.viewSort
        }
        button.save()
    }

    def deleteButton(Button button) {
        button.delete()
    }
}
