package me.w2x.blog.domain

import groovy.transform.ToString

@ToString
class Draft {
    Post post

    String title

    String raw

    String content

    String excerpt

    Category category

    Date dateCreated

    Date lastUpdated

    static constraints = {
        title nullable: true
        raw nullable: true
        post nullable: true
        content nullable: true
        excerpt nullable: true
        category nullable: true
    }

    static mapping = {
        table 't_draft'
        version false
        title column: 's_title'
        raw column: 's_raw'
        content column: 's_content'
        excerpt column: 's_excerpt'
        category column: 'n_category_id'
        dateCreated column: 'd_created'
        lastUpdated column: 'd_updated'
    }
}
