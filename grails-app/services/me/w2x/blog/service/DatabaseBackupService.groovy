package me.w2x.blog.service

import grails.transaction.Transactional

@Transactional
class DatabaseBackupService {

    def grailsApplication

    void backup() {
        def allDomainClasses = grailsApplication.getArtefacts("Domain")*.clazz
        if(!allDomainClasses){
            return
        }

    }


}
