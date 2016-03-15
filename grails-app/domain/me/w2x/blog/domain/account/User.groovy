package me.w2x.blog.domain.account

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = 'username')
@ToString(includes = 'username', includeNames = true, includePackage = false)
class User implements Serializable {

    private static final long serialVersionUID = 1

    @SuppressWarnings(['GrailsDomainWithServiceReference'])
    transient springSecurityService

    String username
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    User(String username, String password) {
        this()
        this.username = username
        this.password = password
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this)*.role
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
    }

    static transients = ['springSecurityService']

    static constraints = {
        username blank: false, unique: true
        password blank: false
    }

    static mapping = {
        table 't_user'
        version false
        username column: 's_username'
        password column: 's_password'
        enabled column: 'b_enabled'
        accountExpired column: 'b_account_expired'
        accountLocked column: 'b_account_locked'
        passwordExpired column: 'b_password_expired'
    }
}
