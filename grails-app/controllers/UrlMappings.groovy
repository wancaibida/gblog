

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?" {
            constraints {
                // apply constraints here
            }
        }

        "/"(controller: "post") {
            action = [GET: 'index']
        }
        "404"(view: '/front/error/404')
        "500"(view: '/front/error/500')

        group "/admin", {
            "/"(controller: "index", action: "index", method: "GET")

            "/user/auth"(controller: "user") {
                action = [GET: 'auth']
            }

            "/user/authfail"(controller: "user") {
                action = [GET: 'authfail']
            }

            "/user/j_spring_security_check"(controller: "user") {
                action = [GET: 'doLogin']
            }

            "/menu"(controller: "index") {
                action = [GET: 'menus', POST: 'menus']
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
            "/menus/icons"(controller: "menuMgr", action: "icons", method: "GET")
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

        "/about"(controller: 'other') {
            action = [GET: 'about']
        }

        "/contact"(controller: 'other') {
            action = [GET: 'contact']
        }
    }
}
