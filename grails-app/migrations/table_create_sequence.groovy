databaseChangeLog = {

    changeSet(author: "chengang", id: "20160111-1") {
        createTable(tableName: "sequence_definition") {
            column(name: "id", type: "int8") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sequence_defiPK")
            }

            column(name: "version", type: "int8") {
                constraints(nullable: "false")
            }

            column(name: "format", type: "varchar(100)")

            column(name: "name", type: "varchar(100)") {
                constraints(nullable: "false")
            }

            column(name: "tenant_id", type: "int8")
        }
    }

    changeSet(author: "chengang", id: "20160111-2") {
        createTable(tableName: "sequence_number") {
            column(name: "id", type: "int8") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "sequence_numbPK")
            }

            column(name: "version", type: "int8") {
                constraints(nullable: "false")
            }

            column(name: "definition_id", type: "int8") {
                constraints(nullable: "false")
            }

            column(name: "sequence_group", type: "varchar(40)")

            column(name: "sequence_number", type: "int8") {
                constraints(nullable: "false")
            }
        }
    }


    changeSet(author: "chengang", id: "20160111-40") {
        createIndex(indexName: "unique_name", tableName: "sequence_definition", unique: "true") {
            column(name: "tenant_id")

            column(name: "name")
        }
    }

    changeSet(author: "chengang", id: "20160111-41") {
        createIndex(indexName: "unique_sequence_group", tableName: "sequence_number", unique: "true") {
            column(name: "definition_id")

            column(name: "sequence_group")
        }
    }

    changeSet(author: "chengang", id: "20160111-34") {
        addForeignKeyConstraint(baseColumnNames: "definition_id", baseTableName: "sequence_number", constraintName: "FK_bmvoamvmdn1i7m29bl5odvjo7", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "sequence_definition", referencesUniqueColumn: "false")
    }

    changeSet(author: "chengang", id: "20160111-35") {
        createSequence(schemaName: "public", sequenceName: "hibernate_sequence")
    }
}
