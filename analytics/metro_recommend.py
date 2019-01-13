# -*- coding: utf-8 -*-
"""
Created on Sat Jan 12 15:19:27 2019

@author: Yatharth
"""
import geopy.distance
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
points = pd.read_csv(r'C:\Users\Yatharth\Desktop\map_dataset3.csv')
metros = pd.read_csv(r'C:\Users\Yatharth\Desktop\dataminer (3).csv')
point_lat=points['lat']
point_long=points['long']
metro_lat=metros['lat'].values.reshape((metros['lat'].shape[0],)).astype(float)
metro_long=metros['long'].values.reshape((metros['long'].shape[0],)).astype(float)
plt.scatter(metro_lat,metro_long)
plt.grid(True)

mx=np.max(point_lat)
minx=np.min(point_lat)
my=np.max(point_long)
miny=np.min(point_long)
deltax=(mx-minx)/8
deltay=(my-miny)/12
Ax=[]
Ay=[]
c=0
initialx = minx + deltax
fx = minx
W = []
while initialx<=mx+deltax:
    fy = miny
    initialy=miny+deltay
    while initialy<=my+deltay:
        By=[]
        Bx=[]
        w=0
        for j in range(point_lat.shape[0]):
            if initialx > point_lat[j] >= fx and initialy > point_long[j] >= fy:
                By.append((point_long[j]))
                Bx.append((point_lat[j]))
                w+=1
        Ay.append(By)
        Ax.append(Bx)
        if w!=0:
            W.append(w)
        c+=1
        fy=initialy
        initialy += deltay
    fx = initialx
    initialx += deltax
def average(Ax):
    Avx=[]
    for x in Ax:
        if x!=[]:
            x=np.asarray(x)
            Avx.append(np.mean(x))
    return Avx
Avx=average(Ax)
Avy=average(Ay)
Wmax=max(W)
Wmin=min(W)
tot=Wmax-Wmin
HighX=[]
ModerateX=[]
HighY=[]
ModerateY=[]
for i in range(len(W)):
    if W[i]<=(Wmin+8*tot/10) and W[i]>(Wmin+2*tot/3):
        ModerateX.append(Avx[i])
        ModerateY.append(Avy[i])
    elif W[i]>(Wmin+8*tot/10):
        HighX.append(Avx[i])
        HighY.append(Avy[i])
        #plt.scatter(Avx[i], Avy[i], c='red')
plt.scatter(ModerateX,ModerateY,color="Orange",label="Moderate")
plt.scatter(HighX,HighY,color="Red",label="High",marker="^")
#plt.plot(HighX,HighY,color="Red",label="High")
plt.legend()
plt.grid(True)

R = 6373.0
Rem_point_lat=[]
Rem_point_long=[]

for i in range(0,len(HighX)):
    x=HighX[i]
    y=HighY[i]
    distances=[]
    for i in range(0,len(metro_lat)):
        metro_lat_x = metro_lat[i]      
        metro_long_y = metro_long[i]
        distance = geopy.distance.distance((metro_lat_x,metro_long_y),(x,y))
        distances.append(distance)
        
    
    if(min(distances)>5):
        Rem_point_lat.append(x)
        Rem_point_long.append(y)


for i in range(0,len(ModerateX)):
    x=ModerateX[i]
    y=ModerateY[i]
    distances=[]
    for i in range(0,len(metro_lat)):
        metro_lat_x = metro_lat[i]      
        metro_long_y = metro_long[i]
        distance = geopy.distance.distance((metro_lat_x,metro_long_y),(x,y))
        distances.append(distance)
        
    if(min(distances)>10):
        Rem_point_lat.append(x)
        Rem_point_long.append(y)
    

plt.scatter(Rem_point_lat,Rem_point_long,color="Green")
plt.plot(Rem_point_lat,Rem_point_long,color="Green")
#plt.show()        
        