#!/bin/bash
# production
if [ $# -lt 1 ]; then
    echo "not enough arguments"
    echo "usage: sh production.sh -f <path to file to parse starting from src>"
    exit 1;
fi

while getopts f: flag
do
    case "${flag}" in
        f) parseFile=${OPTARG};;
    esac
done

# get back to project root
cd ..
cd ..
cd ..
cd ..

# find all conditions and mark all branches
mvn compile exec:java -Dexec.mainClass=teste.AstFindAllConditionalModifier -Dexec.args="$parseFile true"
