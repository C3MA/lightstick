#!/bin/bash

./esptool.py --port /dev/ttyUSB0 write_flash 0x00000 0x00000.bin 0x10000 0x10000.bin 0x7c000 esp_init_data_default.bin 0x7e000 blank.bin
