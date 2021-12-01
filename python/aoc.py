import itertools

buffer = {}

def read_input(nr):
    if nr in buffer:
        return buffer[nr]
    else:
        lines = []
        with open(f"input/{nr}.inp") as fp:
            lines = fp.readlines()
        buffer[nr] = lines
        return lines

def read_input_int(nr):
    return [int(x) for x in read_input(nr)]

def cartesian_set_reflective2(set):
    return list(itertools.product(set, set))

def cartesian_set_reflective3(set):
    return list(itertools.product(set, set, set))


if __name__ == "__main__":
    import argparse
    import os

    parser = argparse.ArgumentParser(description="Generates an generic File with the entered number.")
    parser.add_argument("day", type=int, help="Argument for the generator.")
    args = parser.parse_args()
    nr = args.day
    if os.path.exists(f"{nr}.py"):
        print("File already exists!")
        exit()
    with open(f"{nr}.py", "w") as fs:
        fs.write("from aoc import *\n")
        fs.write("\n")
        fs.write("if __name__ == '__main__':\n")
        fs.write("\tprint('TESTFILE " + str(nr) + "')")