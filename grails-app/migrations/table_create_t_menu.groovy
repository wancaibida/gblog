databaseChangeLog = {

    changeSet(author: "chengang", id: "20151205-0001") {
        createTable(tableName: "t_menu") {
            column(name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pk_t_menu")
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

            column(name: "n_sort", type: "integer") {
                constraints(nullable: "false")
            }

            column(name: "s_url", type: "character varying(255)") {
                constraints(nullable: "true")
            }

            column(name: "s_icon", type: "character varying(255)") {
                constraints(nullable: "false")
            }
        }
    }

}
