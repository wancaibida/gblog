package me.w2x.blog.controller.admin.user

import me.w2x.blog.controller.common.BaseController
import me.w2x.blog.domain.account.User
import me.w2x.blog.util.RSAUtils

import javax.servlet.http.HttpServletResponse

/**
 * Created by charles.chen on 2/4/16.
 */
class UserController extends BaseController {

    def authenticationTrustResolver

    def auth() {
        render(view: '/admin/user/login', model:
                [
                        rememberMeParameter: conf.rememberMe.parameter,
                        hasCookie          : authenticationTrustResolver.isRememberMe(authentication)
                ])
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

    def updatePassword() {
//        def user = User.get(params.userId)
//        if (!user) {
//            renderJsonResponse(HttpServletResponse.SC_BAD_REQUEST, 'user.does.exist', 'user.does.exist')
//            return
//        }

        def encodedOldPassword = params.oldPassword
        def encodedNewPassword = params.newPassword

        def oldPassword = RSAUtils.decryptStringByJs(encodedOldPassword)
        def newPassword = RSAUtils.decryptStringByJs(encodedNewPassword)

        if (!newPassword) {
            renderJsonResponse(HttpServletResponse.SC_BAD_REQUEST, 'password null', 'password null')
            return
        }

        def principal = authentication.principal
        def userId = principal.id

        def currentUser = User.get(userId)
        if (!currentUser) {
            renderJsonResponse(HttpServletResponse.SC_BAD_REQUEST, 'user null', 'user null')
            return
        }

        def expectedEncodedOldPassword=currentUser.password
//        if(expectedEncodedOldPassword.equals())


        render(status: HttpServletResponse.SC_OK)
    }
}
