class UrlMappings {

    //TODO change format
    static mappings = {

        "/"(controller: "post", action: 'index', method: 'GET')
        "/index"(controller: "post", action: 'index', method: 'GET')

        "/sitemap.xml"(controller: 'sitemap', action: 'index', method: 'GET')

        "/robots.txt"(controller: 'other', action: 'robots', method: 'GET')

        "404"(view: '/front/error/404')
        "500"(view: '/front/error/500')

        group "/admin", {
            "/"(controller: "index", action: "index", method: "GET")

            group "/login", {
                "/auth"(controller: "user", action: "auth", method: "GET")
                "/authfail"(controller: "user", action: "authfail", method: "GET")
                "/authsuccess"(controller: "user", action: "authsuccess", method: "GET")
            }

            "/allMenus"(controller: "index", action: 'allMenus', method: 'GET')

            "/menu"(controller: "index", action: 'allMenus', method: 'GET')

            "/buttons"(controller: "index", action: "buttons", method: "GET")

            "/menus"(controller: "menuMgr", action: 'index', method: 'GET')
            "/menus"(controller: "menuMgr", action: 'add', method: 'POST')

            "/menus/${menuId}"(controller: "menuMgr", action: 'update', method: 'POST')
            "/menus/${menuId}"(controller: "menuMgr", action: 'delete', method: 'DELETE')

            "/menus/parents"(controller: "menuMgr", action: 'parents', method: 'GET')

            "/menus/${menuId}/buttons"(controller: "menuMgr", action: 'buttons', method: 'GET')
            "/menus/${menuId}/buttons"(controller: "menuMgr", action: 'addButton', method: 'POST')

            "/menus/${menuId}/buttons/${buttonId}"(controller: "menuMgr", action: 'updateButton', method: 'POST')
            "/menus/${menuId}/buttons/${buttonId}"(controller: "menuMgr", action: 'deleteButton', method: 'DELETE')

            "/menus/${menuId}/buttons/all"(controller: "menuMgr", action: 'addButtons', method: 'POST')

            "/categorys"(controller: "categoryMgr", action: 'index', method: 'GET')
            "/categorys"(controller: "categoryMgr", action: 'add', method: 'POST')

            "/categorys/parents"(controller: "categoryMgr", action: 'parents', method: 'GET')

            "/categorys/${categoryId}"(controller: "categoryMgr", action: 'update', method: 'POST')
            "/categorys/${categoryId}"(controller: "categoryMgr", action: 'delete', method: 'DELETE')


            "/posts"(controller: "postMgr", action: 'index', method: 'GET')
            "/posts"(controller: "postMgr", action: 'add', method: 'POST')

            "/posts/${postId}"(controller: "postMgr", action: 'update', method: 'POST')
            "/posts/${postId}"(controller: "postMgr", action: 'delete', method: 'DELETE')

            "/posts/view"(controller: "postMgr", action: 'view', method: 'GET')

            "/drafts"(controller: "postMgr", action: 'saveDraft', method: 'POST')

            "/drafts/$draftId"(controller: "postMgr", action: 'getDraft', method: 'GET')

            "/password/reset"(controller: 'user', action: 'updatePassword', method: 'POST')
        }


        "/posts"(controller: 'post', action: 'index', method: 'GET')

        "/posts/${postId}"(controller: 'post', action: 'view', method: 'GET')

        "/posts/date/${year}/${month}"(controller: 'post', action: 'index', method: 'GET')

        "/categorys/${alias}"(controller: 'post', action: 'index', method: 'GET')

        "/about"(controller: 'other', method: 'GET', action: 'about')

        "/contact"(controller: 'other', method: 'GET', action: 'contact')

        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }
    }
}
