from aoc import *
import numpy as np

if __name__ == '__main__':
	#Part 1
	print(int("".join([str(np.bincount(x).argmax()) for x in np.array([[int(y) for y in x.strip()] for x in read_input(3)]).transpose()]), 2) * int("".join([str(np.bincount(x).argmin()) for x in np.array([[int(y) for y in x.strip()] for x in read_input(3)]).transpose()]), 2))

	#Part 2 - need to find a way to do unbound iterations	
	#print([list(filter(lambda t: t[0][t[1]] == str(np.bincount(np.array([[int(y) for y in x.strip()] for x in read_input(3)]).transpose()[t[0]])), [(i, elem) for i, elem in enumerate(read_input(3))]))])

	#Part 2 - Just some unreadable bullshit
	arr = np.array([[int(y) for y in x.strip()] for x in read_input(3)]).transpose()
	curline = 0
	while len(arr[0]) > 1: 
		arr = [x[(np.bincount(arr[curline]).argmax() if np.bincount(arr[curline]).argmax() != np.bincount(arr[curline]).argmin() else 1) == arr[curline]] for x in arr] 
		curline += 1
	oxy = int("".join([str(x) for x in np.array(arr).flatten()]),2)
	
	arr = np.array([[int(y) for y in x.strip()] for x in read_input(3)]).transpose()
	curline = 0
	while len(arr[0]) > 1: 
		arr = [x[np.bincount(arr[curline]).argmin() == arr[curline]] for x in arr] 
		curline += 1
	scrubber = int("".join([str(x) for x in np.array(arr).flatten()]),2)

	print(oxy * scrubber)