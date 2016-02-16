package me.w2x.blog.command

import grails.validation.Validateable
import me.w2x.blog.bean.Regex
import me.w2x.blog.domain.Category

/**
 * Created by charles.chen on 1/17/16.
 */
class CategoryCommand implements Validateable {
    Long id
    Long parentId = 0L
    String name
    String alias

    static constraints = {
        id nullable: true
        parentId nullable: false
        name blank: false
        alias(blank: false, validator: { val, obj ->
            if (!Regex.ALPHA_NUMERIC.matcher(val).matches()) {
                return false
            }
            def category = Category.where {
                alias == val
                if (obj.id) {
                    id != obj.id
                }
            }.list()
            !category
        })
    }

}
