FROM openjdk:11
MAINTAINER ciceroinfo.com
WORKDIR /usr/src/app
COPY ./target/transaction-handler.jar /usr/src/app/transaction-handler.jar
ENTRYPOINT [ "java", "-jar", "transaction-handler.jar" ]

