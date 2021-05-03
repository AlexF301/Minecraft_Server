FROM openjdk:8

# The working directory
WORKDIR /minecraft

COPY * .

CMD ["java", "-Xmx1024M", "-Xms1024M", "-jar", "server.jar", "nogui"]
