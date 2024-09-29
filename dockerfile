# Escolhe uma imagem base com Java 17 e Alpine (versão leve)
FROM eclipse-temurin:17-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo jar gerado pelo Maven para dentro do container
COPY target/crud-spring-boot-0.0.1-SNAPSHOT.jar /app/app.jar

# Expõe a porta que a aplicação usará
EXPOSE 8080

# Define o comando para executar o jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
