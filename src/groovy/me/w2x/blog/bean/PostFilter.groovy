package me.w2x.blog.bean

import me.w2x.blog.domain.Category

/**
 * Created by charles.chen on 1/31/16.
 */
class PostFilter extends SearchFilter {
    Category category
    Integer year
    Integer month
    Long userId
}
