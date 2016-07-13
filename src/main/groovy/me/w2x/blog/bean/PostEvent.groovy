package me.w2x.blog.bean

import me.w2x.blog.domain.Post
import me.w2x.blog.enu.ActionTypes

/**
 * Created by charles.chen on 7/13/16.
 */
class PostEvent implements SiteEvent {
    ActionTypes actionType
    Post post
}
