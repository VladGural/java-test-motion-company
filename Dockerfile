FROM eclipse-temurin:21_35-jdk-jammy

WORKDIR /service

ENV CONTAINER_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=85"
ENV JAVA_OPTS=""
ENV SERVICE_PARAMS=""
ENV SERVICE_ADDITIONAL_PARAMS=""
ADD target/app.jar /service/app.jar

CMD java $CONTAINER_OPTS $JAVA_OPTS -jar app.jar $SERVICE_PARAMS $SERVICE_ADDITIONAL_PARAMS
