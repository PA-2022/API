#!/bin/bash
cd PA
cd API
git pull 
#TODO uncomment the followings lines when java project is available in this branch 
# cd codeup
# mvn clean install 
# cd target
# kill $(ps aux | grep "jar codeup-0.0.1-" | head -1 | cut -d' ' -f2)
# java -Dspring.profiles.active=dev -jar codeup-0.0.1-SNAPSHOT.jar
