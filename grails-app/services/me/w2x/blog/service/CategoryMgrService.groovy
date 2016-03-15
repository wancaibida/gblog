package me.w2x.blog.service

import grails.transaction.Transactional
import me.w2x.blog.command.CategoryCommand
import me.w2x.blog.domain.Category
import me.w2x.blog.exception.CommandException

@Transactional
class CategoryMgrService {

    def sessionFactory

    def addCategory(CategoryCommand command) {
        def category = new Category()
        category.with {
            name = command.name
            alias = command.alias
            parentId = command.parentId
        }
        category.save()
    }

    def updateCategory(CategoryCommand command) {
        def category = Category.get(command.id)
        category.with {
            name = command.name
            alias = command.alias
            parentId = command.parentId
        }
        category.save()
    }

    def deleteCategory(Category category) {
        def children = Category.findByParentId(category.id)
        if (children) {
            throw new CommandException('error.category.children.exist')
        }
        category.delete()
    }

    def listParentCategorys(Long categoryId) {
        if (categoryId) {
            def session = sessionFactory.currentSession
            def query = session.createSQLQuery(
                    '''
                        SELECT
                         ID,
                         s_name
                        FROM
                         t_category
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
                              t_category
                             WHERE
                              ID = :id
                             UNION ALL
                              SELECT
                               t_category.*
                              FROM
                               t_category,
                               r
                              WHERE
                               t_category.n_parent_id = r. ID
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
            query.setLong('id', categoryId)

            return query.list().collect {
                [id: it[0], text: it[1], value: it[0]]
            }
        }

        Category.list().collect {
            [id: it.id, text: it.name, value: it.id]
        }
    }

    def allCategorys() {
        Category.list()
    }
}
