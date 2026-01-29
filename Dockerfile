FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN ./mvnw -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/blackjack-0.0.1-SNAPSHOT.jar /app/app.jar
ENV PORT=10000
EXPOSE 10000
CMD ["java", "-jar", "/app/app.jar"]
