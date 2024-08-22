FROM eclipse-temurin:21-jre-alpine AS layers
WORKDIR /application
COPY build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY --from=layers /application/dependencies/ ./
COPY --from=layers /application/spring-boot-loader/ ./
COPY --from=layers /application/snapshot-dependencies/ ./
COPY --from=layers /application/application/ ./



ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
