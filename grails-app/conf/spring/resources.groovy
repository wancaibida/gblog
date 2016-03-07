import grails.plugin.springsecurity.SpringSecurityUtils
import me.w2x.blog.filter.CustomUsernamePasswordAuthenticationFilter
import me.w2x.blog.service.impl.InitServiceImpl

// Place your Spring DSL code here
beans = {
    initService(InitServiceImpl) {
    }

    authenticationProcessingFilter(CustomUsernamePasswordAuthenticationFilter) {
        authenticationManager = ref('authenticationManager')
        sessionAuthenticationStrategy = ref('sessionAuthenticationStrategy')
        authenticationSuccessHandler = ref('authenticationSuccessHandler')
        authenticationFailureHandler = ref('authenticationFailureHandler')
        rememberMeServices = ref('rememberMeServices')
        authenticationDetailsSource = ref('authenticationDetailsSource')
        requiresAuthenticationRequestMatcher = ref('filterProcessUrlRequestMatcher')
        usernameParameter = SpringSecurityUtils.securityConfig.apf.usernameParameter // username
        passwordParameter = SpringSecurityUtils.securityConfig.apf.passwordParameter // password
        continueChainBeforeSuccessfulAuthentication = SpringSecurityUtils.securityConfig.apf.continueChainBeforeSuccessfulAuthentication
        // false
        allowSessionCreation = SpringSecurityUtils.securityConfig.apf.allowSessionCreation // true
        postOnly = SpringSecurityUtils.securityConfig.apf.postOnly // true
        storeLastUsername = SpringSecurityUtils.securityConfig.apf.storeLastUsername // false
    }
}
