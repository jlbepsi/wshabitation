# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="jlb.epsi@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# The application's jar file
ARG JAR_FILE=build/libs/wshabitation-0.0.1.jar
ARG RESOURCES_DIR=src/main/resources
# ARG KEYS_DIR=
# Create keys directory
#RUN mkdir keys

# Add the application's jar to the container
COPY ${JAR_FILE} app.jar
# Add dependencies
COPY ${RESOURCES_DIR}/application.properties application.properties
COPY ${RESOURCES_DIR}/logback-spring.xml logback-spring.xml



#COPY ${RESOURCES_DIR}/log4j2-spring.xml log4j2-spring.xml
#COPY ${KEYS_DIR}/private_key.der keys/private_key.der
#COPY ${KEYS_DIR}/public_key.der keys/public_key.der

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]