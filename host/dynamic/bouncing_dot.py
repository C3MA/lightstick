import socket
from array import array
import time
import sys

IP_EMULATOR = "192.168.23.5"
UDP_PORT = 2342
STICK_COUNT=3

red=array('B',[255,0,0])
green=array('B',[0,255,0])
blue=array('B',[0,0,255])
space = array('B',[0,0,0])


TIME=0.03
UPDATE_FACTOR=3

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

up=True
x=0
def generateDot(position):
	global up
	global x
	message = array('B')
	header=array('B', [position,0,0,0])
	message.extend(header)
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

def main(argv):
    if len(argv) < 2:
        sys.stderr.write("Usage: %s <database>\n" % (argv[0],))
        return 1

    if not os.path.exists(argv[1]):
        sys.stderr.write("ERROR: Database %r was not found!\n" % (argv[1],))
        return 1

	while True:
		for times in range(UPDATE_FACTOR):
			for i in range(1,STICK_COUNT+1):
				message = generateDot(i)
				sock.sendto(message, (IP_EMULATOR , UDP_PORT))
				time.sleep(TIME/UPDATE_FACTOR)

if __name__ == "__main__":
    sys.exit(main(sys.argv))
