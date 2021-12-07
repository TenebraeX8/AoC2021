from aoc import *
from functools import reduce
import math
import numpy as np

if __name__ == '__main__':
	print(sum([abs(nr) for nr in np.array([int(x) for x in read_input(7)[0].split(",")] - np.median([int(x) for x in read_input(7)[0].split(",")]))]), sum(sum(range(y+1)) for y in [int(abs(nr)) for nr in np.array([int(x) for x in read_input(7)[0].split(",")]) - math.floor(np.mean([int(x) for x in read_input(7)[0].split(",")]))]))
