import numpy as np
import requests
import time

from grid import *
from actions import *
from constants import *
from util import *
from rl import *

def get(url):
    r = requests.get(url = url)
    return r.text

filename = get("http://localhost:8000/file")
grid = Grid.load(filename)

policy = init_random_policy(grid)

# print("board:")
# grid.draw_board()

# print("rewards:")
# print_values(grid.rewards, grid)

# print("initial_policy:")
# print_policy(policy, grid)


## Policy iteration
# 
# policy = init_random_policy(grid)
# V = init_random_value_function(grid)
# 
# while True:
#     iterative_policy_evaluation(grid, policy, V)
#     is_policy_converged = policy_iteration(grid, V, policy)[1]
#     if is_policy_converged:
#         break

# Value iteration
V = value_iteration(grid)
policy = policy_iteration(grid, V)[0]

# print("value_function:")
# print_values(V, grid)

# print("final_policy:")
# print_policy(policy, grid)

# if input('Would you like to see the agent play the maze? (Y / n): ').lower()[0] == 'y':
#     grid.play(policy)

def send(direction):
    return get("http://localhost:8000/move?" + direction)

def direction_py_to_j(direction):
    if direction == LEFT:
        return 0
    elif direction == UP:
        return 1
    elif direction == RIGHT:
        return 2
    elif direction == DOWN:
        return 3

def direction_j_to_py(direction):
    if direction == 0:
        return LEFT
    elif direction == 1:
        return UP
    elif direction == 2:
        return RIGHT
    elif direction == 3:
        return DOWN

def turn_left(direction):
    j_direction = direction_py_to_j(direction)
    j_left = (j_direction - 1) % 4
    return direction_j_to_py(j_left)

def turn_right(direction):
    j_direction = direction_py_to_j(direction)
    j_right = (j_direction + 1) % 4
    return direction_j_to_py(j_right)

delay = 0.5

temp = send("").split(" ")
state = (int(temp[1]), int(temp[0]))
direction =  direction_j_to_py(int(temp[2]))
done = int(temp[3])

while done == 0:

    param = ""

    action = policy[state]

    if direction == action:
        param = "front"
    elif direction == -action or turn_left(direction) == action:
        param = "left"
    elif turn_right(direction) == action:
        param = "right"

    temp = send(param).split(" ")
    state = (int(temp[1]), int(temp[0]))
    direction =  direction_j_to_py(int(temp[2]))
    done = int(temp[3])

    time.sleep(delay)
