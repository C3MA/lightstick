#!/usr/bin/env python
import Framework
import argparse
import time

def start_text():
    w1 = Framework.Wall(start_ip, stick_count, stick_height, args.simulator)

    w1.foreground_color = [0, 0, 60]
    w1.background_color = [0, 10, 0]

    myText = Framework.MarqueeText(w1, 0.3, height_factor)
    myText.text = "CCC MA"


if __name__ == '__main__':
    # Argument parsing for optional simulator
    parser = argparse.ArgumentParser(description='Text to Sticks')
    parser.add_argument('--simulator', help='IP address of an the simulator; e.g. --simulator 127.0.0.1')

    args = parser.parse_args()

    # Init variables
    height_factor = 2
    stick_height = 59
    stick_count = 25
    start_ip = 17		#192.168.23.17

    # The main part
    start_text()

    #Loop, in order to keep the main thread alive (so we are able to stop the
    #the programm with ctrl+c)
    while True:
        time.sleep(1)
