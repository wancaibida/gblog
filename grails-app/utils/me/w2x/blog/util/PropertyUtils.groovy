package me.w2x.blog.util

/**
 * Created by charles.chen on 4/29/16.
 */
class PropertyUtils {
    static String getProp(String name) {
        System.getProperty(name) ?: System.getenv().get(name)
    }
}
