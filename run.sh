#!/bin/bash
mvn exec:java -Dexec.mainClass="one.empty3.feature.FTPProcessFiles" -Dexec.args="sets/$1"

