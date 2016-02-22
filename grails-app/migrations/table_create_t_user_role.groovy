databaseChangeLog = {
    changeSet(author: "chengang (generated)", id: "1454509643008-10") {
        createTable(tableName: "t_user_role") {
            column(name: "n_user_id", type: "int8") {
                constraints(nullable: "false")
            }

            column(name: "n_role_id", type: "int8") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "chengang (generated)", id: "1454509643008-11") {
        addPrimaryKey(columnNames: "n_role_id, n_user_id", constraintName: "t_user_role_pkey", tableName: "t_user_role")
    }

}

