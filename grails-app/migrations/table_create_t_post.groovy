databaseChangeLog = {

    changeSet(author: "chengang", id: "20151130-0001") {
        createTable(tableName: "t_post") {
            column(name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "pk_t_post")
            }

            column(name: "n_category_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "s_title", type: "character varying(255)") {
                constraints(nullable: "false")
            }

            column(name: "s_raw", type: "text") {
                constraints(nullable: "false")
            }

            column(name: "s_content", type: "text") {
                constraints(nullable: "false")
            }

            column(name: "s_excerpt", type: "character varying") {
                constraints(nullable: "false")
            }

            column(name: "n_comment_count", type: "integer") {
                constraints(nullable: "false")
            }

            column(name: "d_created", type: "timestamp without time zone") {
                constraints(nullable: "false")
            }

            column(name: "d_updated", type: "timestamp without time zone") {
                constraints(nullable: "false")
            }

            column(name: "n_status", type: "integer") {
                constraints(nullable: "false")
            }
        }
    }

}
