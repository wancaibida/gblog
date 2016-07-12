databaseChangeLog = {

    changeSet(author: "chengang (generated)", id: "1468328456529-1") {
        addColumn(tableName: "t_category") {
            column(name: "b_is_deleted", type: "boolean") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "chengang (generated)", id: "1468328456529-2") {
        addColumn(tableName: "t_post") {
            column(name: "b_is_deleted", type: "boolean") {
                constraints(nullable: "true")
            }
        }
    }

    changeSet(author: "chengang (generated)", id: "1468328456529-3") {
        grailsChange {
            change {
                sql.execute('update t_category set b_is_deleted = false where b_is_deleted is null')
                sql.execute('update t_post set b_is_deleted = false where b_is_deleted is null')
            }
        }
    }

    changeSet(author: "chengang (generated))", id: "1468328456529-4") {
        addNotNullConstraint(columnDataType: "boolean", columnName: "b_is_deleted", tableName: "t_category")
    }

    changeSet(author: "chengang (generated))", id: "1468328456529-5") {
        addNotNullConstraint(columnDataType: "boolean", columnName: "b_is_deleted", tableName: "t_post")
    }
}