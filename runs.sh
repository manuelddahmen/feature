#!/bin/bash
for i in sets/*
do
mvn exec:java -DmainClass="one.empty3.feature.FTPProcessFiles" -Dexec.args="sets/${i}"
done
