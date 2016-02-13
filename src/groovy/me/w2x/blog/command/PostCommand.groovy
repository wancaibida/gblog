package me.w2x.blog.command

import me.w2x.blog.domain.Category
import me.w2x.blog.enu.PostStatus
import grails.validation.Validateable

/**
 * Created by charles.chen on 1/24/16.
 */
@Validateable
class PostCommand {
    Long id
    String title
    Long categoryId
    String postStatus
    String raw
    String content

    static constraints = {
        id nullable: true
        title blank: false
        categoryId(nullable: false, validator: { val, obj ->
            Category.get(val) != null
        })
        postStatus inList: [PostStatus.DRAFT.key, PostStatus.PENDING.key, PostStatus.PUBLISH.key]
        content blank: false
        raw blank: true
    }
}
