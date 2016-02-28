package me.w2x.blog.service

import grails.transaction.Transactional
import groovy.json.JsonSlurper
import org.apache.commons.lang3.StringUtils
import org.apache.commons.lang3.math.NumberUtils
import org.hibernate.Query

@Transactional
class BaseService {

    private static final String PARAM_PREFIX_TOKEN = ":"
    private static final String GROUP_LEFT_TOKEN = "("
    private static final String GROUP_RIGHT_TOKEN = ")"
    private static final String LIKE_TOKEN = "%"

    def grailsApplication

    def sessionFactory

    def getGridData(String _countHql, String _queryHql, String where, int page, int pageSize, String sort, String sortOrder) {
        def sufix = new StringBuilder(' where ')
        def filterGroup = [:]
        if (where) {
            filterGroup = new JsonSlurper().parseText(where) as Map
        }
        def paramMap = [:]
        sufix.append(translateGroup(filterGroup, paramMap))

        def countHql = "${_countHql} ${sufix}"
        def queryHql = "${_queryHql} ${sufix}"

        def session = sessionFactory.currentSession
        Query countQuery = session.createQuery(countHql)
        paramMap.each { key, value ->
            countQuery.setParameter(key, value)
        }

        def total = countQuery.uniqueResult()

        if (!total || ((Long) total) == 0L) {
            return [Total: 0, Rows: Collections.emptyList()]
        }

        if (sort && sortOrder) {
            queryHql += " order by ${sort} ${sortOrder}"
        }

        Query listQuery = session.createQuery(queryHql)
        paramMap.each { key, value ->
            listQuery.setParameter(key, value)
        }
        listQuery.setFirstResult((page - 1) * pageSize)
        listQuery.setMaxResults(pageSize)

        def list = listQuery.list()
        [Total: total, Rows: list]
    }


    def getGridData(String domainClassName, String where, int page, int pageSize, String sort, String sortOrder) {
        def fullClassName = "me.w2x.blog.domain.${domainClassName}".toString()
        def domainClass = grailsApplication.getDomainClass(fullClassName).clazz
        def simpleName = domainClass.simpleName

        def countHql = "select count(*) from ${simpleName}"
        def queryHql = "from ${simpleName}"

        getGridData(countHql, queryHql, where, page, pageSize, sort, sortOrder)
    }


    def getSelectData(String domainClassName, String idName, String textName) {
        def domainClass = grailsApplication.getDomainClass(domainClassName).clazz
        domainClass.executeQuery("SELECT ${idName} AS id,${textName} AS text FROM ${domainClass.simpleName}")
    }

    private translateGroup(Map filterGroup, Map params) {
        if (filterGroup == null) {
            return " 1=1 ";
        }

        def op = filterGroup.op;

        StringBuilder where = new StringBuilder();

        boolean isStart = true;
        where.append(GROUP_LEFT_TOKEN);

        List<Map> rules = filterGroup.rules;
        if (rules) {
            for (Map rule : rules) {
                if (!isStart) {
                    where.append(getOperatorText(op));
                }

                where.append(translateRule(rule, params));
                isStart = false;
            }
        }

        List<Map> groups = filterGroup.groups;
        if (groups) {
            for (Map subGroup : groups) {
                if (!isStart) {
                    where.append(getOperatorText(op));
                }

                where.append(translateGroup(subGroup, params));
                isStart = false;
            }
        }

        where.append(GROUP_RIGHT_TOKEN);
        if (isStart) {
            return " 1=1 ";
        }

        return where.toString();

    }

    private translateRule(Map filterRule, Map<String, Object> params) {
        if (filterRule == null) {
            return " 1=1 ";
        }

        String field = filterRule.field;
        String op = filterRule.op;
        String type = filterRule.type;
        String value = filterRule.value;

        def builder = new StringBuilder();

        if (StringUtils.contains(field, "_")) {
            builder.append(StringUtils.replace(field, "_", "."));
        } else {
            builder.append(field);
        }

        //插入op
        builder.append(getOperatorText(op));

        switch (filterRule.op) {
            case 'like':
                if (!StringUtils.startsWith(value, LIKE_TOKEN)) {
                    value = LIKE_TOKEN + value;
                }

                if (!StringUtils.endsWith(value, LIKE_TOKEN)) {
                    value = value + LIKE_TOKEN;
                }
                builder.append(PARAM_PREFIX_TOKEN).append(field);

                params.put(field, value);
                break;

            case 'endwith':
                if (!StringUtils.startsWith(value, LIKE_TOKEN)) {
                    value = LIKE_TOKEN + value;
                }
                builder.append(PARAM_PREFIX_TOKEN).append(field);

                params.put(field, value);
                break;

            case 'startwith':
                if (!StringUtils.endsWith(value, LIKE_TOKEN)) {
                    value = value + LIKE_TOKEN;
                }
                builder.append(PARAM_PREFIX_TOKEN).append(field);
                params.put(field, value);
                break;

            case 'equal':
            case 'notequal':
            case 'greaterorequal':
            case 'greater':
            case 'lessorequal':
            case 'less':
                builder.append(PARAM_PREFIX_TOKEN).append(field);
                params.put(field, createParam(type, value));
                break;

            case 'in':
            case 'notin':
                String[] arr = StringUtils.split(value, ",");
                builder.append(GROUP_LEFT_TOKEN);
                builder.append(PARAM_PREFIX_TOKEN).append(field);
                builder.append(GROUP_RIGHT_TOKEN);

                List<Object> list = new ArrayList<Object>(arr.length);

                for (String str : arr) {
                    list.add(createParam(type, str));
                }

                params.put(field, list);
                break;
        }

        return builder.toString();
    }

    private createParam(String type, String value) {
        type = type.toLowerCase();
        if ("number".equals(type)) {
            return NumberUtils.toLong(value);
        }

        if ("int".equals(type)) {
            return NumberUtils.toInt(value);
        }

        if ("string".equals(type)) {
            return value.toString();
        }

        return value;
    }

    private static String getOperatorText(String op) {
        def operator;
        switch (op) {
            case 'equal':
                operator = '='
                break
            case 'notequal':
                operator = '<>'
                break
            case 'startwith':
                operator = 'LIKE'
                break
            case 'endwith':
                operator = 'LIKE'
                break
            case 'like':
                operator = 'LIKE'
                break
            case 'greater':
                operator = '>'
                break
            case 'greaterorequal':
                operator = '>='
                break
            case 'less':
                operator = '<'
                break
            case 'lessorequal':
                operator = '<='
                break
            case 'in':
                operator = 'IN'
                break
            case 'notin':
                operator = 'NOT IN'
                break
            case 'isnull':
                operator = 'IS NULL'
                break
            case 'isnotnull':
                operator = 'IS NOT NULL'
                break
            case 'and':
                operator = 'AND'
                break
            case 'or':
                operator = 'OR'
                break
            default:
                operator = '='
                break
        }

        " ${operator} "
    }
}
