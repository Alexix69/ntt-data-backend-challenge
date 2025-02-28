# Usar la imagen oficial de OpenJDK 17
FROM openjdk:17-jdk-slim

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado por Spring Boot
COPY target/backend-challenge.jar app.jar

# Exponer el puerto en el que corre la aplicación (8080)
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
