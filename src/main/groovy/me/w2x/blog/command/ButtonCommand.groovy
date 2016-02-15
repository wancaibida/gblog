package me.w2x.blog.command

import grails.validation.Validateable

/**
 * Created by charles.chen on 1/16/16.
 */
@Validateable
class ButtonCommand {
    Long id
    String name
    String alias
    String menuAlias
    String icon
    Integer viewSort

    static constraints = {
        name blank: false
        alias blank: false
        menuAlias blank: false
        icon blank: false
        viewSort nullable: false
    }

}
