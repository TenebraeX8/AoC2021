from numpy.core.defchararray import count
from aoc import *
import numpy as np

if __name__ == "__main__":
    print(np.array([elem1 < elem2  for elem1, elem2 in zip(read_input_int(1), read_input_int(1)[1:])]).sum())
    print(np.array([elem1 < elem2  for elem1, elem2 in zip([sum(read_input_int(1)[x:x+3]) for x in range(len(np.array(read_input_int(1)))-2)], [sum(read_input_int(1)[x:x+3]) for x in range(len(np.array(read_input_int(1)))-2)][1:])]).sum())