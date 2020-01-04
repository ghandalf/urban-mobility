# From an existing image
FROM openjdk:11-jdk-slim
USER ghandalf

# Owner
LABEL owner="Ouellet.Francis@gmail.com"

# Expose the port out of the container 
EXPOSE 8020

# The application to include to the container
# FIXME: this file must be taken from a repository after build where all tests passed
ARG JAR_FILE=ws-api/target/ws-api-0.1.4.jar

# Copy the jar file to the container with the name given
ADD ${JAR_FILE} ws-api-0.1.4.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "jar", "/ws-api-0.1.4.jar"]
