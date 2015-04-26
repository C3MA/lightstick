import socket
from array import array
import time

UDP_IP1 = "192.168.23.66"
UDP_IP2 = "192.168.23.89"
UDP_IP3 = "192.168.23.85"
UDP_PORT = 2342

red=array('B',[50,0,0])
green=array('B',[0,50,0])
blue=array('B',[0,0,50])
space = array('B',[0,0,0])

print "UDP target IP:", UDP_IP1
print "UDP target port:", UDP_PORT

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
		sock.sendto(message, (UDP_IP1, UDP_PORT))
		sock.sendto(message, (UDP_IP2, UDP_PORT))
		sock.sendto(message, (UDP_IP3, UDP_PORT))
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
		sock.sendto(message, (UDP_IP1, UDP_PORT))
		sock.sendto(message, (UDP_IP2, UDP_PORT))
		sock.sendto(message, (UDP_IP3, UDP_PORT))
		time.sleep(TIME)
