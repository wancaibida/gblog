package me.w2x.blog.domain

import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true, excludes = 'metaClass,class')
class Menu {
    String name
    String alias
    String url
    String icon
    Integer _sort
    Long parentId

    static mapping = {
        table 't_menu'
        version false
        name column: 's_name'
        alias column: 's_alias'
        url column: 's_url'
        icon column: 's_icon'
        _sort column: 'n_sort'
        parentId column: 'n_parent_id'
    }

    static constraints = {
        id nullable: true
        name blank: false
        alias(blank: true, validator: { val, obj ->
            def menu = where {
                alias == val
                if (obj.id) {
                    id != obj.id
                }
            }
            !menu
        })
        url nullable: true
        icon blank: false
        _sort nullable: true
        parentId nullable: false
    }
}
