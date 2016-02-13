databaseChangeLog = {
    changeSet(author: "chengang (generated)", id: "1454509643008-3") {
        createTable(tableName: "t_role") {
            column(name: "id", type: "int8") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "t_role_pkey")
            }
            column(name: "s_authority", type: "VARCHAR(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "chengang (generated)", id: "1454509643008-12") {
        addUniqueConstraint(columnNames: "s_authority", constraintName: "t_role_s_authority_key", deferrable: "false", disabled: "false", initiallyDeferred: "false", tableName: "t_role")
    }

}
