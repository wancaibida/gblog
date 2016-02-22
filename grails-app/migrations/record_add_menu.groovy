databaseChangeLog = {

    changeSet(author: "chengang", id: "20151205-0004") {
        grailsChange {
            change {
                sql.execute("INSERT INTO t_menu ( ID, n_parent_id,s_alias,s_name,s_url,n_sort,s_icon ) VALUES ( 1, 0, 'post', '文章管理', '', 3, 'edit.gif' )")
                sql.execute("INSERT INTO t_menu ( ID, n_parent_id,s_alias,s_name,s_url,n_sort,s_icon ) VALUES ( 2, 1, 'postList', '所有文章', 'posts/', 3, 'feed.gif' )")
                sql.execute("INSERT INTO t_menu ( ID, n_parent_id,s_alias,s_name,s_url,n_sort,s_icon ) VALUES ( 3, 1, 'postMgr', '发布文章', 'postMgr/view.do', 4, 'plus.gif' )")
                sql.execute("INSERT INTO t_menu ( ID, n_parent_id,s_alias,s_name,s_url,n_sort,s_icon ) VALUES ( 4, 1, 'categoryMgr', '分类目录', 'categorys', 2, 'calendar.gif' )")
            }
        }
    }

    changeSet(author: "chengang", id: "20151205-0006") {
        grailsChange {
            change {
                sql.execute("INSERT INTO t_menu ( ID, n_parent_id,s_alias,s_name,s_url,n_sort,s_icon ) VALUES ( 6, 0, 'sys', '系统管理', '', 1, 'settings.gif' )")
                sql.execute("INSERT INTO t_menu ( ID, n_parent_id,s_alias,s_name,s_url,n_sort,s_icon ) VALUES ( 7, 6, 'menus', '菜单管理', 'menus', 1, 'view.gif' )")
                sql.execute("INSERT INTO t_menu ( ID, n_parent_id,s_alias,s_name,s_url,n_sort,s_icon ) VALUES ( 8, 6, 'users', '用户管理', 'users', 4, 'member.gif' )")
            }
        }
    }

}
