package me.w2x.blog.service

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import com.mashape.unirest.http.async.Callback
import com.mashape.unirest.http.exceptions.UnirestException
import grails.transaction.Transactional
import grails.util.Environment
import me.w2x.blog.bean.CategoryEvent
import me.w2x.blog.bean.PostEvent
import me.w2x.blog.bean.SiteEvent
import me.w2x.blog.domain.Category
import me.w2x.blog.domain.Post
import me.w2x.blog.enu.ActionTypes
import org.joda.time.DateTime

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit

@SuppressWarnings(['GrailsStatelessService', 'Instanceof'])
@Transactional
class StaticService {
    def grailsApplication

    static final SITE_MAP = Collections.unmodifiableMap([
            singlePost: 'posts/%s',
            category  : 'categorys/%s',
            index     : 'index.html',
            posts     : 'posts',
            archive   : 'posts/date/%02d/%02d',
            about     : 'about',
            contact   : 'contact',
            sitemap   : 'sitemap.xml',
            robots    : 'robots.txt',
    ])

    OssService ossService

    BlockingQueue<SiteEvent> blockingDeque

    Thread staticWorker = new Thread(new Runnable() {
        @Override
        void run() {
            for (; ;) {
                SiteEvent event = blockingDeque.take()
                //wait it refresh
                TimeUnit.SECONDS.sleep(5L)
                if (event instanceof PostEvent) {
                    processPostEvent(((PostEvent) event).post, event.actionType)
                } else if (event instanceof CategoryEvent) {
                    processCategoryEvent(((CategoryEvent) event).category, event.actionType)
                }
            }
        }
    })

    StaticService() {
        blockingDeque = new ArrayBlockingQueue<SiteEvent>(32)
        if (Environment.current != Environment.TEST) {
            staticWorker.start()
        }
    }

    def processPostEvent(Post post, ActionTypes actionType) {
        if (actionType == ActionTypes.DELETE) {
            def singlePageUrl = String.format(SITE_MAP.get('singlePost'), post.id)
            ossService.delete(singlePageUrl)
        } else {
            staticSinglePost(post)
        }

        def categoryUrl = SITE_MAP.get('category')
        def archive = SITE_MAP.get('archive')

        staticIndex()
        staticByUrl(String.format(categoryUrl, post.category.alias))

        DateTime time = new DateTime(post.dateCreated)
        staticByUrl(String.format(archive, time.year, time.monthOfYear))
    }

    def processCategoryEvent(Category category, ActionTypes actionType) {
        staticIndex()

        def categoryUrl = SITE_MAP.get('category')
        def formatedUrl = String.format(categoryUrl, category.alias)

        def affectedPost = []
        switch (actionType) {
            case ActionTypes.ADD:
                staticByUrl(formatedUrl)
                break
            case ActionTypes.EDIT:
                staticByUrl(formatedUrl)
                affectedPost = Post.findAllByCategory(category)

                break
            case ActionTypes.DELETE:
                ossService.delete(formatedUrl)
                affectedPost = Post.findAllByCategory(Category.get(1L))
                break
        }

        def affectedYearMonth = [:]

        if (affectedPost) {
            affectedPost.each { Post post ->
                def createTime = new DateTime(post.dateCreated)
                def affectedMonth = affectedYearMonth.get(createTime.year)
                if (affectedMonth == null) {
                    affectedMonth = []
                    affectedYearMonth.put(createTime.year, affectedMonth)
                }
                affectedMonth << createTime.monthOfYear

                staticSinglePost(post)
            }
        }

        if (affectedYearMonth) {
            affectedYearMonth.each { Integer year, Collection<Integer> months ->
                months?.unique { a, b -> a <=> b }?.each { Integer month ->
                    staticArchive(year, month)
                }
            }
        }
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

    String staticSinglePost(Post post) {
        def singlePageUrl = String.format(SITE_MAP.get('singlePost'), post.id)
        staticByUrl(singlePageUrl)
    }

    String staticArchive(Integer year, Integer month) {
        def archive = SITE_MAP.get('archive')
        if (year && month) {
            staticByUrl(String.format(archive, year, month))
        }
    }

    def staticIndex() {
        staticByUrl(SITE_MAP.index)
        staticByUrl(SITE_MAP.posts)
    }

    def triggerPostEvent(Post post, ActionTypes actionType) {
        if (post && actionType) {
            blockingDeque.put(new PostEvent(post: post, actionType: actionType))
        }
    }

    def triggerCategoryEvent(Category category, ActionTypes actionTypes) {
        if (category && actionTypes) {
            blockingDeque.put(new CategoryEvent(category: category, actionType: actionTypes))
        }
    }

    String getServerUrl() {
        grailsApplication.config.my.project.serverUrl as String
    }

    void staticAll() {
        Category.list().each {
            processCategoryEvent(it, ActionTypes.EDIT)
        }
    }

    void staticOther() {
        ['about', 'contact', 'robots', 'sitemap'].each {
            staticByUrl(SITE_MAP.get(it))
        }
    }
}
