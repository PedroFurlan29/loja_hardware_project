FROM eclipse-temurin:17-jre-slim

WORKDIR /app

COPY build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENV PORT=8080

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD java -cp app.jar org.springframework.boot.loader.JarLauncher

ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "app.jar"]
