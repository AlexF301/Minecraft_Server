# Gets Java
FROM openjdk:8
# The working directory
WORKDIR /minecraft
# Copies current directory to dockerfile
COPY . .
# Command to start up minecraft in a docker virtual machine
# Parameters "Xmx1024M" means the start memory of 1024M, and the max memory
# is also 1024M
# -jar is the type of file
# "nogui" makes minecraft not use its gui
CMD ["java", "-Xmx512M", "-Xms512M", "-jar", "server.jar", "nogui"]
