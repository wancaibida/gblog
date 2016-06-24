databaseChangeLog = {

    changeSet(author: "chengang (generated)", id: "1466778054849-1") {
        createTable(tableName: "t_draft") {
            column(name: "id", type: "BIGINT") {
                constraints(primaryKey: "true", primaryKeyName: "t_draftPK")
            }

            column(name: "n_category_id", type: "BIGINT")

            column(name: "post_id", type: "BIGINT")

            column(name: "s_raw", type: "TEXT")

            column(name: "s_title", type: "TEXT")

            column(name: "s_excerpt", type: "TEXT")

            column(name: "s_content", type: "TEXT")

            column(name: "d_created", type: "timestamp") {
                constraints(nullable: "false")
            }

            column(name: "d_updated", type: "timestamp") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "chengang (generated)", id: "1466778054849-5") {
        addForeignKeyConstraint(baseColumnNames: "n_category_id", baseTableName: "t_draft", constraintName: "FK_ljdpq8u7o3pvdoum50dag2spy", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "t_category")
    }

    changeSet(author: "chengang (generated)", id: "1466778054849-6") {
        addForeignKeyConstraint(baseColumnNames: "post_id", baseTableName: "t_draft", constraintName: "FK_pg4ub70vft5c2iioogxvi6ygq", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "t_post")
    }

}
