#!/bin/bash
# homolog

# create logFile.out file
cd logFile
touch logFile.out
cd ..

# get back to project root
cd ..
cd ..
cd ..
cd ..

# find all conditions and mark all branches
mvn compile exec:java -Dexec.mainClass=xisnove.AstFindAllConditionalModifier #-Dexec.args=DESIRED_CLASS add args with full path of desired directory

# run fuzzer to generate logFile.out
mvn jqf:fuzz -Dclass=br.usp.larc.nanoib.handlers.LoginFuzzing -Dmethod=fuzz -Dtime=1m

# run frequency_analyser and generate conditions_of_interest.py
python3 src/main/java/xisnove/frequency_analyser/frequency_analyser.py