#### Build Status
- [![Build Status](https://travis-ci.org/wancaibida/gblog.svg?branch=master)](https://travis-ci.org/wancaibida/gblog)


###How to startup
export GRAILS_OPTS="-Dfile.encoding=UTF-8"
grails clean
CDN_URL=[CDN_URL] password=[ADMIN_INITIAL_PASSWORD] JDBC_CONNECTION_USERNAME=[DB_NAME] JDBC_CONNECTION_PASSWORD=[DB_PASSWORD] grails prod run-app