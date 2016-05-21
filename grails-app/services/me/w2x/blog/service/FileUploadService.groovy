package me.w2x.blog.service

import com.qiniu.storage.UploadManager
import com.qiniu.util.Auth
import org.apache.commons.io.FilenameUtils

/**
 * Created by charles.chen on 5/20/16.
 */
class FileUploadService {

    def grailsApplication

    @Lazy
    String bucketname = grailsApplication.config.my.project.bucketname

    @Lazy
    Auth auth = Auth.create(grailsApplication.config.my.project.accessKey, grailsApplication.config.my.project.secretKey);

    void upload(String filePath) {
        String fileName = FilenameUtils.getName(filePath)
        new UploadManager().put(filePath, fileName, getUpToken(bucketname))
    }

    String getUpToken(String bucketname) {
        auth.uploadToken(bucketname)
    }

}
