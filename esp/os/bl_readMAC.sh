#!/bin/bash
echo "Read the MAC address from bootloader"
./esptool.py --port /dev/ttyUSB0 read_mac
