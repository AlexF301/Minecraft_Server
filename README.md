# Minecraft
Alexander Flores and Edwin Cojitambo

## Overview

A Minecraft server! This tutorial uses a server.jar file that contains
other files for things that range from banned players, all the way to the
server properties. The jar file is then modified to be able to launch Minecraft
within a virtual machine.

## Setup

Run this command in the terminal to start up Minecraft on a local host after
downloading the server.jar file from Minecraft's server page.
```
java -Xmx1024M -Xms1024M -jar server.jar nogui
```
After running the command in the terminal, add a server within the Minecraft
application with any server name, but for the server ip address, type in
"localhost". Your Minecraft server should now be running locally. To run the
server in a local vm, run
```
docker-compose up --build -d
```
within the jar file directory, as well as creating a new server with the ip
address of the port "0.0.0.0:25565", and your local vm should be running, along
the new server created. After creating the virtual machine server, it is now
time to run the application on a cloud VM. Create a light sail server if you
have not, and select a plan that takes at least 2GB of RAM, as our server
will require it. After creating an Ubuntu light sail instance, run these
commands in order to get a functioning light sail instance capable of hosting
our server.

The commands to install Docker within light sail are as follows
```
sudo apt update
sudo apt install apt -transport-https ca-certificates curl software-properties-common
sudo apt-get update
sudo apt install docker
```

The commands to install docker-compose are as follows
```
sudo curl -L https://github.com/docker/compose/releases/download/1.29.1/docker-compose-'uname-s'-'uname-m'-'uname-m'-o/usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version
```

The commands to start the Docker service are as follows
```
sudo systemctl start docker ////// might fail. Run 2 commands below
sudo systemctl unmask docker
sudo systemctl start docker
```

After all these commands are completed, you are ready to launch Minecraft within
light sail, with the command
```
sudo docker-compose up --build -d
```

## Technologies used
You need to download docker to be able to run the vm, as well as on light sail
https://docs.docker.com/get-docker/

downloading the server.jar file that contains Minecraft world information
https://www.minecraft.net/en-us/download/server/

Light sail in order to hose the server on a cloud based VM
https://aws.amazon.com/lightsail/

## Background

To get this working, we needed to use two youtube tutorials to get the docker
file working properly as shown below.
https://www.youtube.com/watch?v=JtKIpGjtLzw&t=663s&ab_channel=WorldofZero
https://www.youtube.com/watch?v=8ghc71KagTc&t=608s&ab_channel=Voizdev

There were also troubleshooting issues in regards of properly implementing
docker within light sail, as the three links for those are down below
https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-18-04
https://www.digitalocean.com/community/tutorials/how-to-install-docker-compose-on-ubuntu-18-04
https://forums.docker.com/t/failed-to-start-docker-service-unit-is-masked/67413
