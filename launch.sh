#!/bin/bash
cd PA
cd API
git pull origin halisia
cd codeup
mvn clean install
cd target
java -jar codeup-0.0.1-SNAPSHOT.jar &