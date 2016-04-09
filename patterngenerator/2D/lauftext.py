#!/usr/bin/env python3

import time
from array import array
import socket
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
        print("Update "+ self.__ip)
        global sock
        message = array('B')
        message.extend(array('B',[0,0,0,0])) #Header
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

	def __init__(self,startNumber, endNumber):
		self.__sticks = [ Stick(IP_HOST_BASE+str(i),i) for i in range(startNumber, endNumber)]

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
		if len(self.__sticks) < startStick+0:
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
			#	self.get(startStick+5).get(i).setColor(red,green,blue)
			#for i in range(15,45):
			#	self.get(startStick+6).get(i).setColor(red,green,blue)
			#for i in range(15,45):
			#	self.get(startStick+7).get(i).setColor(red,green,blue)
			# for i in range(15,45):
			# 	self.get(startStick+8).get(i).setColor(red,green,blue)



#i = 0

w = Wall(24,17)
w.clear()
w.setColor(0,0,20)
w.update()

while True:
   time.sleep(0.5)
	w.shiftLeft()
