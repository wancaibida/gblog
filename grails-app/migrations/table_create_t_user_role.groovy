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

    changeSet(author: "chengang (generated)", id: "1466777601782-3") {
        addForeignKeyConstraint(baseColumnNames: "n_role_id", baseTableName: "t_user_role", constraintName: "FK_9pneq9opk7kb8oh4vsycn6ae9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "t_role")
    }

    changeSet(author: "chengang (generated)", id: "1466777601782-4") {
        addForeignKeyConstraint(baseColumnNames: "n_user_id", baseTableName: "t_user_role", constraintName: "FK_g5r7kb60s6eajtuoois6bigxs", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "t_user")
    }

}

