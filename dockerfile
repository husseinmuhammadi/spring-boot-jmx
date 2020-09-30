FROM maven:3.6.3-jdk-11 as maven
RUN mkdir -p /root/.m2 \
    && mkdir /root/.m2/repository
COPY maven/conf/settings.xml /root/.m2
WORKDIR /workspace
COPY pom.xml pom.xml
COPY src src
RUN mvn clean package

FROM openjdk:11
EXPOSE 5000
ENV JAVA_TOOL_OPTIONS "-Dcom.sun.management.jmxremote.ssl=false \
    -Dcom.sun.management.jmxremote.authenticate=false \
    -Dcom.sun.management.jmxremote.port=5000 \
    -Dcom.sun.management.jmxremote.rmi.port=5000 \
    -Dcom.sun.management.jmxremote.host=0.0.0.0 \
    -Djava.rmi.server.hostname=0.0.0.0"
WORKDIR /app
COPY --from=maven /workspace/target/spring-boot-jmx-1.0.0-SNAPSHOT.jar spring-boot-jmx.jar
ENTRYPOINT [ "java", "-jar", "spring-boot-jmx.jar" ]
