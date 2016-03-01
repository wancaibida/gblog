databaseChangeLog = {
    changeSet(author: "chengang (generated)", id: "1456837255722-9") {
        createTable(tableName: "t_session") {
            column(name: "s_series", type: "TEXT") {
                constraints(nullable: "false")
            }

            column(name: "s_username", type: "VARCHAR(64)")

            column(name: "s_token", type: "VARCHAR(64)")

            column(name: "d_last_used", type: "TIMESTAMP WITHOUT TIME ZONE")
        }
    }

    changeSet(author: "chengang (generated)", id: "1456837255722-19") {
        addPrimaryKey(columnNames: "s_series", constraintName: "t_session_pkey", tableName: "t_session")
    }
}