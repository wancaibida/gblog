package me.w2x.blog.bean

import me.w2x.blog.domain.Category
import me.w2x.blog.enu.ActionTypes

/**
 * Created by charles.chen on 7/13/16.
 */
class CategoryEvent implements SiteEvent {
    ActionTypes actionType
    Category category
}
