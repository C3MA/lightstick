import socket
from array import array
import time
import sys

IP_HOST_BASE = "192.168.23."
UDP_PORT = 2342

red=array('B',[255,0,0])
green=array('B',[0,255,0])
blue=array('B',[0,0,255])
space = array('B',[0,0,0])


TIME=0.1
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
                        message.extend(space)
                        message.extend(space)
                        message.extend(space)
                        message.extend(space)
                        message.extend(space)
                        message.extend(space)
                        message.extend(space)
                        message.extend(space)
                        message.extend(space)
                        message.extend(space)
                        x=x-1
                else:
                        up=True
        return message  

def main(argv):
    if len(argv) < 3:
        sys.stderr.write("Usage: %s <esp|simulator>\n- esp\t\trequires the amount of sticks as second parameter\n- simulator\trequires the IP of the simulator as second parameter and the stickcount as third one\n" % (argv[0],))
        sys.stderr.write("Example: %s esp 3\n" % (argv[0]))
        sys.stderr.write("Example: %s simulator 192.168.23.5 3\n" % (argv[0]))
        return 1

    stickcount=0
    targetIPs=[]
    if argv[1] == "esp":
        print("Use the hardware consisting out of %s sticks" % (argv[2],)) 
        stickcount=int(argv[2])
        for i in range(1,stickcount+1):
            targetIPs.append(IP_HOST_BASE + str(i))
    elif argv[1] == "simulator" and len(argv) == 4:
        print("Simulate to IP %s with %s sticks\n" % (argv[2],argv[3]))
        stickcount=int(argv[3])
        for i in range(1,stickcount+1):
            targetIPs.append(argv[2])
    else:
        sys.stderr.write("Wrong paramater combination!\n See Usage: %s\n" % (argv[0],))
        return 1
        
    sys.stdout.write("Send to the following IPs: ")    
    for i in range(1,stickcount+1):
        sys.stdout.write(targetIPs[i-1] + " ")
    sys.stdout.write("\n")

    while True:
        for times in range(UPDATE_FACTOR):
                for i in range(1,stickcount+1):
                        message = generateDot(i)
                        sock.sendto(message, (targetIPs[i-1], UDP_PORT))
                        time.sleep(TIME/UPDATE_FACTOR)

if __name__ == "__main__":
    sys.exit(main(sys.argv))
