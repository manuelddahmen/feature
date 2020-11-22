#!/bin/bash
for i in sets/*
do
mvn exec:java -Dexec.mainClass="one.empty3.feature.FTPFilesProcess" -Dexec.args="sets/${i}"
done
