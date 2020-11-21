#!/bin/bash

for i in $@
do
cd $i
mvn exec:java -Dexec.args="."
cd ..
done
