# Gets Java 17, Minecraft from 1.18+ uses Java 17
FROM openjdk:21
# The working directory
WORKDIR /minecraft
# Copies current directory to dockerfile
COPY . .
# Command to start up minecraft in a docker virtual machine
# Parameters "Xmx8192M" means the start memory of 8192M, and the max memory
# is also 8192M. 8GB of ram
# -jar is the type of file
# "nogui" makes minecraft not use its gui
CMD ["java", "-Xmx8192M", "-Xms8192M", "-jar", "server.jar", "nogui"]
