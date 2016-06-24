package me.w2x.blog.command

import grails.validation.Validateable
import me.w2x.blog.domain.Category

/**
 * Created by charles.chen on 6/24/16.
 */
class DraftCommand implements Validateable {
    Long id

    Long postId

    String title

    String raw

    String content

    String excerpt

    Category category

    static constraints = {
        id nullable: true
        postId nullable: true
        title nullable: true
        raw nullable: true
        content nullable: true
        excerpt nullable: true
        category nullable: true
    }
}
