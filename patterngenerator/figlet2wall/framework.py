#!/usr/bin/env python3

import time
from array import array
import socket
import sys
import argparse

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
UDP_PORT = 2342
IP_HOST_BASE = "192.168.23."

FPS=30

class Led(object): 

    def __init__(self, red, blue, green): 
        self.__red = red 
        self.__blue = blue 
        self.__green = green 

    def setColor(self, red, blue, green):
        self.__red = red 
        self.__blue = blue 
        self.__green = green 
 
    def getBytes(self): 
       return array('B',[self.__red, self.__blue, self.__green]) 

class Stick(object): 

    def __init__(self, ip, position): 
        self.__leds = [ Led(0,50,0) for i in range(60)]
        self.__ip = ip
        self.__position = position
        self.__changed = True

    def update(self):
      if self.__changed:
        #print("Update "+ str(self.__position) + " at " + str(self.__ip))
        global sock
        message = array('B')
        message.extend(array('B',[self.__position,0,0,0])) #Header
        message.extend(self.getBytes())
        try: 
            sock.sendto(message, (self.__ip, UDP_PORT))
            self.__changed = False
        except socket.error: 
            print("exception")

    def get(self, pos):
        self.__changed = True
        return self.__leds[pos]

    def setColor(self, red, green, blue):
        for i in range(0, len(self.__leds)):
            self.__leds[i].setColor(red,green,blue)
        self.__changed = True

    def getBytes(self):
        bArray = array('B')
        for l in self.__leds:
           bArray.extend(l.getBytes())
        return bArray

    def setBytes(self,byteArray):
        #print("Setting "+str(len(byteArray))+" Bytes")
        for i in range(0,len(byteArray),3):
            #print("Led:"+str(i/3)+" Branch "+str(i)+": "+str(byteArray[i])+str(byteArray[i+1])+str(byteArray[i+2]))
            self.__leds[int(i/3)].setColor(byteArray[i],byteArray[i+1], byteArray[i+2])
        self.__changed = True

    def clear(self):
        self.setColor(0,0,0)


class Wall(object):

    def __init__(self,startNumber, endNumber, simulatorIP=""):
        if (simulatorIP == "" or simulatorIP == None):
            self.__sticks = [ Stick(IP_HOST_BASE+str(i),i) for i in range(startNumber, endNumber)]
        else:
            print simulatorIP
            self.__sticks = [ Stick(simulatorIP,i) for i in range(startNumber, endNumber)]

    def get(self, pos):
        return self.__sticks[pos]

    def update(self):
        for s in self.__sticks:
            s.update()

    def len(self):
        return len(self.__sticks)

    def shiftLeft(self):
        fArray = array('B')
        for i in range(0,len(self.__sticks)):
            bArray = self.__sticks[i].getBytes()
            if i > 0:
                self.__sticks[i-1].setBytes(bArray)
            else:
                fArray = bArray
        self.__sticks[len(self.__sticks)-1].setBytes(fArray)

    def shiftRight(self):
        fArray = self.__sticks[0].getBytes()
        for i in range(0,len(self.__sticks)):
            if i < len(self.__sticks) -1:
                self.__sticks[i+1].setBytes(fArray)
            fArray = self.__sticks[i].getBytes()
        self.__sticks[0].setBytes(fArray)

    def setColor(self, red, green, blue):
        for i in range(0, len(self.__sticks)):
            self.__sticks[i].setColor(red,green,blue)

    def clear(self):
        for stick in self.__sticks:
            stick.clear()

    def drawArrow(self,startStick,red,green,blue):
        if len(self.__sticks) < startStick+9:
            print("Not enough sticks")
        else:
            self.get(startStick+0).get(30).setColor(red,green,blue)
            for i in range(25,35):
                self.get(startStick+1).get(i).setColor(red,green,blue)
            for i in range(15,45):
                self.get(startStick+2).get(i).setColor(red,green,blue)
            for i in range(10,50):
                self.get(startStick+3).get(i).setColor(red,green,blue)
            for i in range(0,60):
                self.get(startStick+4).get(i).setColor(red,green,blue)
            #for i in range(15,45):
            #   self.get(startStick+5).get(i).setColor(red,green,blue)
            #for i in range(15,45):
            #   self.get(startStick+6).get(i).setColor(red,green,blue)
            #for i in range(15,45):
            #   self.get(startStick+7).get(i).setColor(red,green,blue)
            # for i in range(15,45):
            #   self.get(startStick+8).get(i).setColor(red,green,blue)



#----------- Main
# Argument parsing..
class CmdInput:
    pass

parser = argparse.ArgumentParser(description='Text to Sticks')
parser.add_argument('--simulator', help='IP address of an the simulator; e.g. --simulator 127.0.0.1')
parser.add_argument('--textcolor', help='Color of the text, that should be displayed. e.g. "#000080"')
parser.add_argument('--stickcount', help='Amount of sticks, that are used')
parser.add_argument('--scroll', help='Amount of seconds to scroll the text; -1 will scroll endlessly')

args = parser.parse_args(namespace=CmdInput)
# Set the default parameter
heightfactor = 5
stickCount = 10
textColor = [0, 0, 60]
backgroudColor = [0, 20, 0]
backgroudColorSimu = [0, 255, 0]
scrollDelay = 0.5 # in seconds

# Read parameter
if (CmdInput.stickcount):
    stickCount = int(CmdInput.stickcount)

if (CmdInput.textcolor):
    if (len(CmdInput.textcolor) == 7 and CmdInput.textcolor[0] == '#'):
        textColor[0] = int(CmdInput.textcolor[1:3], 16) # Red
        textColor[1] = int(CmdInput.textcolor[3:5], 16) # Green
        textColor[2] = int(CmdInput.textcolor[5:7], 16) # Blue
    else:
        print("Color must be in the following format: '#RRGGBB' (Red, Green, Blue are defined as hex values)")
        exit(1)

print("Generate Text for Wall with " + str(stickCount))
w1 = Wall(1,stickCount + 1, CmdInput.simulator)
w1.clear()
w1.setColor(*backgroudColor)
# Hack, to make the background compatible for the simulator
if (CmdInput.simulator):
    w1.setColor(*backgroudColorSimu)
w1.update()

# prepare text convertion buffer
textBuffer = list()

for y, line in enumerate(sys.stdin):
    line = line.replace("\r", "") 
    line = line.replace("\n", "") 
    print(line)
    textBuffer.append([0] * len(line) )
    for i, c in enumerate(line):
        if c != " ":
                textBuffer[y][i] = 1
        else:
                textBuffer[y][i] = 0


# Shrink it baby!
outputBuffer = list()
firstColumn = []
# Move the first column into the output
outputBuffer.append([ textBuffer[i][0] for i in range(0, len(textBuffer))])

# start with the second column
for spalte in range(1, len(textBuffer[0])):
 #   print "------ Spalte " + str(spalte) + " --------"
    for zeile in range(0, len(textBuffer)):
        if textBuffer[zeile][spalte -1] !=  textBuffer[zeile][spalte]:
            # Move the column to the output buffer
            outputBuffer.append([ textBuffer[i][spalte] for i in range(0, len(textBuffer))])
            break

for columnNo, column in enumerate(outputBuffer):
    for rowNo, row in enumerate(outputBuffer[columnNo]): 
        s = w1.get((stickCount - 1) - columnNo)
        for x in range(0, heightfactor):
            offset = 59 - ((rowNo * heightfactor) + x)
            if row == 1:
                s.get(offset).setColor(*textColor)

w1.update()
startTime = int(time.time())
seconds2scroll = 0
if CmdInput.scroll:
    seconds2scroll = int(CmdInput.scroll)
print startTime
print seconds2scroll
while (startTime + seconds2scroll > int(time.time())):
    print(".")
    time.sleep(scrollDelay)
