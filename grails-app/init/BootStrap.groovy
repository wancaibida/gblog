import grails.util.Environment
import me.w2x.blog.domain.account.Role
import me.w2x.blog.domain.account.User
import me.w2x.blog.domain.account.UserRole

class BootStrap {

    def init = { servletContext ->
        def adminUsername = 'admin'
        def admin = User.findByUsername(adminUsername)
        if (!admin) {
            def password
            if (Environment.current == Environment.DEVELOPMENT || Environment.current == Environment.TEST) {
                password = 'test123'
            } else {
                password = System.properties['password']
            }
            assert password
            admin = new User(username: adminUsername, password: password).save()
            UserRole.create(admin, Role.findByAuthority('ROLE_ADMIN'), true)
        }
    }
    def destroy = {
    }
}
