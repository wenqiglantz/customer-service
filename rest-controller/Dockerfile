FROM amazoncorretto:11-alpine-jdk
MAINTAINER github.com/wenqiglantz
EXPOSE 9100
COPY mssql.jks /tmp/mssql.jks
COPY target/customer-service-exec.jar customer-service-exec.jar
ENTRYPOINT ["java","-jar","/customer-service-exec.jar", "-p", "9100"]
