#### Build Status
- [![Build Status](https://travis-ci.org/wancaibida/gblog.svg?branch=master)](https://travis-ci.org/wancaibida/gblog)

###Configurations
|Name |Description   |Required   |
| ------------ | ------------ | ------------ |
|CDN_URL   | cdn url  |Yes   |
|ADMIN_INITIAL_PASSWORD   | Admin password  | Yes  |
|JDBC_CONNECTION_USERNAME   | Databse user name  | Yes  |
|JDBC_CONNECTION_PASSWORD   |Databse password   |Yes   |
| ACCESS_KEY  | Qiniu access key  |Yes   |
|  SECRET_KEY |  Qiniu secret key |Yes   |
|BUCKETNAME|Qiniu bucket name|Yes|
|DUMP_PATH|Databse dump file path|Yes|



###How to startup
export GRAILS_OPTS=&quot;-Dfile.encoding=UTF-8&quot;

./grailsw clean
./grailsw prod run-app