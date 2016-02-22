databaseChangeLog = {

    changeSet(author: "chengang", id: "20160205-0001") {
        grailsChange {
            change {
                sql.execute("insert into t_role (id,s_authority) values (1,'ROLE_ADMIN')")
            }
        }
    }

}
