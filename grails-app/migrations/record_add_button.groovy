databaseChangeLog = {

    changeSet(author: "chengang", id: "20160102-0001") {
        grailsChange {
            change {
                sql.execute("INSERT INTO t_button(id,s_name,s_alias,s_icon,s_menu_alias,n_sort) VALUES (1,'添加','add','add.gif','menus',0)")
                sql.execute("INSERT INTO t_button(id,s_name,s_alias,s_icon,s_menu_alias,n_sort) VALUES (2,'修改','modify','modify.gif','menus',1)")
                sql.execute("INSERT INTO t_button(id,s_name,s_alias,s_icon,s_menu_alias,n_sort) VALUES (3,'删除','delete','delete.gif','menus',2)")
                sql.execute("INSERT INTO t_button(id,s_name,s_alias,s_icon,s_menu_alias,n_sort) VALUES (4,'按钮管理','button','home.gif','menus',3)")

                sql.execute("INSERT INTO t_button(id,s_name,s_alias,s_icon,s_menu_alias,n_sort) VALUES (5,'添加','add','add.gif','postList',0)")
                sql.execute("INSERT INTO t_button(id,s_name,s_alias,s_icon,s_menu_alias,n_sort) VALUES (6,'修改','modify','modify.gif','postList',1)")
                sql.execute("INSERT INTO t_button(id,s_name,s_alias,s_icon,s_menu_alias,n_sort) VALUES (7,'删除','delete','delete.gif','postList',2)")

                sql.execute("INSERT INTO t_button(id,s_name,s_alias,s_icon,s_menu_alias,n_sort) VALUES (8,'添加','add','add.gif','categoryMgr',0)")
                sql.execute("INSERT INTO t_button(id,s_name,s_alias,s_icon,s_menu_alias,n_sort) VALUES (9,'修改','modify','modify.gif','categoryMgr',1)")
                sql.execute("INSERT INTO t_button(id,s_name,s_alias,s_icon,s_menu_alias,n_sort) VALUES (10,'删除','delete','delete.gif','categoryMgr',2)")
            }

            rollback {
                sql.execute("DELETE FROM t_button WHERE s_menu_alias = 'menus'")
                confirm 'rollback confirmation message'
            }
        }
    }

    changeSet(author: "chengang", id: "20160124-0001") {
        grailsChange {
            change {
                sql.execute("insert into t_category ( id, s_name, s_alias, n_parent_id) values ( '1', 'Java技术', 'java', '0')")
            }

        }
    }

    changeSet(author: "chengang", id: "20160715-0001") {
        grailsChange {
            change {
                sql.execute("INSERT INTO t_button(id,s_name,s_alias,s_icon,s_menu_alias,n_sort) VALUES (11,'全站静态化','staticAll','milestone.gif','postList',4)")
            }
        }
    }
}
