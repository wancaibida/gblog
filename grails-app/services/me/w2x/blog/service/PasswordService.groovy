package me.w2x.blog.service

import me.w2x.blog.domain.account.User

class PasswordService {

    def springSecurityService

    def updatePassword(User user, String newPassword) {
        user.password = newPassword
        user.save()
    }

    def encodePassword(String password) {
        springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }

    def isPasswordValid(String expectedPassword, String password) {
        springSecurityService?.passwordEncoder?.isPasswordValid(expectedPassword, password, null)
    }
}
