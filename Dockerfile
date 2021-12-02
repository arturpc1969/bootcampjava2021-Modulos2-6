FROM openjdk:16-slim
RUN groupadd -r livraria && useradd --no-log-init -r -g livraria livraria
USER livraria
WORKDIR /app
COPY target/*.jar /app/livraria-api.jar
EXPOSE 8080
CMD java -XX:+UseContainerSupport -Xmx512m -Dserver.port=${PORT} -jar livraria-api.jar