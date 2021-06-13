# the second stage of our build will use open jdk 8 on alpine 3.9
FROM openjdk:15-alpine

# copy only the artifacts we need from the first stage and discard the rest
COPY ./target/vms-0.0.1-SNAPSHOT.jar /vms.jar

# set the startup command to execute the jar
ENTRYPOINT ["java", "--enable-preview", "-jar", "/vms.jar"]
