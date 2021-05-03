# -*- mode: ruby -*-
# vi: set ft=ruby :
FROM python:3.8-slim-buster
WORKDIR /minecraft

RUN python3 -m venv .venv


RUN .venv/bin/pip install e .

CMD [java -Xmx1024M -Xms1024M -jar server.jar nogui]
