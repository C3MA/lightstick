#!/bin/bash

#date +%T | figlet -f banner | python framework.py
read a
echo "$a" | figlet -f banner | python framework.py
