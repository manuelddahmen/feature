#!/bin/bash
for i in $@
do
mvn exec:java -Dexec.args="${i}"
done
