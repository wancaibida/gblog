package me.w2x.blog.service

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.async.Callback
import com.mashape.unirest.http.exceptions.UnirestException
import grails.transaction.Transactional
import me.w2x.blog.bean.PostEvent
import me.w2x.blog.bean.SiteEvent
import me.w2x.blog.domain.Post
import me.w2x.blog.enu.ActionTypes
import org.joda.time.DateTime

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

@Transactional
class StaticService {
    def grailsApplication

    static final SITE_MAP = Collections.unmodifiableMap([
            singlePost: 'posts/%s',
            category  : 'categorys/%s',
            index     : 'index',
            archive   : 'posts/date/%s/%s'
    ])

    OssService ossService

    BlockingQueue<SiteEvent> blockingDeque

    Thread staticWorker = new Thread(new Runnable() {
        @Override
        void run() {
            for (; ;) {
                SiteEvent event = blockingDeque.take()
                if (event instanceof PostEvent) {
                    staticByPost(((PostEvent) event).post, event.actionType)
                }
            }
        }
    })

    StaticService() {
        blockingDeque = new ArrayBlockingQueue<SiteEvent>(32)
        staticWorker.start()
    }

    def staticByPost(Post post, ActionTypes actionType) {
        def singlePageUrl = String.format(SITE_MAP.get('singlePost'), post.id)
        if (actionType == ActionTypes.DELETE) {
            ossService.delete(singlePageUrl)
        } else {
            staticByUrl(singlePageUrl)
        }

        def indexUrl = SITE_MAP.get('index')
        def categoryUrl = SITE_MAP.get('category')
        def archive = SITE_MAP.get('archive')

        staticByUrl(indexUrl)
        staticByUrl(String.format(categoryUrl, post.category.alias))

        DateTime time = new DateTime(post.dateCreated)
        staticByUrl(String.format(archive, time.getYear(), time.getMonthOfYear()))
    }

    String staticByUrl(String url) {
        Unirest.get(serverUrl + url).asBinaryAsync(new Callback<InputStream>() {
            @Override
            void completed(HttpResponse<InputStream> response) {
                ossService.upload(url, response.body)
                log.info("static ${url} success...")
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

    def triggerPostEvent(Post post, ActionTypes actionType) {
        blockingDeque.put(new PostEvent(post: post, actionType: actionType))
    }

    def triggerCategoryEvent(Category category, ActionTypes actionTypes) {

    }

    String getServerUrl() {
        println  grailsApplication.config.my.project.serverUrl as String
        grailsApplication.config.my.project.serverUrl as String
    }

}
