# Minecraft
Alexander Flores and Edwin Cojitambo

## Overview

A Minecraft Java server! This Server uses Fabric for its modding.
The jar file is a modified version with Fabric to be able to launch Minecraft within a virtual machine.

NOTE: Server is running Minecraft 1.20.1. Have this version of minecraft installed on your device
https://www.minecraft.net/en-us/article/minecraft--java-edition-1-20-1 

## Add Mods For Your Use

Add the mods the server is using so you can play on it.

Unzip required_server_mods.zip. You can do this a couple ways: 

* By double clicking on the file
* right clicking and selecting "extract files"
* For Windows powershell (replace "path info" with your desired path): 
```
Expand-Archive -Force "zipfile path" "path to extract to"
```
* For Unix cli (replace "path info" with your desired path): 
```
unzip "zipfile path" -d "path to extract to"
```

run "fabric-installer-1.0.0.exe" and follow the instructions for installation.
* Minecraft version: 1.20.1
* Loader version: 0.15.9

With Fabric installed, copy or move the files in the ``client_mods_for_server`` folder to your local minecraft mods folder.
Make sure its the files (end in .jar) you are copying/moving over, not the whole folder.
The path on Windows follows this or a similar structure:

``C:\Users\User\AppData\Roaming\.minecraft\mods``

launch Minecraft with Fabric 1.20.1 and connect to the server with the server IP Address (provided by whomever is hosting the server)

## Technologies Used

Download Java 17+ :
https://www.java.com/download/ie_manual.jsp 

You need to download docker/docker-compose to be able to run on a server (both locally and in the cloud):
https://docs.docker.com/get-docker/

Download Fabric (this is done in the zip file but here is the link regardless):
https://fabricmc.net/use/installer/ 

### Cloud Server Self Hosting 
* Light sail:
https://aws.amazon.com/lightsail/

* Google Cloud Compute Engine:
https://cloud.google.com/products/compute?hl=en

## Development Setup

### Ensure you have the required technolgies downloaded. More information below 

### Local

Download Java and Docker, links can be found in the `Technologies Used` section

Use docker to build the server
```
docker-compose up --build -d
```

Docker command to run the local server
```
docker-compose up -d
```

Add a server within the Minecraft application with any server name with
the the server ip address, "localhost". Your Minecraft server should now be running locally.

The `docker-compose.yml` file handles forwarding the minecraft specific port "0.0.0.0:25565". Your server should now be running locally. 

To bring down the local server:
```
docker-compose down
```

Congrats! You are now able to run a minecraft server locally for development, play around, add new mods, etc. Next Step, create your server on the Cloud.

### VM Server
We recommend deploying a server locally first so you can get an idea of what the process building a server is like. Additionally you learn how to play around with the game files, including those that get generated.

It is now time to run the application on a cloud VM. Use whatever Cloud provider you want, AWS, Google Cloud, Oracle, Azure, etc. For this project, we have used two services on seperate occasians. AWS Lightsail and Google Compute Engine were both successful. 

The system requirements for minecraft are provided on the minecraft support website: `https://www.minecraft.net/en-us/store/minecraft-deluxe-collection-pc#accordionv1-b6c8df09da-item-7739893325`
 
### Configure a Virtual Machine/instance
* To run Minecraft on a server, we need a vm machine that is least **2GB of RAM**, as required to run Minecraft. The more the better, just be cautious of pricing. Always refer to the Providers pricing documenation and cost calculators
* We recommend an Ubuntu operating sytem. The commands that follow are easier to work with on Ubuntu
* For Google Compute Engine, In the networking section, add a tag. This tag can be named whatever you want, just remember it as this is used later when configuring the firewall

## SSH Into VM instance

To SSH onto the VM Instance, the Cloud providers have their own CLI you can launch on their consoles if you wish to use that or you can SSH from your own terminal with a private key.

To SSH from your own terminal, find and download the private key corresponding to your vm instance: https://cloud.google.com/compute/docs/connect/standard-ssh#connect_to_vms
```
ssh -i PATH_TO_PRIVATE_KEY USERNAME@EXTERNAL_IP
```
Verify you are able to connect to your vm instance in whatever way is convenient for you before moving on.
### Firewall Config

The process will be slighly different for each provider but the general idea remains the same
* Find the section to configure Firewalls and create one. 
    * For Google Cloud: Create Firewall Rule
* Configure the new firewall rule
    * Allow public IPV4. This is `0.0.0.0/0`
    * TCP Port, Minecraft's specific port: `25565`

NOTE: For google Compute Engine, target by specific tag, add the tag you specified when configuring the instance. Go back to your vm instances dashboard. From the dashboard -> 3 dots on right side of your listed instance -> view network details. Check to see if the vm is using the firewall rule, if not add it.

### Install Java

Minecraft runs on Java, therefore we need Java. To download on the CLI:
```
sudo add-apt-repository ppa:openjdk-r/ppa
sudo apt update
sudo apt install openjdk-17-jre-headless
```
Confirm any prompts

Additionally, add the firewall rule by running the command:
```
sudo ufw allow 25565
```

### Install Docker and Docker-Compose

run these commands in order to get a functioning instance capable of hosting
our server. The commands to install Docker are as follows
```
sudo apt install docker
```

The commands to install docker-compose are as follows
```
sudo apt install docker-compose
docker-compose --version
```

The commands to start the Docker service are as follows
```
sudo systemctl start docker ////// might fail. Run 2 commands below
sudo systemctl unmask docker
sudo systemctl start docker
```

### Clone repo

```
git clone https://github.com/AlexF301/Minecraft_Server.git
cd Minecraft_Server
```

### Start Server

After all these commands are completed, you are ready to launch Minecraft within
light sail, with the command to build and run the docker container
```
sudo docker-compose up --build -d
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
docker within the virutal machines, we used the below guides: 
* https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-18-04
* https://www.digitalocean.com/community/tutorials/how-to-install-docker-compose-on-ubuntu-18-04
* https://forums.docker.com/t/failed-to-start-docker-service-unit-is-masked/67413
