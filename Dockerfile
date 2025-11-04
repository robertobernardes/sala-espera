# Imagem oficial do Tomcat 10.1 com Java 21
FROM tomcat:10.1-jdk21-temurin-jammy

# Instala o pacote de localização e gera o locale pt_BR
RUN apt-get update && apt-get install -y locales \
    && echo "pt_BR.UTF-8 UTF-8" >> /etc/locale.gen \
    && locale-gen pt_BR.UTF-8 \
    && update-locale LANG=pt_BR.UTF-8

# Define as variáveis de ambiente do sistema
ENV LANG pt_BR.UTF-8
ENV LC_ALL pt_BR.UTF-8

#Define o diretório de trabalho padrão dentro do container.
WORKDIR /usr/local/tomcat/webapps/

# Copia o arquivo WAR da sua máquina host para o diretório de deploy
COPY target/sala-espera.war /usr/local/tomcat/webapps/

# Definindo a porta do Tomcat
EXPOSE 8080

# 5. O comando padrão (CMD) da imagem base do Tomcat já inicia o servidor.
# CMD ["catalina.sh", "run"]