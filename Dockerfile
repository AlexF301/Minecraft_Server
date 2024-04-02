# Gets Java 17, Minecraft from 1.18+ uses Java 17
FROM openjdk:17
# The working directory
WORKDIR /minecraft
# Copies current directory to dockerfile
COPY . .
# Command to start up minecraft in a docker virtual machine
# Parameters "Xmx2048M" means the start memory of 2048M, and the max memory
# is also 2048M. 2GB of ram
# -jar is the type of file
# "nogui" makes minecraft not use its gui
CMD ["java", "-Xmx2048M", "-Xms2048M", "-jar", "fabric-server-mc.1.20.4-loader.0.15.9-launcher.1.0.0.jar", "nogui"]
