# Imagem oficial do Tomcat 10.1 com Java 21
FROM tomcat:10.1-jdk21-temurin-jammy

#Define o diretório de trabalho padrão dentro do container.
WORKDIR /usr/local/tomcat/webapps/

# Copia o arquivo WAR da sua máquina host para o diretório de deploy
COPY target/sala-espera.war /usr/local/tomcat/webapps/

# Definindo a porta do Tomcat
EXPOSE 8080

# 5. O comando padrão (CMD) da imagem base do Tomcat já inicia o servidor.
# CMD ["catalina.sh", "run"]