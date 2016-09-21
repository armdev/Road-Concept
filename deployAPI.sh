#!/bin/bash

cd core;
mvn clean package;
cd ..;

cd api;
mvn clean package;
echo "Deploying Road Concept API on http://localhost:8080/ ..." 
java -jar target/road-concept-api-1.0-SNAPSHOT-fat.jar 
cd ..;