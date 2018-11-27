#!/usr/bin/env bash
docker run --rm --name mongo -d -v /home/$(whoami)/mongodb/:/data/db --net host -d mongo
