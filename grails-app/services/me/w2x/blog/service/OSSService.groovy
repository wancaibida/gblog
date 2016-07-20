package me.w2x.blog.service

import com.aliyun.oss.OSSClient
import com.aliyun.oss.model.ObjectMetadata
import grails.core.GrailsApplication
import org.apache.commons.io.FilenameUtils

/**
 * Created by charles.chen on 7/11/16.
 */
@SuppressWarnings('GrailsStatelessService')
class OssService {

    GrailsApplication grailsApplication

    void upload(String key, InputStream inputStream) {
        ObjectMetadata metadata = new ObjectMetadata()
        if (!FilenameUtils.getExtension(key)) {
            metadata.setContentType('text/html')
        }
//        metadata.setContentEncoding(Constant.DEFAULT_ENCODING)
        client.putObject(bucketName, key, inputStream, metadata)
    }

    void delete(String key) {
        client.deleteObject(bucketName, key)
    }

    OSSClient getClient() {
        String endPoint = grailsApplication.config.my.project.ossEndPoint as String
        String accessKeyId = grailsApplication.config.my.project.ossAccessKeyId as String
        String accessKeySecret = grailsApplication.config.my.project.ossAccessKeySecret as String

        new OSSClient(endPoint, accessKeyId, accessKeySecret)
    }

    String getBucketName() {
        grailsApplication.config.my.project.ossBucketName as String
    }

}
