package me.w2x.blog.command

import grails.validation.Validateable

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

    Long categoryId

    static constraints = {
        id nullable: true
        postId nullable: true
        title nullable: true
        raw nullable: true
        content nullable: true
        excerpt nullable: true
        categoryId nullable: true
    }
}
