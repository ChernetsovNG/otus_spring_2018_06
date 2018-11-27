#!/usr/bin/env bash
# dev helper script to run local mongo for testing in docker
docker run --rm --name mongo -d -v /home/$(whoami)/mongodb/:/data/db --net host -d mongo
