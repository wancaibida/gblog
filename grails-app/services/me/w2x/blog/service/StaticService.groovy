package me.w2x.blog.service

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.async.Callback
import com.mashape.unirest.http.exceptions.UnirestException
import grails.transaction.Transactional
import me.w2x.blog.domain.Post
import me.w2x.blog.enu.ActionTypes
import org.apache.commons.io.IOUtils

@Transactional
class StaticService {

    OssService ossService

    String staticByUrl(String url) {
        Unirest.get(url).asBinaryAsync(new Callback<InputStream>() {
            @Override
            void completed(HttpResponse<InputStream> response) {
                println IOUtils.toString(response.body)
            }

            @Override
            void failed(UnirestException e) {
                log.error(e)
            }

            @Override
            void cancelled() {
                log.warn('cancelled')
            }
        })
    }

    def triggerPostEvent(Post post, ActionTypes actionTypes) {

    }

    def triggerCategoryEvent(Category category, ActionTypes actionTypes) {

    }

}
