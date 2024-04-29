# Використовуємо відповідний образ Java
FROM openjdk:11

# Додавання JAR файлу Spring Boot додатку у контейнер
COPY target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

# Запуск Spring Boot додатку при старті контейнера
CMD ["java", "-jar", "/app/app.jar"]
