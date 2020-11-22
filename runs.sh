#!/bin/bash
for i in sets/*
do
mvn exec:java -Dexec.args="sets/${i}"
done
