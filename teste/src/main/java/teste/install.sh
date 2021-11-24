#!/bin/bash
#

# install python and pandas
sudo apt install python3 -y
sudo apt install python3-pip -y
sudo pip3 install pandas

# get to root directory and install javaparser and jqf-fuzzer
cd ..  
cd ..
cd ..
cd ..
mvn install