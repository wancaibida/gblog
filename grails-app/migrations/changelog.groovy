/**
 * User: Gang Chen
 * Date: 2015/11/30 21:33
 */
databaseChangeLog = {
    include file: 'table_create_t_user.groovy'
    include file: 'table_create_t_role.groovy'
    include file: 'table_create_t_user_role.groovy'
    include file: 'table_create_t_post.groovy'
    include file: 'table_create_t_menu.groovy'
    include file: 'table_create_t_category.groovy'
    include file: 'table_create_t_button.groovy'
    include file: 'table_create_sequence.groovy'
    include file: 'record_add_menu.groovy'
    include file: 'record_add_button.groovy'
    include file: 'record_add_role.groovy'
}
