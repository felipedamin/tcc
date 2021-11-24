#!/bin/bash
# homolog

# find all conditions and mark all branches
mvn compile exec:java -Dexec.mainClass=xisnove.AstFindAllConditionalModifier #-Dexec.args=DESIRED_CLASS add args with full path of desired directory

# run fuzzer to generate logFile.out
mvn jqf:fuzz -Dclass=br.usp.larc.nanoib.handlers -Dmethod=fuzz -Dtime=10s

# run frequency_analyser and generate conditions_of_interest.py
python3 frequency_analyser/frequency_analyser.py