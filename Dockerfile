FROM centos:6.7
MAINTAINER charles.chen <wancaibida@gmail.com>
ADD docker-files/nginx.repo /etc/yum.repos.d/
RUN yum update -y
RUN yum install -y wget
RUN yum install -y unzip
RUN yum install -y nginx
RUN yum install -y git

ADD docker-files/nginx.conf /etc/nginx/nginx.conf


RUN wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u31-b13/jdk-8u31-linux-x64.rpm 

RUN rpm -ivh jdk-8*-linux-x64.rpm && rm jdk-8*-linux-x64.rpm

ENV JAVA_HOME /usr/java/latest

# Set customizable env vars defaults.
# Set Grails version (max version for this Docker image is: 2.5.3).
ENV GRAILS_VERSION 2.4.4

# Install Grails
WORKDIR /usr/lib/jvm
RUN wget https://github.com/grails/grails-core/releases/download/v$GRAILS_VERSION/grails-$GRAILS_VERSION.zip && \
    unzip grails-$GRAILS_VERSION.zip && \
    rm -rf grails-$GRAILS_VERSION.zip && \
    ln -s grails-$GRAILS_VERSION grails

# Setup Grails path.
ENV GRAILS_HOME /usr/lib/jvm/grails
ENV PATH $GRAILS_HOME/bin:$PATH

# Create App Directory
RUN mkdir /app

# Set Workdir
WORKDIR /app

RUN ADD . /app

EXPOSE 80

RUN /etc/init.d/nginx start

# Set Default Behavior
ENTRYPOINT ["grails run-app"]
