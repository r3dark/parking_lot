#!/bin/bash

## This file will run the application based on the input parameter specified

arg1=$1
## Location of application jar
dir=build/libs
## Name of executable jar
jar_name=parking_lot-1.0-SNAPSHOT.jar

## Check if only 1 argument is entered
if [ $# -lt 2 ]; then
    ## Check if argument entered is not null
    if [ -n "$1" ]; then
        java -jar $dir/$jar_name "$arg1"
    else
        java -jar $dir/$jar_name
        exit 1
    fi
else
    echo "More than one input file specified. Please specify a single file."
    exit 1
fi
