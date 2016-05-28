package me.w2x.blog.controller.front.other

import grails.core.GrailsApplication
import grails.web.Action
import groovy.xml.MarkupBuilder

import java.lang.reflect.Method

@SuppressWarnings(['NestedBlockDepth', 'TrailingComma'])
class SitemapController {
    GrailsApplication grailsApplication

    /**
     * If you want to exclude any controllers in the sitemap, especially Error controllers and services etc, include them in this array
     */
    def controllerNamesToExclude = ['sitemap', 'error']

    /**
     * If you want to certain actions excluded, include them in this array. All actions with this name will be ignored
     */
    def actionsToExclude = ['getSearchFilter', 'getCategorys', 'getRecentPosts', 'getArchives', 'view']

    def index() {
        StringWriter writer = new StringWriter()
        MarkupBuilder mkp = new MarkupBuilder(writer)
        mkp.mkp.xmlDeclaration(version: '1.0', encoding: 'UTF-8')
        mkp.urlset('xmlns': 'http://www.sitemaps.org/schemas/sitemap/0.9',
                'xmlns:xsi': 'http://www.w3.org/2001/XMLSchema-instance',
                'xsi:schemaLocation': 'http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd') {

            mkp.url {
                loc(g.createLink(absolute: true, uri: '/'))
                changefreq('weekly')
                priority(0.8)
            }

            grailsApplication.controllerClasses.each { controller ->
                Class controllerClass = controller.clazz

                // skip controllers in plugins
                if (controllerClass.name.startsWith('me.w2x.blog.controller.front.post') && !controllerNamesToExclude.contains(controller.logicalPropertyName)) {
                    String logicalControllerName = controller.logicalPropertyName

                    // get the actions defined as methods (Grails 2)
                    controllerClass.methods.each { Method method ->

                        if (method.getAnnotation(Action) && !actionsToExclude.contains(method.name)) {

                            mkp.url {
                                loc(g.createLink(absolute: true, controller: logicalControllerName, action: method.name))
                                changefreq('weekly')
                                priority(0.8)
                            }
                        }
                    }
                }
            }
        }
        render(text: writer.toString(), contentType: 'text/xml', encoding: 'UTF-8')
    }
}
