# Gets Java 17, Minecraft from 1.18+ uses Java 17
FROM openjdk:17
# The working directory
WORKDIR /minecraft
# Copies current directory to dockerfile
COPY . .
# Command to start up minecraft in a docker virtual machine
# Parameters "Xmx4096M" means the start memory of 4096M, and the max memory
# is also 4096M. 4GB of ram
# -jar is the type of file
# "nogui" makes minecraft not use its gui
CMD ["java", "-Xmx4096M", "-Xms4096M", "-jar", "fabric-server-mc.1.20.1-loader.0.15.9-launcher.1.0.0.jar", "nogui"]
