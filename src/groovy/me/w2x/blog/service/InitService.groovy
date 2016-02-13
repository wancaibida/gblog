package me.w2x.blog.service

import org.springframework.web.context.ServletContextAware

/**
 * User: Gang Chen
 * Date: 2015/12/5 21:54
 */
interface InitService extends ServletContextAware {
    def init()
}
