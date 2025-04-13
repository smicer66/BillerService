FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} BillerService.jar
ENTRYPOINT ["java","-jar","/BillerService.jar"]