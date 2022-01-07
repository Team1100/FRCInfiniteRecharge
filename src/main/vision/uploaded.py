from cscore import CameraServer
from networktables import NetworkTablesInstance

import time
import cv2
import json
import numpy as np
import math

class CameraView(object):
    def __init__(self, camera):
        self.camera = camera
        self.width = self.camera['width']
        self.height = self.camera['height']

class TargetBoundingBox(object):
    def __init__(self, imageResult, approx):
        self.imageResult = imageResult
        self.x, self.y, self.w, self.h, = cv2.boundingRect(approx)
        self.boundingCenterX = self.x + self.w/2
        self.centerX = ((self.x + (self.w/2))-320)/320
        self.centerY = ((self.y + (self.h/2))-180)/180 * -1

    def drawRectangle(self):
        # Draw rectangle on the Image
        cv2.rectangle(self.imageResult, (self.x,self.y),(self.x+self.w,self.y+self.h),(0,255,0),3)

class VisionApplication(object):
    def __init__(self):
        self.garea = 0

        self.imgResult = None
        self.mask = None
        self.team = None

        self.hueMin = 13
        self.hueMax = 255
        self.satMin = 1
        self.satMax = 66
        self.valMin = 235
        self.valMax = 255
        self.myColors = [[self.hueMin,self.satMin,self.valMin,self.hueMax,self.satMax,self.valMax]]

        self.targetDetected = False
        self.areaRatio = 0 # this is the areaRatio of every contour that is seen by the camera
        self.largestAreaRatio = 0 # this is the areaRatio of the target once it has been isolated
        self.aspectRatio = 0 # this is the aspectRatio of every contour that is seen by the camera
        self.largestAspectRatio = 0 # this is the aspectRatio fo the target once it has been isolated

        self.advancedDistance = 0
        self.vision_nt = None 
        self.mask = None
        self.contours = None
        self.target = None

        self.boundingBox = None # Target bounding box

        # Initialize configuration
        self.config = self.readConfig()
        self.team = self.config["team"]
        self.camera = CameraView(self.config['cameras'][0])

        # Initialize Camera Server
        self.initializeCameraServer()

        # Initialize NetworkTables Client
        self.initializeNetworkTables()

    def readConfig(self):
        config = None
        with open('/boot/frc.json') as fp:
            config = json.load(fp)
        return config

    def initializeCameraServer(self):
        cserver = CameraServer.getInstance()
        cserver.startAutomaticCapture()
        self.cvsrc = cserver.putVideo("visionCam", self.camera.width, self.camera.height)
        self.cvmask = cserver.putVideo("maskCam", self.camera.width, self.camera.height) #new
        
        self.input_stream = CameraServer.getInstance().getVideo()
        self.output_stream = CameraServer.getInstance().putVideo('Processed', self.camera.width, self.camera.height)
        self.sink = CameraServer.getInstance().getVideo()

    def initializeNetworkTables(self):
        # Table for vision output information
        ntinst = NetworkTablesInstance.getDefault()
        ip = ''
        print("Setting up NetworkTables client for team {} at {}".format(self.team,ip))
        ntinst.startClientTeam(self.team)
        #ntinst.startClient(ip)
        self.vision_nt = ntinst.getTable('Shuffleboard/Vision')

        self.putMaskingValues()

        # Wait for NetworkTables to start
        time.sleep(0.5)

    def putMaskingValues(self):
        self.vision_nt.putNumber('hueMin',self.hueMin)
        self.vision_nt.putNumber('hueMax',self.hueMax)
        self.vision_nt.putNumber('satMin',self.satMin)
        self.vision_nt.putNumber('satMax',self.satMax)
        self.vision_nt.putNumber('valMin',self.valMin)
        self.vision_nt.putNumber('valMax',self.valMax)

    def getMaskingValues(self):
        self.hueMin = int(self.vision_nt.getNumber('hueMin',255))
        self.hueMax = int(self.vision_nt.getNumber('hueMax',255))
        self.satMin = int(self.vision_nt.getNumber('satMin',255))
        self.satMax = int(self.vision_nt.getNumber('satMax',255))
        self.valMin = int(self.vision_nt.getNumber('valMin',255))
        self.valMax = int(self.vision_nt.getNumber('valMax',255))
        self.myColors = [[self.hueMin,self.satMin,self.valMin,self.hueMax,self.satMax,self.valMax]]

    def runApplication(self):
        count = 0
        sumArea = 0
        # used to set a time delay after losing the target to report a lost target
        targetDetTol = 1.0 
        t1 = 0
        t2 = 0
        while True:
            camCenter = (self.camera.width * 4)/2
            input_img = None
            frame_time, input_img = self.sink.grabFrame(input_img)
            #(height of target (m) - height of camera in up position (m))/tan(pitch + angle of camera)

            # Notify output of error and skip iteration
            if frame_time == 0:
                self.output_stream.notifyError(self.sink.getError())
                continue

            # Convert to HSV and threshold image
            self.imgResult = input_img.copy()
            self.targetDetected = False
            self.mask = self.getImageMask(input_img,self.myColors)
            self.contours = self.getContours(self.mask)
            self.target = self.isolateTarget(self.contours)
            self.boundingBox = self.drawBoundingBox(self.target)
            
            pitch = (self.boundingBox.centerY/2) * 48.9417

            self.advancedDistance = (2.298 - .9779) / math.tan(math.radians(pitch + 20.93552078))
            offset = camCenter - self.boundingBox.boundingCenterX
            t2 = time.clock_gettime(time.CLOCK_MONOTONIC) # gets the current "time"
            timeDiff = t2-t1 # difference between the most recent time and the time recorded when the target was last seen

            if self.targetDetected:
                sumArea += self.garea
                targetDetTolCount = 0
                t1 = t2
                self.vision_nt.putNumber('targetDetected',1)
            else: # only sets updates the targetDetected if a certain amount of time has passed
                if timeDiff > targetDetTol:
                    self.vision_nt.putNumber('targetDetected',0)
             
            count +=1
            loopLen = 25
            self.vision_nt.putNumber('realTimeArea',self.garea) # these lines put all the necessary data on network tables, which are then displayed on shuffleboard
            self.vision_nt.putNumber('areaRatio',self.areaRatio)
            self.vision_nt.putNumber('largestAreaRatio', self.largestAreaRatio)

            self.vision_nt.putNumber('aspectRatio', self.aspectRatio)
            
            self.vision_nt.putNumber('largestAspectRatio', self.largestAspectRatio)
            self.vision_nt.putNumber('CenterOfBoxX', self.boundingBox.centerX)
            self.vision_nt.putNumber('CenterOfBoxY', self.boundingBox.centerY)

            self.vision_nt.putNumber('advancedDistance', self.advancedDistance)
            self.vision_nt.putNumber('offset',-offset)

            if sumArea > 0 and count >= loopLen:
            #if count >= loopLen:
                average = sumArea/count
                distance = (20235 * (average ** -.558))

                self.vision_nt.putNumber('distance',distance)
                self.vision_nt.putNumber('area',average) 
                sumArea = 0
                count = 0

            self.vision_nt.putNumber('pitch', pitch)
            self.cvsrc.putFrame(self.imgResult)
            self.getMaskingValues()

            self.cvmask.putFrame(self.mask)

    def getImageMask(self, img, myColors):
        imgHSV = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)  
        lower = np.array(myColors[0][0:3])
        upper = np.array(myColors[0][3:6])
        mask = cv2.inRange(imgHSV, lower, upper)
        #cv2.imshow("img",mask)
        return mask


    def getContours(self, img):
        image, contours, hierarchy = cv2.findContours(img, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)
        return contours

    def isolateTarget(self, contours):
        tolerance = .225
        idealRatio = 0.1 # this is the ideal ratio for the area ratio value. 
        idealAspectRatio = 1.8 # this is the ideal aspect ratio based off of the diagram but can be changed as needed.
        aspectTolerance = .44 # this is the tolerance for finding the target with the right aspect ratio
        # start off with a large tolerance, and if the ideal ratio is correct, lower the tolerance as needed. 
        self.garea = 0
        target = self.target # Default to the "old" target
        if len(contours) > 0:
            largest = contours[0]
            area = 0
            for contour in contours:
                contourArea = cv2.contourArea(contour) #area of the particle
                x, y, w, h, = cv2.boundingRect(contour)
                boundingArea = w * h
                if (boundingArea < 1100):
                    continue
                self.areaRatio = contourArea/boundingArea
                self.aspectRatio = w/h
                if self.areaRatio > idealRatio - tolerance and self.areaRatio < idealRatio + tolerance: # if the target is within the right area ratio range, it is possibly the correct target
                    if self.aspectRatio > idealAspectRatio - aspectTolerance and self.aspectRatio < idealAspectRatio + aspectTolerance: # if the target is within the correct aspect ratio range aswell, it is definitely the right target
                        largest = contour
                        area = boundingArea
                        self.largestAreaRatio = self.areaRatio
                        self.largestAspectRatio = self.aspectRatio
                        self.targetDetected = True
                        self.garea = area
                        target = largest
                        # Draw the contours
                        cv2.drawContours(self.imgResult, target, -1, (255,0,0), 3)
        return target

    def drawBoundingBox(self, target):
        peri = cv2.arcLength(target, True)
        approx = cv2.approxPolyDP(target, 0.02 * peri, True)
        boundingBox = TargetBoundingBox(self.imgResult, approx)
        boundingBox.drawRectangle()
        return boundingBox

def main():
    visionApp = VisionApplication()
    visionApp.runApplication()

main()   
