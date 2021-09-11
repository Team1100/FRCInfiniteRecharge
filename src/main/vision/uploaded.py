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

hueMin = 13
hueMax = 255
satMin = 1
satMax = 66
valMin = 235
valMax = 255

boundingCenterX = 0
centerY = 0
centerX = 0
targetDetected = False


areaRatio = 0 # this is the areaRatio of every contour that is seen by the camera
largestAreaRatio = 0 # this is the areaRatio of the target once it has been isolated
aspectRatio = 0 # this is the aspectRatio of every contour that is seen by the camera
largestAspectRatio = 0 # this is the aspectRatio fo the target once it has been isolated

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

    tolerance = .2
    idealRatio = 0.1 # this is the ideal ratio for the area ratio value. 
    idealAspectRatio = 2 # this is the ideal aspect ratio based off of the diagram but can be changed as needed.
    aspectTolerance = .44 # this is the tolerance for finding the target with the right aspect ratio
                         # start off with a large tolerance, and if the ideal ratio is correct, lower the tolerance as needed. 
    global garea
    global areaRatio
    global largestAreaRatio
    global aspectRatio  #width/height of the box
    global largestAspectRatio

    garea = 0
    if len(contours) > 0:
        global targetDetected
        largest = contours[0]
        area = 0
        for contour in contours:
            
            contourArea = cv2.contourArea(contour) #area of the particle
            x, y, w, h, = cv2.boundingRect(contour)
            boundingArea = w * h
            if (boundingArea < 1100):
                continue
            areaRatio = contourArea/boundingArea
            aspectRatio = w/h
            if areaRatio > idealRatio - tolerance and areaRatio < idealRatio + tolerance: # if the target is within the right area ratio range, it is possibly the correct target
                if aspectRatio > idealAspectRatio - aspectTolerance and aspectRatio < idealAspectRatio + aspectTolerance: # if the target is within the correct aspect ratio range aswell, it is definitely the right target
                    largest = contour
                    area = boundingArea
                    largestAreaRatio = areaRatio
                    largestAspectRatio = aspectRatio
                else:
                    print(str(aspectRatio))

        
        
        peri = cv2.arcLength(largest, True)
        approx = cv2.approxPolyDP(largest, 0.015 * peri, True)
        if area > 100: # may not need this if statement. Consider deleting in the future.
            targetDetected = True
            global boundingCenterX #center of the bounding box x axis
            global boundingCenterY # center of the bounding box y axis
            global centerX
            global centerY
            cv2.drawContours(imgResult,largest, -1, (255,0,0), 3)
            peri = cv2.arcLength(largest, True)
            garea = area

            approx = cv2.approxPolyDP(largest, 0.02 * peri, True)
            x, y, w, h, = cv2.boundingRect(approx)
            boundingCenterX = x + w/2
            centerX = ((x + (w/2))-320)/320
            centerY = ((y + (h/2))-240)/240

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
   ntinst.startClientTeam(team)
   #ntinst.startClient(ip)
   vision_nt = ntinst.getTable('Shuffleboard/Vision')

   vision_nt.putNumber('hueMin',13)
   vision_nt.putNumber('hueMax',255)
   vision_nt.putNumber('satMin',1)
   vision_nt.putNumber('satMax',66)
   vision_nt.putNumber('valMin',235)
   vision_nt.putNumber('valMax',255)





   # Wait for NetworkTables to start
   time.sleep(0.5)
   count = 0
   sumArea = 0
   # used to set a time delay after losing the target to report a lost target
   targetDetTol = 1.0 
   t1 = 0
   t2 = 0
   while True:


        global boundingCenterX
        global boundingCenterY
        global centerX
        global centerY
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
        global areaRatio
        global largestAreaRatio
        global aspectRatio
        global largestAspectRatio

        camCenter = (width * 4)/2
        offset = camCenter - boundingCenterX
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
        
        t2 = time.clock_gettime(time.CLOCK_MONOTONIC) # gets the current "time"
        timeDiff = t2-t1 # difference between the most recent time and the time recorded when the target was last seen

        if targetDetected:
            sumArea += garea
            targetDetTolCount = 0
            t1 = t2
            vision_nt.putNumber('targetDetected',1)
        else: # only sets updates the targetDetected if a certain amount of time has passed
            if timeDiff > targetDetTol:
                vision_nt.putNumber('targetDetected',0)
         
        count +=1
        loopLen = 25
        vision_nt.putNumber('realTimeArea',garea) # these lines put all the necessary data on network tables, which are then displayed on shuffleboard
        vision_nt.putNumber('areaRatio',areaRatio)
        vision_nt.putNumber('offset',-offset)
        vision_nt.putNumber('largestAreaRatio', largestAreaRatio)

        vision_nt.putNumber('aspectRatio', aspectRatio)
        
        vision_nt.putNumber('largestAspectRatio', largestAspectRatio)
        vision_nt.putNumber('CenterOfBoxX', centerX)
        vision_nt.putNumber('CenterOfBoxY', centerY)

        if sumArea > 0 and count >= loopLen:
        #if count >= loopLen:
            average = sumArea/count
            distance = (20235 * (average ** -.558))

            vision_nt.putNumber('distance',distance)
            vision_nt.putNumber('area',average) 
            sumArea = 0
            count = 0

            
            

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
