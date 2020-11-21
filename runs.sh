#!/bin/bash
for i in set/*
do
mvn exec:java -Dexec.args="settings/${i}"
done
