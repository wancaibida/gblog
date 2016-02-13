databaseChangeLog = {

    changeSet(author: "chengang", id: "20151130-0003") {
        createTable(tableName: "t_category") {
            column(name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pk_t_category")
            }

            column(name: "n_parent_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "s_name", type: "character varying(255)") {
                constraints(nullable: "false")
            }

            column(name: "s_alias", type: "character varying(255)") {
                constraints(nullable: "false")
            }
        }
    }
}
