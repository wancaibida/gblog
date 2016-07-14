package me.w2x.blog.service

import com.aliyun.oss.OSSClient
import grails.core.GrailsApplication

/**
 * Created by charles.chen on 7/11/16.
 */
class OssService {
    GrailsApplication grailsApplication

    void upload(String key, InputStream inputStream) {
        client.putObject(bucketName, key, inputStream)
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
