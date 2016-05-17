package me.w2x.blog

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import me.w2x.blog.service.DatabaseBackupService
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

/**
 * Created by charles.chen on 5/17/16.
 */
@Integration
@Rollback
class DatabaseBackupServiceSpec extends Specification {

    @Autowired
    DatabaseBackupService databaseBackupService

    void 'test backup'() {
        when:
        databaseBackupService.backup()
        then:
        1 == 1
    }
}
