from functools import reduce
from aoc import *

if __name__ == '__main__':
    x_part, y_part = read_input(17)[0].replace("target area: x=", "").replace("y=", "").split(", ")
    x_lower, x_upper = [int(x) for x in x_part.split("..")]
    y_lower, y_upper = [int(y) for y in y_part.split("..")]
    x_range = range(x_lower, x_upper + 1)
    y_range = range(y_lower, y_upper + 1)
    
    sequences = []
    for y_vel in range(y_lower, 500 + 1):
        for x_vel in range(0, x_upper + 1):
            steps = []
            x, y, cur_x_vel, cur_y_vel = 0, 0, x_vel, y_vel
            hit = False
            max_y = y
            while x < x_upper and y > y_lower:
                x += cur_x_vel
                y += cur_y_vel
                steps.append((x, y))
                cur_x_vel += -1 if cur_x_vel > 0 else (1 if cur_x_vel < 0 else 0)
                cur_y_vel -= 1
                if (x in x_range) and (y in y_range):
                    hit = True
                max_y = max(max_y, y)
            if hit:
                sequences.append((max_y, steps))
    print(f"Solution for part 1: {max([y for y,_ in sequences])}")
    print(f"Solution for part 2: {len(sequences)}")