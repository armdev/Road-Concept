#!/usr/bin/env bash

../Docker/prepare_build_all.sh 0

#stop running containers
docker-compose -f ../Docker/docker-compose-dev.yml stop

#build service
docker-compose -f ../Docker/docker-compose-dev.yml build

#start service
docker-compose -f ../Docker/docker-compose-dev.yml up

