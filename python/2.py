from numpy.core.defchararray import multiply
from aoc import *
import operator
import numpy as np

if __name__ == '__main__':
	print(np.prod(sum([np.array([(int(steps) if direction == "forward" else 0, int(steps) if direction == "down" else (-int(steps) if direction == "up" else 0))]) for direction, steps in [tuple(line.split(' ')) for line in read_input(2)]])), np.prod(sum([np.array([(int(steps) if direction == "forward" else 0, int(aim) * int(steps) if direction == "forward" else 0)]) for direction, steps, aim in [tuple(line.split(' ')) + tuple([aim]) for line, aim in zip(read_input(2), np.cumsum([int(steps) if direction == "down" else (-int(steps) if direction == "up" else 0) for direction, steps in [tuple(line.split(' ')) for line in read_input(2)]]))]])))