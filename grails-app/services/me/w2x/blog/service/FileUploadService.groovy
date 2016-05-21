package me.w2x.blog.service

import com.qiniu.storage.UploadManager
import com.qiniu.util.Auth
import org.apache.commons.io.FilenameUtils

/**
 * Created by charles.chen on 5/20/16.
 */
class FileUploadService {

    def grailsApplication

    void upload(String filePath) {
        String fileName = FilenameUtils.getName(filePath)
        new UploadManager().put(filePath, fileName, getUpToken(bucketname))
    }

    String getUpToken(String bucketname) {
        auth.uploadToken(bucketname)
    }

    Auth getAuth() {
        Auth.create(grailsApplication.config.my.project.accessKey, grailsApplication.config.my.project.secretKey)
    }

    String getBucketname() {
        grailsApplication.config.my.project.bucketname
    }
}
