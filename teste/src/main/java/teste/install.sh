#!/bin/bash
#

# install python and pandas
apt install python3 -y
apt install python3-pip -y
pip3 install pandas

# get to root directory and install javaparser and jqf-fuzzer
cd ..  
cd ..
cd ..
cd ..
mvn install