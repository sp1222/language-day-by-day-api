FROM gradle:8.9.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build

FROM openjdk:24-slim

COPY --from=builder /app/build/libs/*.jar /app/lib/

WORKDIR /app

CMD ["java", "-cp", "/app/lib/*.jar", "com.sp1222.language.daybyday.api.Application"]
