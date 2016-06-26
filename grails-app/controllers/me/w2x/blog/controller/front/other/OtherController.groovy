package me.w2x.blog.controller.front.other

/**
 * Created by charles.chen on 2/3/16.
 */
class OtherController {

    static layout = 'postLayout'

    def about() {
        render(view: '/front/other/about')
    }

    def contact() {
        render(view: '/front/other/contact')
    }

    def robots() {
        render(
                text:
                        '''User-agent: *
Disallow: /admin/
Sitemap: http://w2x.me/sitemap.xml
''',
                contentType: 'text/plain'
        )
    }
}
