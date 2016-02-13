databaseChangeLog = {

    changeSet(author: "chengang (generated)", id: "1454506785790-8") {
        createTable(tableName: "t_user") {
            column(name: "id", type: "int8") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "t_user_pkey")
            }

            column(name: "s_username", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "s_password", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }

            column(name: "b_enabled", type: "bool")

            column(name: "b_account_expired", type: "bool")

            column(name: "b_account_locked", type: "bool")

            column(name: "b_password_expired", type: "bool")
        }
    }

    changeSet(author: "chengang (generated)", id: "1454506785790-9") {
        addUniqueConstraint(columnNames: "s_username", constraintName: "uk_t_user", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "t_user")
    }
}
