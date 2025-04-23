FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY target/api-account-app-0.0.1-SNAPSHOT.jar api-account-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT java -Djavax.xml.transform.TransformerFactory=com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl -Duser.timezone=America/Bogota -jar /api-account-app-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev --spring.cloud.config.label=local -Duser.timezone=America/Bogota
EXPOSE 8090
