import socket
from array import array
import time

IP_BASE = "192.168.23"
IP4LOAD = "192.168.23.91"
STICK_COUNT=3
UDP_PORT = 2342

red=array('B',[50,0,0])
green=array('B',[0,50,0])
blue=array('B',[0,0,50])
space = array('B',[0,0,0])


TIME=0.03
UPDATE_FACTOR=3

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

up=True
x=0
def generateDot():
	global up
	global x
	message = array('B')
	if up:
		if x < 60:		
			for s in range(0, x):
				message.extend(space)
			message.extend(red)
			message.extend(green)
			message.extend(blue)
			x=x+1
		else:
			up=False
	else:
		if x > 0:
			for s in range(0, x):
				message.extend(space)
                        message.extend(red)
                        message.extend(green)
                        message.extend(blue)
			message.extend(space)
			x=x-1
		else:
			up=True
	return message	

while True:
	message = generateDot()
	for times in range(UPDATE_FACTOR):
		for i in range(1,STICK_COUNT+1):
			sock.sendto(message, (IP_BASE + "." + str(i) , UDP_PORT))
		for i in range(1,70):
			sock.sendto(message, (IP4LOAD , UDP_PORT))
		time.sleep(TIME/UPDATE_FACTOR)
