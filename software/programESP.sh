#!/bin/bash

DEVICE=/dev/ttyUSB0
SLEEPTIME=0.2
TCPPORT=2323

function usage() {
  echo "$0 usage:"
  echo "$0 serial|ip fileOnHost.lua (fileOnESP.lua)"
  echo ""
  echo "The flash logic can be done via ethernet or serial"
  echo "The first filename is mandatory!"
  echo ""
  echo "The second filename is the filename on the LUA devic."
  echo ""
  echo "Example:"
  echo -e "$0 serial hello.txt\tSends the content of hello.txt to the ESP (directly as if you type it)"
  echo -e "$0 serial init_example.lua init.lua\tUpdates the init.lua on the ESP"
  echo "   (The init.lua file is executed each time, the ESP starts)"
  exit 1
}

case "$1" in
serial)
  IP=""
  if [ "$EUID" -ne 0 ]
    then echo "Please run as root"
    exit 1
  fi
  ;;

*)
  if [[ $1 =~ ^[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}$ ]];then
    IP=$1
  else
    usage
  fi
esac


if [ ! -f $2 ]; then
 usage
fi

if [ $IP != "" ]; then
    echo "Using network: $IP "
    echo "Checking connection ..."
    ping -c 3 $IP >> /dev/null
    if [ $? -ne 0 ]; then
      echo "Cannot find ESP at $IP"
      exit 1
    fi
    DEVICE=/dev/tcp/$IP/$TCPPORT
fi

# The flashing logic
if [ "$3" == "" ]; then
  echo "Sending to shell..."
  cat $2  | while read a; do echo -e "$a\r" >> $DEVICE; echo -e "$a"; sleep $SLEEPTIME; done
else
  echo "Writing $3 on the ESP"
  echo "========================="
  echo "" >> $DEVICE; sleep $SLEEPTIME
  echo "file.open(\"$3\",\"w\")" >> $DEVICE; sleep $SLEEPTIME
  cat $2 | while read a; do echo "file.writeline([[${a}]])" >> $DEVICE; echo -e "\r" >> $DEVICE; echo "$a"; sleep $SLEEPTIME; done
  # Close the init file
  echo "file.close()" >> $DEVICE; sleep $SLEEPTIME
  echo "========================="
  echo "now login on ESP and call"
  echo "node.restart()"
fi
