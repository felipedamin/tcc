#!/bin/sh
# homolog
if [ $# -lt 10 ]; then
    echo "not enough arguments"
    echo "usage: sh homolog.sh -p <path to file to parse starting from src> -u <unit test class> -c <full qualified name of class to fuzz> -m <method name to fuzz> -t <time to fuzz(30s/1m/etc)>"
    exit 1;
fi

while getopts p:u:c:m:t: flag
do
    case "${flag}" in
        p) parseFile=${OPTARG};;
        u) unitTestClass=${OPTARG};;
        c) fuzzClass=${OPTARG};;
        m) fuzzMethod=${OPTARG};;
        t) fuzzTime=${OPTARG};;
    esac
done

# clean logFile if exists
> src/main/java/teste/logFile/logFile.out

# save copy of parsed file
mkdir src/main/java/teste/parseBackup
cp $parseFile src/main/java/teste/parseBackup/parsedJavaFile.backup

# find all conditions and mark all branches
mvn compile exec:java -Dexec.mainClass=teste.AstFindAllConditionalModifier -Dexec.args="$parseFile false" #DESIRED_CLASS add args with full path of desired directory

mvn compile

mvn test -Dtest=$unitTestClass

# run fuzzer to generate logFile.out
mvn jqf:fuzz -Dclass=$fuzzClass -Dmethod=$fuzzMethod -Dtime=$fuzzTime

# copy original file back
cp src/main/java/teste/parseBackup/parsedJavaFile.backup $parseFile

> src/main/java/teste/parseBackup/parsedJavaFile.backup

# run frequency_analyser and generate conditions_of_interest.py
python3 src/main/java/teste/frequency_analyser/frequency_analyser.py