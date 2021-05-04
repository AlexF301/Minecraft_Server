FROM openjdk:8

# The working directory
WORKDIR /minecraft

COPY . .

CMD ["java", "-Xmx512M", "-Xms512M", "-jar", "server.jar", "nogui"]
