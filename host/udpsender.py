import socket
from array import array
import time

IP_BASE = "192.168.23"
STICK_COUNT=3
UDP_PORT = 2342

red=array('B',[50,0,0])
green=array('B',[0,50,0])
blue=array('B',[0,0,50])
space = array('B',[0,0,0])


TIME=0.01

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

while True:
	for x in range(60):
		message = array('B')
		for s in range(0, x):
			message.extend(space)
		message.extend(red)
		message.extend(green)
		message.extend(blue)
		for i in range(1,STICK_COUNT+1):
			sock.sendto(message, (IP_BASE + "." + str(i) , UDP_PORT))
		time.sleep(TIME)
	for y in range(60):
		x=60-y
		message = array('B')
		for s in range(0, x):
			message.extend(space)
		message.extend(blue)
		message.extend(green)
		message.extend(red)
		message.extend(space)
		for i in range(1,STICK_COUNT+1):
			sock.sendto(message, (IP_BASE + "." + str(i), UDP_PORT))
		time.sleep(TIME)
