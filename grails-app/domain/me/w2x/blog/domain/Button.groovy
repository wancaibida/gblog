package me.w2x.blog.domain

import groovy.transform.ToString

@ToString
class Button {
    String name
    Integer viewSort
    String icon
    String menuAlias
    String alias

    static constraints = {
    }

    static mapping = {
        table 't_button'
        version false
        name column: 's_name'
        viewSort column: 'n_sort'
        icon column: 's_icon'
        menuAlias column: 's_menu_alias'
        alias column: 's_alias'
    }
}
