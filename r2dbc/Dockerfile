FROM openjdk:8-jre-alpine
ENV APP_HOME /app
COPY target/*.jar $APP_HOME/boot-postgres.jar
COPY logback.xml $APP_HOME/
WORKDIR $APP_HOME

ENTRYPOINT ["sh", "-c"]
CMD ["java -Dspring.profiles.active=docker -Dlogging.config=logback.xml -jar boot-postgres.jar"]
