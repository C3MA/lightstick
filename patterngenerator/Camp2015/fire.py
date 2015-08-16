#!/usr/bin/env python3

import time
import random
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

    def setBytes(self,byteArray):
    	self.setColor(byteArray[0],byteArray[1], byteArray[2])

class Stick(object): 

    def __init__(self, ip, position): 
        self.__leds = [ Led(0,50,0) for i in range(60)]
        self.__ip = ip
        self.__position = position
        self.__changed = True

    def update(self,force=False):
      if self.__changed or force:
        #print("Update "+ self.__ip)
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
        	self.get(int(i/3)).setColor(byteArray[i],byteArray[i+1], byteArray[i+2])
        self.__changed = True

    def clear(self):
    	self.setColor(0,0,0)

    def shiftUp(self):
    	for i in range(len(self.__leds)-1,0,-1):
    		fArray = self.get(i).getBytes()
    		if i+1 < len(self.__leds)-1:
    			self.get(i+1).setBytes(fArray)

    def shiftDown(self):
    	for i in range(0, len(self.__leds)):
    		fArray = self.get(i).getBytes()
    		if i-1 > 0:
    			self.get(i-1).setBytes(fArray)



class Wall(object):

	def __init__(self,startNumber, endNumber):
		self.__sticks = [ Stick(IP_HOST_BASE+str(i),i) for i in range(startNumber, endNumber)]

	def get(self, pos):
		return self.__sticks[pos]

	def update(self,force=False):
		for s in self.__sticks:
			s.update(force)

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
		fArray = array('B')
		for i in range(0,len(self.__sticks)):
			if i < len(self.__sticks) -2:
				fArray = self.__sticks[i].getBytes()
				self.__sticks[i].setBytes(fArray)
			else:
				fArray = self.__sticks[i].getBytes()
				self.__sticks[0].setBytes(fArray)

	def setColor(self, red, green, blue):
		for i in range(0, len(self.__sticks)):
			self.__sticks[i].setColor(red,green,blue)

	def clear(self):
		for stick in self.__sticks:
			stick.clear()

	def shiftUp(self):
		for stick in self.__sticks:
			stick.shiftUp()

	def shiftDown(self):
		for stick in self.__sticks:
			stick.shiftDown()

	def drawArrow(self,startStick,red,green,blue):
		if len(self.__sticks) < startStick+8:
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
			for i in range(15,45):
				self.get(startStick+5).get(i).setColor(red,green,blue)
			for i in range(15,45):
				self.get(startStick+6).get(i).setColor(red,green,blue)
			for i in range(15,45):
				self.get(startStick+7).get(i).setColor(red,green,blue)
			# for i in range(15,45):
			# 	self.get(startStick+8).get(i).setColor(red,green,blue)


sticks=50

w1 = Wall(1, sticks)
w1.clear()
w1.setColor(0,10,0)
w1.drawArrow(0,50,0,0)
w1.drawArrow(12,0,50,50)

# w2 = Wall(26,51)
# w2.clear()
# w2.setColor(0,10,0)
# w2.drawArrow(0,50,0,0)
# w2.drawArrow(12,0,50,50)


w1.update()
# w2.update()

lochArray=array('i',(-1 for i in range(0,50)))

while True:
	time.sleep(0.08)
	for a in range(0,sticks - 1):
		s1 = w1.get(a)
		for i in range(0,60):
			if 50 - (i * 6) > 0:
				s1.get(i).setColor(100,100 - (i * 6),50 - (i * 6))
			elif 100 - (i * 6) > 0:
				s1.get(i).setColor(100,100 - (i * 6),0)
			else:
				s1.get(i).setColor(100,0,0)

	for a in range(0,sticks - 1):
		s1 = w1.get(a)
		if lochArray[a] != -1: 
			s1.get(lochArray[a]).setColor(0,0,0)
			lochArray[a] = lochArray[a]+ random.randint(0,3)
			if lochArray[a] >= 60:
				lochArray[a] = -1
		else:
			ra = random.randint(0,3)
			if ra == 0:
				lochArray[a] = random.randint(0,3)
		for i in range(59, random.randint(47,59),-1):
			s1.get(i).setColor(0,0,0)
	w1.update()

# while True:
# 	time.sleep(0.2)
# 	w1.shiftRight()
# 	w1.update()
# 	w2.shiftLeft()
# 	w2.update()

# while True:
# 	w1.shiftLeft()
# 	w1.update()
# 	time.sleep(10/FPS)
	# for sNum in range(0,w1.len()):
	# 	s = w1.get(sNum)
	# 	s.setColor(200,0,0)
	# 	if sNum > 0:
	# 		s1 = w1.get(sNum -1)
	# 		s1.setColor(0,50,0)
	# 	else:
	# 		s1 = w1.get(w1.len()-1)
	# 		s1.setColor(0,50,0)
	# 	w1.update()
	# 	time.sleep(1/FPS)

# s1 = Stick('192.168.23.25',50)
# s1.updateStick()

# while True:
# 	for i in range(0,60):
# 		s1.get(i).setColor(255,255,255)
# 		if i < 59:
# 			s1.get(i+1).setColor(255,0,0)
# 		s1.updateStick()
# 		s1.get(i).setColor(0,50,0)
# 		time.sleep(0.1)

