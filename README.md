# Lighstick
## Interfaces
* WS2812 - LED output
* UART (needed for programming and debugging)
* TCP-Port 2323 Telnet/Netcat
* UDP-Port 2342 RGB information

## UDP RGB Protocol
* Header
  * ID (one byte) - uniq identifier of one ledstick
  * Delay (one byte in ms)
  * timestamp in milliseconds only last two bytes
* RGB
  * LED1
    * red (one byte)
    * green (one byte)
    * blue (one byte)
  * LED2
    * red (one byte)
    * green (one byte)
    * blue (one byte)


Header is at the moment 4 bytes

# Links
* https://www.ccc-mannheim.de/wiki/ESP8266/Lightstick
