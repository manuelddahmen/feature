#!/bin/bash
for i in settings/*
do
mvn exec:java -Dexec.args="settings/${i}"
done
