class UrlMappings {

    //TODO change format
    static mappings = {

        "/"(controller: "post", action: 'index', method: 'GET')

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

            "/allMenus"(controller: "index") {
                action = [GET: 'allMenus', POST: 'allMenus']
            }

            "/menu"(controller: "index") {
                action = [GET: 'allMenus', POST: 'allMenus']
            }

            "/buttons"(controller: "index", action: "buttons", method: "GET")
            "/menus"(controller: "menuMgr") {
                action = [GET: 'index', POST: 'add']
            }
            "/menus/${menuId}"(controller: "menuMgr") {
                action = [POST: 'update', DELETE: 'delete']
            }
            "/menus/parents"(controller: "menuMgr") {
                action = [GET: 'parents']
            }
            "/menus/${menuId}/buttons"(controller: "menuMgr") {
                action = [GET: 'buttons', POST: 'addButton']
            }
            "/menus/${menuId}/buttons/${buttonId}"(controller: "menuMgr") {
                action = [POST: 'updateButton', DELETE: 'deleteButton']
            }
            "/menus/${menuId}/buttons/all"(controller: "menuMgr") {
                action = [POST: 'addButtons']
            }
            "/categorys"(controller: "categoryMgr") {
                action = [GET: 'index', POST: 'add']
            }
            "/categorys/parents"(controller: "categoryMgr") {
                action = [GET: 'parents']
            }
            "/categorys/${categoryId}"(controller: "categoryMgr") {
                action = [POST: 'update', DELETE: 'delete']
            }

            "/posts"(controller: "postMgr") {
                action = [GET: 'index', POST: 'add']
            }
            "/posts/${postId}"(controller: "postMgr") {
                action = [POST: 'update', DELETE: 'delete']
            }
            "/posts/view"(controller: "postMgr") {
                action = [GET: 'view']
            }

            "/drafts"(controller: "postMgr", action: 'saveDraft', method: 'post')

            "/drafts/$draftId"(controller: "postMgr", action: 'getDraft', method: 'get')

            "/password/reset"(controller: 'user') {
                action = [POST: 'updatePassword']
            }
        }


        "/posts"(controller: 'post') {
            action = [GET: 'index']
        }

        "/posts/${postId}"(controller: 'post') {
            action = [GET: 'view']
        }

        "/posts/date/${year}/${month}"(controller: 'post') {
            action = [GET: 'index']
        }

        "/categorys/${alias}"(controller: 'post') {
            action = [GET: 'index']
        }

        "/about"(controller: 'other', method: 'GET', action: 'about')

        "/contact"(controller: 'other', method: 'GET', action: 'contact')

        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }
    }
}
