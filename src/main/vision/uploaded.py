from cscore import CameraServer
from networktables import NetworkTablesInstance

import time
import cv2
import json
import numpy as np

garea = 0

myColors = [[2,96,78,18,172,231]]

imgResult = None
team = None

def findColor(img,myColors):
    imgHSV = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)  
    lower = np.array(myColors[0][0:3])
    upper = np.array(myColors[0][3:6])
    mask = cv2.inRange(imgHSV, lower, upper)
    #cv2.imshow("img",mask)
    getContours(mask)


def getContours(img):
    image, contours, hierarchy = cv2.findContours(img, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_NONE)

    for cnt in contours:

        area = cv2.contourArea(cnt)
        if area > 20:

            cv2.drawContours(imgResult,cnt, -1, (255,0,0), 3)
            peri = cv2.arcLength(cnt, True)
            global garea
            garea = area

            approx = cv2.approxPolyDP(cnt, 0.02 * peri, True)
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
   
   input_stream = CameraServer.getInstance().getVideo()
   output_stream = CameraServer.getInstance().putVideo('Processed', width, height)
   sink = CameraServer.getInstance().getVideo()

   # Table for vision output information
   ntinst = NetworkTablesInstance.getDefault()
   ip = '192.168.102.225'
   print("Setting up NetworkTables client for team {} at {}".format(team,ip))
   ntinst.startClient(ip)
    
   vision_nt = ntinst.getTable('Vision')
   

   # Wait for NetworkTables to start
   time.sleep(0.5)
   count = 0
   while True:


        global imgResult
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
main()    
