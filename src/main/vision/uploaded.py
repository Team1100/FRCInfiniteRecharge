from cscore import CameraServer
from networktables import NetworkTablesInstance

import time
import cv2
import json
import numpy as np

garea = 0

myColors = [[158,0,255,168,13,255]]

imgResult = None
mask = None
team = None

hueMin = 1
hueMax = 255
satMin = 1
satMax = 255
valMin = 1
valMax = 255

def findColor(img,myColors):
    global mask
    imgHSV = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)  
    lower = np.array(myColors[0][0:3])
    upper = np.array(myColors[0][3:6])
    mask = cv2.inRange(imgHSV, lower, upper)
    #cv2.imshow("img",mask)
    getContours(mask)


def getContours(img):
    image, contours, hierarchy = cv2.findContours(img, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
    if len(contours) > 0:
        largest = contours[0]
        for contour in contours:
            if cv2.contourArea(contour) > cv2.contourArea(largest):
                largest = contour
        area = cv2.contourArea(largest)
        if area > 50:

            cv2.drawContours(imgResult,largest, -1, (255,0,0), 3)
            peri = cv2.arcLength(largest, True)
            global garea
            garea = area

            approx = cv2.approxPolyDP(largest, 0.02 * peri, True)
            x, y, w, h, = cv2.boundingRect(approx)
            cv2.rectangle(imgResult, (x,y),(x+w,y+h),(0,255,0),3)


def main():
   fp = open('/boot/frc.json')
   config = json.load(fp)
   camera = config['cameras'][0]
   global team
   team = config["team"]

   width = camera['width']
   height = camera['height']
   cserver = CameraServer.getInstance()
   cserver.startAutomaticCapture()
   cvsrc = cserver.putVideo("visionCam", width, height)
   cvmask = cserver.putVideo("maskCam", width, height) #new
   
   input_stream = CameraServer.getInstance().getVideo()
   output_stream = CameraServer.getInstance().putVideo('Processed', width, height)
   sink = CameraServer.getInstance().getVideo()

   # Table for vision output information
   ntinst = NetworkTablesInstance.getDefault()
   ip = '192.168.102.225'
   print("Setting up NetworkTables client for team {} at {}".format(team,ip))
   #ntinst.startClientTeam(team)
   ntinst.startClient(ip)
   vision_nt = ntinst.getTable('Vision')
   

   # Wait for NetworkTables to start
   time.sleep(0.5)
   count = 0

   while True:


        global imgResult
        global mask
        global hueMin
        global hueMax
        global satMin
        global satMax
        global valMin
        global valMax
        global myColors
        input_img = None
        frame_time, input_img = sink.grabFrame(input_img)


        # Notify output of error and skip iteration
        if frame_time == 0:
            output_stream.notifyError(sink.getError())
            continue

        # Convert to HSV and threshold image
        imgResult = input_img.copy()
        findColor(input_img,myColors)
        count += 1
        vision_nt.putNumber('area', garea)
        vision_nt.putNumber('debug', count)
        
        cvsrc.putFrame(imgResult)
        if count >= 1000:
            count = 0
        hueMin = int(vision_nt.getNumber('hueMin',1))
        hueMax = int(vision_nt.getNumber('hueMax',255))
        satMin = int(vision_nt.getNumber('satMin',1))
        satMax = int(vision_nt.getNumber('satMax',255))
        valMin = int(vision_nt.getNumber('valMin',1))
        valMax = int(vision_nt.getNumber('valMax',255))
        myColors = [[hueMin,satMin,valMin,hueMax,satMax,valMax]]
        cvmask.putFrame(mask)
main()    
