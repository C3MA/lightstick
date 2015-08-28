#!/bin/bash

read a
echo "$a" | figlet -f banner | python framework.py $@
