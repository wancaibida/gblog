package me.w2x.blog.command

import me.w2x.blog.domain.Menu
import grails.validation.Validateable

/**
 * Created by charles.chen on 1/8/16.
 */
@Validateable
class MenuCommand {
    Long id
    String name
    String alias
    String url
    String icon
    Long parentId

    static constraints = {
        id nullable: true
        name blank: false
        alias(blank: true, validator: { val, obj ->
            def menu = Menu.where {
                alias == val
                if (obj.id) {
                    id != obj.id
                }
            }
            !menu
        })
        url nullable: true
        icon blank: false
        parentId nullable: false
    }
}
