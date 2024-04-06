# Minecraft
Alexander Flores and Edwin Cojitambo

## Overview

A Minecraft server! This Server uses Fabric for its modding.
The jar file is modified to be able to launch Minecraft within a virtual machine.

NOTE: Server is running Minecraft 1.20.1. Have this version of minecraft installed
https://www.minecraft.net/en-us/article/minecraft--java-edition-1-20-1 

## Add Mods For Your Use

Add the mods the server is using so you can play on it.

Unzip required_server_mods.zip. You can do this a couple ways: 

* By double clickingon the file
* right clicking and selecting "extract files"
* For Windows (with powershell): 
```
Expand-Archive -Force "zipfile path" "path to extract to"
```
* For Unix cli
```
unzip "zipfile path" -d "path to extract to"
```

run "fabric-installer-1.0.0.exe" and follow the instructions for installation.
* Minecraft version: 1.20.1
* Loader version: 0.15.9

With Fabric installed, copy or move the files in the client_mods_for_server folder to your local minecraft mods folder.
Make sure its the files (end in .jar) you are copying/moving over, not the whole folder
The path on Windows follows this or a similar structure:

C:\Users\User\AppData\Roaming\.minecraft\mods

launch Minecraft with Fabric 1.20.1 and connect to the server with the server IP Address (provided by whomever is hosting the server)

## Technologies used

Download Java 17+
https://www.java.com/download/ie_manual.jsp 

You need to download docker to be able to run the vm, as well as on light sail
https://docs.docker.com/get-docker/

Download Fabric (this is done in the zip file but here is the link regardless)
https://fabricmc.net/use/installer/ 

Light sail in order to host the server on a cloud based VM
https://aws.amazon.com/lightsail/

Oracle to host the server on a cloud based VM

## Development Setup

### Local

Ensure you have the required technolgies. Use docker to build the server
```
docker-compose up --build -d
```

Docker command to run the local server
```
docker-compose up -d
```

Add a server within the Minecraft application with any server name with
the the server ip address, "localhost". Your Minecraft server should now be running locally.

within the jar file directory, as well as creating a new server with the ip
address of the port "0.0.0.0:25565", and your local vm should be running, along
with the new server created. After creating the virtual machine server, it is
now time to run the application on a cloud VM. Create a light sail or oracle server if you
have not, and select a vm machine that is least 2GB of RAM, as required to run Minecraft. 
After creating an Ubuntu instance (recommended), run these commands in order to get a functioning instance capable of hosting
our server.

```
docker-compose down
```

### VM Server

The commands to install Docker are as follows
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
light sail, with the command to build the docker container
```
sudo docker-compose up --build -d
```

The command to run the server
```
docker-compose up -d
```

In Minecraft, connect to the server with the server IP Address

to stop the server/bring down the container
```
docker-compose down
```

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
