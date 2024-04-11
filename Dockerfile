# Gets Java 17, Minecraft from 1.18+ uses Java 17
FROM openjdk:17
# The working directory
WORKDIR /minecraft
# Copies current directory to dockerfile
COPY . .
# Command to start up minecraft in a docker virtual machine
# Parameters "Xmx5120M" means the start memory of 5120M, and the max memory
# is also 5120M. 4GB of ram
# -jar is the type of file
# "nogui" makes minecraft not use its gui
CMD ["java", "-Xmx5120M", "-Xms5120M", "-jar", "fabric-server-mc.1.20.1-loader.0.15.9-launcher.1.0.0.jar", "nogui"]
