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

boundingCenter = 0

targetDetected = False

corners = 0
sumCorners = 0

areaRatio = 0
largestAreaRatio = 0

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

    tolerance = .15
    idealRatio = 0.1 #ideal = .166
    global garea
    global areaRatio
    global largestAreaRatio
    garea = 0
    if len(contours) > 0:
        global targetDetected
        largest = contours[0]
        area = 0
        for contour in contours:
            
            contourArea = cv2.contourArea(contour) #area of the particle
            x, y, w, h, = cv2.boundingRect(contour)
            boundingArea = w * h
            if (boundingArea < 200):
                continue
            areaRatio = contourArea/boundingArea
            if areaRatio > idealRatio - tolerance and areaRatio < idealRatio + tolerance:
                largest = contour
                area = boundingArea
                largestAreaRatio = areaRatio
            #if cv2.contourArea(contour) > cv2.contourArea(largest):
                #largest = contour

        
        
        peri = cv2.arcLength(largest, True)
        approx = cv2.approxPolyDP(largest, 0.015 * peri, True)
        global corners
        corners = len(approx)
        if area > 100: #and corners < 6.5:
            targetDetected = True
            global boundingCenter
            cv2.drawContours(imgResult,largest, -1, (255,0,0), 3)
            peri = cv2.arcLength(largest, True)
            garea = area

            approx = cv2.approxPolyDP(largest, 0.02 * peri, True)
            x, y, w, h, = cv2.boundingRect(approx)
            boundingCenter = x + (w/2)
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
   ip = 'Computer IP here'
   #print("Setting up NetworkTables client for team {} at {}".format(team,ip))
   ntinst.startClientTeam(team)
   #ntinst.startClient(ip)
   vision_nt = ntinst.getTable('Shuffleboard/Vision')

   vision_nt.putNumber('hueMin',1)
   vision_nt.putNumber('hueMax',255)
   vision_nt.putNumber('satMin',1)
   vision_nt.putNumber('satMax',255)
   vision_nt.putNumber('valMin',1)
   vision_nt.putNumber('valMax',255)





   # Wait for NetworkTables to start
   time.sleep(0.5)
   count = 0
   sumArea = 0
   while True:


        global boundingCenter
        global imgResult
        global mask
        global hueMin
        global hueMax
        global satMin
        global satMax
        global valMin
        global valMax
        global myColors
        global targetDetected
        global sumCorners
        global corners
        global areaRatio
        global largestAreaRatio
        camCenter = (width * 4)/2
        offset = camCenter - boundingCenter
        input_img = None
        frame_time, input_img = sink.grabFrame(input_img)


        # Notify output of error and skip iteration
        if frame_time == 0:
            output_stream.notifyError(sink.getError())
            continue

        # Convert to HSV and threshold image
        imgResult = input_img.copy()
        targetDetected = False
        findColor(input_img,myColors)
        if targetDetected:
            sumArea += garea
            sumCorners += corners
            count +=1
        loopLen = 25
        vision_nt.putNumber('realTimeArea',garea)
        vision_nt.putNumber('areaRatio',areaRatio)
        vision_nt.putNumber('largestAreaRatio', largestAreaRatio)
        if count >= loopLen:
            average = sumArea/count
            distance = (.00004 * average * average) - (.2104 * average) + 345.33

            vision_nt.putNumber('distance',distance)
            vision_nt.putNumber('area',average) 
            vision_nt.putNumber('offset',-offset)

            vision_nt.putNumber('corners',sumCorners/loopLen)
            sumCorners = 0
            sumArea = 0
            count = 0
        if targetDetected:

            vision_nt.putNumber('targetDetected',1)
        else:

            vision_nt.putNumber('targetDetected',0)
            
            

        vision_nt.putNumber('debug', count)
        
        cvsrc.putFrame(imgResult)

        hueMin = int(vision_nt.getNumber('hueMin',255))
        hueMax = int(vision_nt.getNumber('hueMax',255))
        satMin = int(vision_nt.getNumber('satMin',255))
        satMax = int(vision_nt.getNumber('satMax',255))
        valMin = int(vision_nt.getNumber('valMin',255))
        valMax = int(vision_nt.getNumber('valMax',255))

        myColors = [[hueMin,satMin,valMin,hueMax,satMax,valMax]]
        cvmask.putFrame(mask)
main()    
