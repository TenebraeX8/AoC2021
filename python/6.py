from aoc import *
from functools import reduce
import numpy as np

if __name__ == '__main__':
	print(sum(reduce(lambda value, step: [value[(idx + 1) % len(value)] if idx != 6 else value[7] + value[0] for idx,x in enumerate(list(value[1:]) + list([value[0]]))], list(range(80)), np.bincount([int(x) for x in read_input(6)[0].split(",")], minlength=9))), sum(reduce(lambda value, step: [value[(idx + 1) % len(value)] if idx != 6 else value[7] + value[0] for idx,x in enumerate(list(value[1:]) + list([value[0]]))], list(range(256)), np.bincount([int(x) for x in read_input(6)[0].split(",")], minlength=9))))
	