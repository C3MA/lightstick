#!/usr/bin/env python

import socket
from array import array
import time
import sys
import copy
import pdb;
import colorsys
import random

UDP_PORT = 2342

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)


class Wall():
    ipnet = "192.168.23."
    def __init__(self, stickcount, firstIP="1"):
        self.sticks = []
        print("initialise wall object with " + str(stickcount) + " sticks")
        self.stickcount = stickcount
        for i in range(0, stickcount):
            print i
            self.sticks.insert(i, Lightstick(self.ipnet+str(firstIP+i), 60, 0, 0))
            self.sticks[i].updateLeds()
            
    def getIps(self):
        for stick in self.sticks:
            print(stick.ip)
            
    def allOff(self):
        for stick in self.sticks:
            stick.setAllLeds(0,0,0)
            
    def setAllSticks(self,r,g,b):
        for stick in self.sticks:
            stick.setAllLeds(r,g,b)
            stick.updateLeds()
            time.sleep(0.2)
                
class Lightstick():
    header = array('B',[0,0,0,0])
    dark = array('B',[0,0,0])
    def __init__(self, ip, rt=0,gr=0,bl=0):
        self.ip = ""
        self.leds = []
        default = array('B',[rt,gr,bl])
        self.ip = ip
        sys.stdout.write("initialise new lightstick\n")
        self.leds.append(self.header)
        for i in range(0,60):
            #sys.stdout.write("adding led <" + str(i+1) + ">\n")
            self.leds.append(copy.copy(default))
            
    def setAllLeds(self, r, g, b):
        print ("set all leds to: "+str(r)+", "+str(g)+", "+str(b))
        for i in range(1,61):
            self.leds[i] = [r,g,b]
        self.updateLeds()
    
    def setLed(self, led, red, green, blue):
        #print("Setting LED <"+str(led)+"> to: "+str(red)+ ", "+str(green)+", "+str(blue))
        #old = copy.copy(self.leds[led])
        self.leds[led][0] = red
        self.leds[led][1] = green
        self.leds[led][2] = blue
        #return old
    
    def printLeds(self):
        #self.leds.pop(0)#
        print(self.ip)
        for led in self.leds:
            print(led)
            
    def updateLeds(self):
        message = array('B',[])
        for x in self.leds:
            message.extend(x)
        sock.sendto(message,(self.ip, UDP_PORT))
        #print("updating lightstick"+self.ip)

def main(argv):
    #w = Wall(50,1)
    w = Wall(12,26)
    colorValue = random.random()
    rgb =  colorsys.hsv_to_rgb(colorValue, 1, 0.20)
    color = [int(rgb[0]*255), int(rgb[1]*255), int(rgb[2]*255)]
    up=True
    while True:
        for y in range(59*(not up),60*up, -1+(2*up) ):
            for i in range(0,len(w.sticks)/2):
                x1 = len(w.sticks)-1-i
                x2 = i
                #print("Updating " + str(x1) + " and " + str(x2))
                w.sticks[x1].setLed(y+1, *color)
                w.sticks[x1].updateLeds()
                w.sticks[x2].setLed(y+1, *color)
                w.sticks[x2].updateLeds()
            if ((y % 5) == 0):
                change = random.random()
                colorValue += change
                rgb =  colorsys.hsv_to_rgb(colorValue, 1, 0.20)
                color = [int(rgb[0]*255), int(rgb[1]*255), int(rgb[2]*255)]
            time.sleep(0.20)
        up=not up
        
    
if __name__ == "__main__":
    sys.exit(main(sys.argv))
