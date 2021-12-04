from os import system
from aoc import *
import numpy as np

#No chance I ll ever do this in one line! Instead you get some pretty unreadable bullshit which surprisingly still will compute the right answer!

def part1(values, inp):
	inp = [(grid, np.zeros(len(inp[0])), np.zeros(len(inp[0][0]))) for grid in inp]
	for val_idx, value in enumerate(values):
		for grid, linecount, colcount in inp:
			for idx, line in enumerate(grid):
				colidx = list(line).index(value) if value in line else -1
				if colidx != -1:
					linecount[idx] += 1
					colcount[colidx] += 1
					if linecount[idx] == len(inp[0][0]) or colcount[colidx] == len(inp[0][0][0]):
						print(sum([x for x in grid.flatten() if x not in values[0:val_idx + 1]]) * value)
						return

def part2(values, inp):
	inp = [(grid, np.zeros(len(inp[0])), np.zeros(len(inp[0][0]))) for grid in inp]
	finished = 0
	finished_grids = set()
	for val_idx, value in enumerate(values):
		for grid, linecount, colcount in inp:
			if str(grid) in finished_grids:
				continue
			for idx, line in enumerate(grid):
				colidx = list(line).index(value) if value in line else -1
				if colidx != -1:
					linecount[idx] += 1
					colcount[colidx] += 1
					if linecount[idx] == len(inp[0][0]) or colcount[colidx] == len(inp[0][0][0]):
						finished += 1
						finished_grids.add(str(grid))
						if finished == len(inp):
							print(sum([x for x in grid.flatten() if x not in values[0:val_idx + 1]]) * value)
							return
						break

if __name__ == '__main__':
	import time
	start = time.time()
	values, inp = (np.array([int(x.strip()) for x in read_input(4)[0].split(",")]), np.array([[[int(z) for z in list(filter(lambda l: l != "", y.strip().split(" ")))] for y in x.strip().split("\n")] for x in "".join(read_input(4)[1:]).split('\n\n')]))
	part1(values, inp)
	part2(values, inp)
	print(time.time() - start)