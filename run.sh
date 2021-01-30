#!/bin/bash
for i in sets/*
do
    echo "${i}"
    mvn exec:java -Dexec.mainClass="one.empty3.feature.FTPProcessFiles" -Dexec.args="$i"
done
