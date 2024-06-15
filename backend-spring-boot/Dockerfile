FROM openjdk:17
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} demo-0.0.1-SNAPSHOT.jar 
#COPY --from=build target/demo-0.0.1-SNAPSHOT.jar appTingeso.jar
ARG JAR_FILE=build/libs/civilo-g10-backend-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} appCivilo.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/appCivilo.jar"]