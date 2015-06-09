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
  * LED ...

Header is at the moment 4 bytes

# Links
* https://www.ccc-mannheim.de/wiki/ESP8266/Lightstick


#TrackerStick
Tracker files player (xm,mod,s3m) that creates light patterns based on the used notes and instruments in the tracker file.


Examples using the EmuStick software:

 * https://www.youtube.com/watch?v=BA6pZhu4QjQ
 * https://www.youtube.com/watch?v=6QiemtzXNDI
