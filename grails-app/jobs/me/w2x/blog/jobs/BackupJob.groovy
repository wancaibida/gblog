package me.w2x.blog.jobs

import me.w2x.blog.service.FileUploadService
import org.apache.commons.io.FileUtils

class UploadBackupJob {

    def grailsApplication

    FileUploadService fileUploadService

    static triggers = {
        cron name: 'myTrigger', cronExpression: "0 30 1 * * ?"
    }

    def description = "Upload backup job with Cron Trigger"

    void execute() {
        log.info('Upload dump files start')
        String dumpPath = grailsApplication.config.my.dumpPath
        if (dumpPath && new File(dumpPath).exists()) {
            new File(dumpPath).listFiles().each {
                fileUploadService.upload(it.getAbsolutePath())
                FileUtils.deleteQuietly(it)
                log.info("File ${it.getAbsolutePath()} upload successful!")
            }
        }
        log.info('Upload dump files end')
    }
}