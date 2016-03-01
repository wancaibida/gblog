package me.w2x.blog.controller.common

import grails.plugin.springsecurity.SpringSecurityUtils
import me.w2x.blog.bean.QueryResult
import me.w2x.blog.exception.CommandException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

import javax.servlet.http.HttpServletResponse

abstract class BaseController {
    def baseService

    def getGridData(QueryResult<?> queryResult) {
        [Total: queryResult.total, Rows: queryResult.data]
    }

    def getGrid(Class domainClass) {
        def rawWhere = params.where as String
        def page = params.int('page', 1)
        def pageSize = params.int('pagesize', 10)
        def sort = params.sortname as String
        def sortOrder = params.sortorder as String

        baseService.getGridData(domainClass, rawWhere, page, pageSize, sort, sortOrder)
    }

    def handleValidation(command) {
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

    def handleCommandException(CommandException exception) {
        def errors = exception.errors
        renderJsonResponse(HttpServletResponse.SC_BAD_REQUEST, message(code: errors[0]), null)
    }

    def renderJsonResponse(statusCode, errorMessage, errorCode) {
        response.status = statusCode
        def data = [:]
        data.errorId = response.status
        data.errorMessage = errorMessage
        if (errorCode) {
            data.errorId = errorCode
        }

        render(contentType: 'application/json', {
            data
        })
    }

    protected ConfigObject getConf() {
        SpringSecurityUtils.securityConfig
    }


    def Authentication getAuthentication() {
        SecurityContextHolder.context?.authentication
    }


}
