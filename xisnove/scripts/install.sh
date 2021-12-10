#!/bin/sh
#

# install python and pandas
sudo apt install python3 -y
sudo apt install python3-pip -y
sudo pip3 install pandas

# install javaparser and jqf-fuzzer
mvn install