/**
 * User: Gang Chen
 * Date: 2015/11/29 22:36
 */
modules = {
    core {
        dependsOn 'jquery, utils'
        resource url: '/js/core.js', disposition: 'head'
        resource url: '/js/ui.js'
        resource url: '/css/main.css'
        resource url: '/css/branding.css'
        resource url: '/css/print.css', attrs: [media: 'print']
    }

    utils {
        dependsOn 'jquery'

        resource url: '/js/utils.js'
    }


}
