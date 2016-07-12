package me.w2x.blog.domain

import groovy.transform.ToString

@ToString
class Post {

    String title

    String raw

    String content

    String excerpt

    Integer commentCount

    Integer status

    Category category

    Date dateCreated

    Date lastUpdated

    Boolean isDeleted = Boolean.FALSE

    static constraints = {
        title blank: false
        raw blank: true
        content blank: false
        excerpt blank: false
    }

    static mapping = {
        table 't_post'
        title column: 's_title'
        raw column: 's_raw'
        content column: 's_content'
        excerpt column: 's_excerpt'
        commentCount column: 'n_comment_count'
        dateCreated column: 'd_created'
        lastUpdated column: 'd_updated'
        status column: 'n_status'
        category column: 'n_category_id', fetch: 'join'
        isDeleted column: 'b_is_deleted'
        version false
    }

}
