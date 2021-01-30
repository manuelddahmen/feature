#!/bin/bash
for i in sets/$1
do
    echo "${1}"
    mvn exec:java -Dexec.mainClass="one.empty3.feature.FTPProcessFiles" -Dexec.args="sets/$1"
done
