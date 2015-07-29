# Software on ESP8266
All required software to be executed on the ESP8266 is stored in this directory

## OS
This is the LUA firmware [NodeMCU](https://github.com/nodemcu/nodemcu-firmware)

It can be flashed on Linux hosts with the following commands.
```
cd os
./flash.sh
```
The ESP must be jumpered for the bootloader and had a power cycle:
GPIO0 pulled to GND
GPIO2 pulled to VCC(3.3V)
The serial connection between the ESP and the host is done with an USB-UART converter -> **ttyUSB0** is used in flash.sh

## Initialization (Logic)

After the OS has been flashed and the jumper is back in normal mode:
GPIO0 jumpered to VCC(3.3V)
GPIO2 open (LEDs can be connected there)
The logic itself can be flashed.
The initial flashing must be done via serial
```
cd  init-lua/
sudo ./programESP.sh serial udp_led_init.lua init.lua
```

After the logic has be integrated once, it can be updated remote via TCP:
```
cd init-lua/
./programESP.sh 192.168.23.1 udp_led_init.lua init.lua
```
