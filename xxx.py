import numpy as np
import argparse
import matplotlib.pyplot as plt



parser=argparse.ArgumentParser()
parser.add_argument('-s',type=float,help='odchylenie standardowe')
parser.add_argument('-u',type=float,help='srednia')
parser.add_argument('-r',type=float,nargs=2,help='zakres')
parser.add_argument('-w',type=float,help='wariacja')
args = parser.parse_args()


mean = args.u  
odch = args.s 
variance = args.w  
if variance: 
    odch = np.sqrt(variance)
if odch:
    variance=np.power(odch,2)
range_start, range_end = args.r 


x=np.linspace(range_start,range_end,1000)
y=(1/(variance*np.sqrt(2)))*(np.exp(-np.power((x-mean),2))/(2*np.power(variance,2)))



plt.plot(x,y)
plt.show()
#type=float

