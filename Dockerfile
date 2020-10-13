FROM adoptopenjdk:11-jre-hotspot
RUN echo "Europe/Moscow" > /etc/timezone
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/application.jar"]