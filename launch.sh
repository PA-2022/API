#!/bin/bash
cd PA
cd API
<<<<<<< HEAD
git checkout halisia
git pull origin halisia
=======
git checkout main
git pull
#TODO: uncomment the followings lines when java project is avalaible in this branch
>>>>>>> 43772b55e916262fbf9f46707414cde571a5e436
cd codeup
mvn clean install
cd target
kill $(ps aux | grep "jar codeup-0.0.1-" | grep -v "grep" | head -1 | awk '{print $2}')
nohup java -Dspring.profiles.active=dev -jar codeup-0.0.1-SNAPSHOT.jar &
<<<<<<< HEAD
#
=======

>>>>>>> 43772b55e916262fbf9f46707414cde571a5e436
