package me.w2x.blog.domain

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes = ['series', 'username'])
@ToString(includes = ['series', 'username'], cache = true, includeNames = true, includePackage = false)
class Session implements Serializable {

    private static final long serialVersionUID = 1

    String series
    String username
    String token
    Date lastUsed

    static constraints = {
        series maxSize: 64
        token maxSize: 64
        username maxSize: 64
    }

    static mapping = {
        table 't_session'
        id name: 'series', generator: 'assigned'
        series column: 's_series'
        username column: 's_username'
        token column: 's_token'
        lastUsed column: 'd_last_used'
        version false
    }
}
