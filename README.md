# Track-Area
This project was used as a Proof of Concept to one of the government project. 

## Problem Statement
To compute area traversed by a tractor while ploughing a field as precisely as possible. A sensor is mounted on a tractor while it is ploughing a field.
The sensor is emitting co-ordinates and they are processed to generate a GPGGA string, which contains latitude and longitude of a location along with additional information.

## Solution
A REST endpoint is exposed to receive the GPGGA string from the sensor device which is generated after some processing.
Points keep coming at a frequency of 1-2 points per second. A report should be generated for every session of levelling the field. 
There is no mechanical signal to know when to start and end a session. So if a point comes after some threshold delay (maybe 15 minutes), we consider the session to be closed.


Entities in the system :

a) A user who is usually a farmer

b) For a farmer, there may be several devices registered 

c) Each device may be doing multiple session (maybe 2-4) per day

So to solve this a cron job runs per user per device to check if at this instant, is the session for that device is closed. 
To know that session is closed we check the time difference between the last two points sorted by the time of creation.
If so, we consider this session to be closed. 
Now that we have the points that were traversed we need to calculate the area that has been covered.

So to calculate the area, it is easier to have a regular geometry from these arbitrary set of points. So we create a convex hull (https://en.wikipedia.org/wiki/Convex_hull) from these set of point.
After getting the convex hull, we can simply calculate the area of n shaped polygon by using (https://en.wikipedia.org/wiki/Shoelace_formula).

### UI
![UI](https://github.com/atulkgupta9/track-area/blob/master/src/main/resources/img/img-2.gif)

### Sample Report
![Report](https://github.com/atulkgupta9/track-area/blob/master/src/main/resources/img/report.png)
![Plot](https://github.com/atulkgupta9/track-area/blob/master/src/main/resources/img/plot.png)
