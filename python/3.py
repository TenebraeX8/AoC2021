from aoc import *
import numpy as np

if __name__ == '__main__':
	#Part 1
	print(int("".join([str(np.bincount(x).argmax()) for x in np.array([[int(y) for y in x.strip()] for x in read_input(3)]).transpose()]), 2) * int("".join([str(np.bincount(x).argmin()) for x in np.array([[int(y) for y in x.strip()] for x in read_input(3)]).transpose()]), 2))

	#Part 2 - need to find a way to do unbound iterations	
	#print([list(filter(lambda t: t[0][t[1]] == str(np.bincount(np.array([[int(y) for y in x.strip()] for x in read_input(3)]).transpose()[t[0]])), [(i, elem) for i, elem in enumerate(read_input(3))]))])