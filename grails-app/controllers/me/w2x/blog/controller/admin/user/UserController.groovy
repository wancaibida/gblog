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

    def passwordService

    def auth() {
        render(view: '/admin/user/login', model:
                [
                        rememberMeParameter: conf.rememberMe.parameter,
                        hasCookie          : authenticationTrustResolver.isRememberMe(authentication)
                ])
    }

    def authfail() {
        if (params.boolean('ajax')) {
            renderResponse(HttpServletResponse.SC_BAD_REQUEST, message(code: 'user.password.not.correct'))
        } else {
            redirect(action: 'auth')
        }
    }

    def authsuccess() {
        if (params.boolean('ajax')) {
            render(status: HttpServletResponse.SC_OK)
        } else {
            render(status: HttpServletResponse.SC_OK)
        }
    }

    def updatePassword() {
        def rsaOldPassword = params.oldPassword
        def rsaNewPassword = params.newPassword

        def oldPassword = RSAUtils.decryptStringByJs(rsaOldPassword)
        def newPassword = RSAUtils.decryptStringByJs(rsaNewPassword)

        if (!newPassword) {
            renderResponse(HttpServletResponse.SC_BAD_REQUEST, message(code: 'user.newpassword.not.null'))
            return
        }

        def principal = authentication.principal
        def userId = principal.id

        def currentUser = User.get(userId)
        if (!currentUser) {
            renderResponse(HttpServletResponse.SC_BAD_REQUEST, message(code: 'user.not.exist'))
            return
        }

        if (passwordService.isPasswordValid(currentUser.password, oldPassword)) {
            passwordService.updatePassword(currentUser, newPassword)
            render(status: HttpServletResponse.SC_OK)
        } else {
            return renderResponse(HttpServletResponse.SC_BAD_REQUEST, message(code: 'user.password.not.correct'))
        }
    }
}
