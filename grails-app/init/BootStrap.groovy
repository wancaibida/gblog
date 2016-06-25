import grails.util.Environment
import me.w2x.blog.domain.account.Role
import me.w2x.blog.domain.account.User
import me.w2x.blog.domain.account.UserRole
import me.w2x.blog.util.PropertyUtils

class BootStrap {

    def init = { servletContext ->
        if (Environment.current != Environment.TEST) {
            def adminUsername = 'admin'
            def admin = User.findByUsername(adminUsername)
            if (!admin) {
                def password
                if (Environment.current == Environment.DEVELOPMENT) {
                    password = 'test123'
                } else {
                    password = PropertyUtils.getProp('ADMIN_INITIAL_PASSWORD')
                }
                assert password
                admin = new User(username: adminUsername, password: password).save()
                UserRole.create(admin, Role.findByAuthority('ROLE_ADMIN'), true)
            }
        }
    }
    def destroy = {
    }
}
