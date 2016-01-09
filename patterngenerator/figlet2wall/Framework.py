#!/usr/bin/env python3

import time
from array import array
import socket
import sys
import subprocess
import threading

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
        #print("Update "+ str(self.__position) + " at " + str(self.__ip))
        global sock
        message = array('B')
        message.extend(array('B',[self.__position,0,0,0])) #Header
        message.extend(self.getBytes())
        try: 
            sock.sendto(message, (self.__ip, UDP_PORT))
            self.__changed = False
        except socket.error as socket_error:
            print("exception: ", socket_error, self.__ip)

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
    def __init__(self,start_number, stick_count, stick_led_count, simulatorIP=""):
        self.__foreground_color = [0,0,0]
        self.__background_color = [0,0,0]

        try:
            self.__stick_count = int(stick_count)
        except ValueError:
            raise ValueError("stick_count has to be a number!")

        try:
            self.__stick_led_count = int(stick_led_count)
        except ValueError:
            raise ValueError("stick_led_count has to be a number!")

        end_number = start_number + self.__stick_count + 1

        if (simulatorIP == "" or simulatorIP == None):
            self.__sticks = [ Stick(IP_HOST_BASE+str(i),i) for i in range(start_number, end_number)]
        else:
            print simulatorIP
            self.__sticks = [ Stick(simulatorIP,i) for i in range(start_number, end_number)]

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
            if self.__background_color:
                stick.setColor(self.background_color[0],
                               self.background_color[1],
                               self.background_color[2])
            else:
                stick.clear()

    def drawArrow(self,startStick,red,green,blue):
        if len(self.__sticks) < startStick+9:
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

    @property
    def foreground_color(self):
        return self.__foreground_color

    @foreground_color.setter
    def foreground_color(self, value):
        if len(value) == 3:
            self.__foreground_color = value
        else:
            raise ValueError("Need a list with 3 elements for RGB colors")

    @property
    def background_color(self):
        return self.__background_color

    @background_color.setter
    def background_color(self, value):
        if len(value) == 3:
            self.__background_color = value
        else:
            raise ValueError("Need a list with 3 elements for RGB colors")

    @property
    def stick_count(self):
        return self.__stick_count

    @property
    def stick_led_count(self):
        return self.__stick_led_count


class MarqueeText(object):
    class MarqueeAnimationThread(threading.Thread):
        def __init__(self, marquee_text_object, outputBuffer):
            super(MarqueeText.MarqueeAnimationThread, self).__init__()
            self._stop = threading.Event()

            self.__marquee_text = marquee_text_object
            self.__outputBuffer = outputBuffer

        def run(self):
            direction = 0   #0 = right; 1 = left
            offset = 0
            max_offset = len(self.__outputBuffer) - self.__marquee_text.wall_object.stick_count - 1

            while not self.stopped():
                self.__marquee_text.draw_text(self.__outputBuffer, offset)
                self.__marquee_text.wall_object.update()

                if direction == 0:
                    if offset < max_offset:
                        offset += 1
                    else:
                        direction = 1
                else:
                    if offset > 0:
                        offset -= 1
                    else:
                        direction = 0

                time.sleep(self.__marquee_text.tick_delay)
                self.__marquee_text.wall_object.clear()

        def stop(self):
            self._stop.set()

        def stopped(self):
            return self._stop.isSet()

    def __init__(self, wall_object, tick_delay=0.5, height_factor=1, text_color=None):
        self.wall_object = wall_object
        self._stick_count = self.wall_object.stick_count
        self._stick_height = self.wall_object.stick_led_count
        self._height_factor = height_factor
        self._text_color = text_color
        self.__tick_delay = tick_delay
        self._text = ""
        self.__marquee_thread = None

        if text_color:
            self._text_color = text_color
        else:
            self._text_color = wall_object.foreground_color

    def __start_new_marquee(self):
        outputBuffer = self.__generate_output_buffer(self.text)

        #Clean sticks
        self.wall_object.clear()

        #Is the text too big to show it as a single image?
        if self.wall_object.stick_count < len(outputBuffer):
            # Then we need a marquee

            # Stop potential existing thread
            if self.__marquee_thread:
                if not self.__marquee_thread.stopped():
                    self.__marquee_thread.stop()

            self.__marquee_thread = self.MarqueeAnimationThread(self, outputBuffer)
            self.__marquee_thread.setDaemon(True)
            self.__marquee_thread.start()
        else:
            # Draw single Frame
            self.draw_text(outputBuffer)


    def __generate_output_buffer(self, text=" "):
        # prepare text convertion buffer
        textBuffer = list()

        #Call figlet and make it give us a nice ascii art
        figletOutput = subprocess.check_output(["figlet", "-f", "banner", text])

        for y, line in enumerate(figletOutput.split("\n")):
            line = line.replace("\r", "")
            line = line.replace("\n", "")
            print(line)
            textBuffer.append([0] * len(line) )
            for i, c in enumerate(line):
                if c != " ":
                        textBuffer[y][i] = 1
                else:
                        textBuffer[y][i] = 0

        # Shrink it baby!
        outputBuffer = list()

        # For getting the dimension information, we'll use the first column and first row
        columns = len(textBuffer[0])
        rows = len(textBuffer) - 1 # We need to subtract 1, because the last line generates an empty list

        #Fill the outputBuffer with 0's
        outputBuffer=[[0 for row in range(0, rows)] for column in range(0, columns)]

        for column in range(0, columns):
            for row in range(0, rows):
                outputBuffer[column][row] = textBuffer[row][column]

        return outputBuffer


    def draw_text(self, outputBuffer, x_offset=0):
        for columnNo, column in enumerate(outputBuffer):
            for rowNo, row in enumerate(outputBuffer[columnNo]):

                s = self.wall_object.get(max(min(columnNo-x_offset, self._stick_count), 0))
                for x in range(0, self._height_factor):
                    offset = 18 - ((rowNo * self._height_factor) + x)
                    if row == 1:
                        s.get(offset).setColor(*self._text_color)

        self.wall_object.update()

    @property
    def tick_delay(self):
        return self.__tick_delay

    @tick_delay.setter
    def tick_delay(self, value):
        self.__tick_delay = value

    @property
    def text(self):
        return self._text

    @text.setter
    def text(self, value):
        self._text = value
        self.__start_new_marquee()

    @property
    def text_color(self):
        return self._text_color

    @text_color.setter
    def text_color(self, value):
        if len(value)==3:
            self._text_color = value
        else:
            raise ValueError("Need a list with 3 elements for RGB colors")
