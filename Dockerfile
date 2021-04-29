# -*- mode: ruby -*-
# vi: set ft=ruby :
FROM python:3.8-slim-buster
WORKDIR /app

RUN python3 -m venv .venv

COPY flaskr .
COPY setup.py .

RUN .venv/bin/pip install e .

CMD [".venv/bin/gunicorn", "--bind", "0.0.0.0:5000", "flaskr:create_app()"]
