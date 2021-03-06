package me.w2x.blog.controller.common

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import me.w2x.blog.bean.QueryResult
import me.w2x.blog.exception.CommandException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

import javax.servlet.http.HttpServletResponse

class BaseController {

    static final String CONTENT_TYPE_JSON = 'application/json'

    def baseService

    protected getGridData(QueryResult<?> queryResult) {
        [Total: queryResult.total, Rows: queryResult.data]
    }

    protected getGrid(Class domainClass) {
        def rawWhere = params.where as String
        def page = params.int('page', 1)
        def pageSize = params.int('pagesize', 10)
        def sort = params.sortname as String
        def sortOrder = params.sortorder as String

        baseService.getGridData(domainClass, rawWhere, page, pageSize, sort, sortOrder)
    }

    protected handleValidation(command) {
        def errors = []
        command.errors.allErrors.each {
            def field = it.field
            String objectName = it.objectName
            def simpleName = objectName.tokenize('.')[-1]
            def code = it.code
            String errorCode = "error.${simpleName}.${field}.${code}"
            errors << errorCode
        }
        throw new CommandException(errors)
    }

    protected handleCommandException(CommandException exception) {
        def errors = exception.errors
        buildResponse(HttpServletResponse.SC_BAD_REQUEST, message(code: errors[0]), null)
    }

    @SuppressWarnings('FactoryMethodName')
    protected buildResponse(statusCode, errorMessage) {
        buildResponse(statusCode, errorMessage, null)
    }

    @SuppressWarnings('FactoryMethodName')
    protected buildResponse(statusCode, errorMessage, errorCode) {
        response.status = statusCode
        def data = [:]
        data.errorId = response.status
        data.errorMessage = errorMessage
        if (errorCode) {
            data.errorId = errorCode
        }
        render data as JSON
    }

    protected ConfigObject getConf() {
        SpringSecurityUtils.securityConfig
    }

    protected Authentication getAuthentication() {
        SecurityContextHolder.context?.authentication
    }

}
