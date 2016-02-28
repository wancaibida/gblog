package me.w2x.blog.controller.admin.user

import me.w2x.blog.controller.common.BaseController

import javax.servlet.http.HttpServletResponse

/**
 * Created by charles.chen on 2/4/16.
 */
class UserController extends BaseController {

    def auth() {
        render(view: '/admin/user/login', model:
                [rememberMeParameter: conf.rememberMe.parameter])
    }

    def authfail() {
        if (params.boolean('ajax')) {
            render(contentType: 'application/json', {
                [error: 'error password or user name']
            })
        } else {
            redirect(action: 'auth')
        }
    }

    def authsuccess() {
        if (params.boolean('ajax')) {
            render(contentType: 'application/json', {
            })
        } else {
            render(status: HttpServletResponse.SC_OK)
        }
    }

    def doLogin() {

    }
}
