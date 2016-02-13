databaseChangeLog = {

    changeSet(author: "chengang", id: "20151226-0001") {
        createTable(tableName: "t_button") {
            column(name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pk_t_button")
            }

            column(name: "s_menu_alias", type: "character varying(255)") {
                constraints(nullable: "false")
            }

            column(name: "s_alias", type: "character varying(255)") {
                constraints(nullable: "false")
            }

            column(name: "s_name", type: "character varying(255)") {
                constraints(nullable: "false")
            }

            column(name: "s_icon", type: "character varying(255)") {
                constraints(nullable: "false")
            }

            column(name: "n_sort", type: "integer") {
                constraints(nullable: "false")
            }

        }
    }

}
