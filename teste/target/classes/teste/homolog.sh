#!/bin/bash
# homolog
if [ $# -lt 3 ]; then
    echo "not enough arguments"
    echo "usage: sh homolog.sh -p <path to file to parse starting from src> -c <full qualified name of class to fuzz> -m <method name to fuzz>"
    exit 1;
fi

while getopts p:c:m: flag
do
    case "${flag}" in
        p) parseFile=${OPTARG};;
        c) fuzzClass=${OPTARG};;
        m) fuzzMethod=${OPTARG};;
    esac
done

# create logFile.out file
cd logFile
touch logFile.out
cd ..

# get back to project root
cd ..
cd ..
cd ..
cd ..

# save copy of parsed file
mkdir src/main/java/teste/parseBackup
cp $parseFile src/main/java/teste/parseBackup/parsedJavaFile.backup

# find all conditions and mark all branches
mvn compile exec:java -Dexec.mainClass=teste.AstFindAllConditionalModifier -Dexec.args="$parseFile false" #DESIRED_CLASS add args with full path of desired directory

# run fuzzer to generate logFile.out
mvn jqf:fuzz -Dclass=$fuzzClass -Dmethod=$fuzzMethod -Dtime=1m

# run frequency_analyser and generate conditions_of_interest.py
python3 src/main/java/teste/frequency_analyser/frequency_analyser.py