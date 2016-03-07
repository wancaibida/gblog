package me.w2x.blog.filter

import grails.plugin.springsecurity.web.authentication.GrailsUsernamePasswordAuthenticationFilter
import me.w2x.blog.util.RSAUtils

import javax.servlet.http.HttpServletRequest

/**
 * Created by charles.chen on 3/7/16.
 */
class CustomUsernamePasswordAuthenticationFilter extends GrailsUsernamePasswordAuthenticationFilter {
    @Override
    protected String obtainPassword(HttpServletRequest request) {
        def password = super.obtainPassword(request)
        return RSAUtils.decryptStringByJs(password)
    }
}
