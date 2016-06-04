import grails.util.Environment
import me.w2x.blog.util.PropertyUtils

// See http://logback.qos.ch/manual/groovy.html for details on configuration
appender('CONSOLE', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%level %logger - %msg%n"
    }
}

appender("FILE", RollingFileAppender) {
    encoder(PatternLayoutEncoder) {
        Pattern = "%d %level %thread %mdc %logger - %m%n"
    }
    rollingPolicy(TimeBasedRollingPolicy) {
        FileNamePattern = "/${PropertyUtils.getProp('LOG_PATH') ?: 'tmp'}/blog_%d{yyyy_MM_dd}.log"
    }
}

switch (Environment.current) {
    case [Environment.DEVELOPMENT, Environment.TEST]:
        root(INFO, ['CONSOLE'])
        logger("StackTrace", INFO, ['CONSOLE'], false)
        break
    case Environment.PRODUCTION:
        root(ERROR, ['FILE'])
        break
    default:
        root(INFO, ['CONSOLE'])
        break
}