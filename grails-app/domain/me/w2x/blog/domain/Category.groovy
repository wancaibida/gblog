package me.w2x.blog.domain

import groovy.transform.ToString
import me.w2x.blog.bean.Regex

@ToString
class Category {
    String name
    String alias
    Long parentId = 0L

    static constraints = {
        id nullable: true
        name blank: false
        alias(blank: false, validator: { val, obj ->
            if (!Regex.ALPHA_NUMERIC.matcher(val).matches()) {
                return false
            }
            def category = where {
                alias == val
                if (obj.id) {
                    id != obj.id
                }
            }.list()
            !category
        })
        parentId nullable: false

    }

    static mapping = {
        table 't_category'
        version false
        name column: 's_name'
        alias column: 's_alias'
        parentId column: 'n_parent_id'
    }
}
