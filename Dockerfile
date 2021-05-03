FROM openjdk:8
WORKDIR /minecraft

COPY * .

CMD ["java", "-Xmx1024M", "-Xms1024M", "-jar", "server.jar", "nogui"]
