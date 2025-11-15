FROM openjdk:21
COPY "./target/BUCARAMANGA-1.jar" "app.jar"
EXPOSE 8081
ENTRYPOINT [ "java", "-jar", "app.jar" ]