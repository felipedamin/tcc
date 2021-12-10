#!/bin/sh
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

# save copy of parsed file
mkdir src/main/java/xisnove/parseBackup
cp $parseFile src/main/java/xisnove/parseBackup/parsedJavaFile.backup

# find all conditions and mark all branches
mvn compile exec:java -Dexec.mainClass=xisnove.AstFindAllConditionalModifier -Dexec.args="$parseFile true"

mvn compile

# save back original file
cp src/main/java/xisnove/parseBackup/parsedJavaFile.backup $parseFile

> src/main/java/xisnove/parseBackup/parsedJavaFile.backup
