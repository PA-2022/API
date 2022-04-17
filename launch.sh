#!/bin/bash
cd PA
cd API
git pull origin halisia
cd codeup
mvn clean install 
cd target

if [ $(ps aux | grep "jar codeup-0.0.1-" | head -1 | cut -d' ' -f2) ]
then
    kill $(ps aux | grep "jar codeup-0.0.1-" | head -1 | cut -d' ' -f2)
fi

java -Dspring.profiles.active=dev -jar codeup-0.0.1-SNAPSHOT.jar