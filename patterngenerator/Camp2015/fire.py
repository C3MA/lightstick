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

red=array('B',[75,0,0])
green=array('B',[0,75,0])
blue=array('B',[0,0,75])
space = array('B',[0,0,0])


TIME=0.03
UPDATE_FACTOR=3

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
    w = Wall(50,1)

    list_of_colors = [(0xff, 0xff, 0xff), (0xff, 0xff, 0x33), (0xff, 0x80, 0x00),(0xff, 0x00, 0x00)]

    def LerpColour(c1,c2,t):
        return (int(c1[0]+(c2[0]-c1[0])*t),int(c1[1]+(c2[1]-c1[1])*t),int(c1[2]+(c2[2]-c1[2])*t))

    while True:
        for n in range(len(list_of_colors)-1):
            for y in range((60/4)*n,(60/4)*(n+1)):
                color = LerpColour(list_of_colors[n],list_of_colors[n+1],(1.0/60)*y)
                print color
                for x in range(0,len(w.sticks)):
                    if(random.random() <= 0.3):
                        w.sticks[x].setLed(y, 0, 0, 0)
                    else:
                        w.sticks[x].setLed(y, *color)
                    w.sticks[x].updateLeds()

    # while True:
    #     rgb =  colorsys.hsv_to_rgb(random.random(), 1, 0.20)
    #     color = [int(rgb[0]*255), int(rgb[1]*255), int(rgb[2]*255)]
    #     up=True
    #     for i in range(0,len(w.sticks)/2):
    #         x1 = len(w.sticks)-1-i
    #         x2 = i
    #         print("Updating " + str(x1) + " and " + str(x2))
    #         for y in range(59*(not up),60*up, -1+(2*up) ):
    #             w.sticks[x1].setLed(y+1, *color)
    #             w.sticks[x1].updateLeds()
    #             w.sticks[x2].setLed(y+1, *color)
    #             w.sticks[x2].updateLeds()
    #             time.sleep(0.005)
    #         up=not up
    #     rgb =  colorsys.hsv_to_rgb(random.random(), 1, 0.20)
    #     color = [int(rgb[0]*255), int(rgb[1]*255), int(rgb[2]*255)]
    #     for i in range(0,len(w.sticks)/2):
    #         z1 = (len(w.sticks)/2) - i - 1
    #  z2 = (len(w.sticks)/2) + i
    #         for x in range(59*(not up),60*up, -1+(2*up) ):
    #             w.sticks[z1].setLed(x+1, *color)
    #             w.sticks[z1].updateLeds()
    #             w.sticks[z2].setLed(x+1, *color)
    #             w.sticks[z2].updateLeds()
    #             time.sleep(0.005)
    #         up=not up


if __name__ == "__main__":
    sys.exit(main(sys.argv))
