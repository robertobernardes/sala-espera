#!/bin/bash

mvn clean package -DskipTests
docker compose up -d --build

exit 0
