#!/bin/bash
cd PA
cd API
git checkout halisia
git pull origin halisia
cd codeup
mvn clean install 
cd target
kill $(ps aux | grep "jar codeup-0.0.1-" | grep -v "grep" | head -1 | awk '{print $2}')
nohup java -Dspring.profiles.active=dev -jar codeup-0.0.1-SNAPSHOT.jar &
#
