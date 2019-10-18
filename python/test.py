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

filename = '../mazes/six.txt'
grid = Grid.load(filename)

policy = init_random_policy(grid)

print("board:")
grid.draw_board()

print("rewards:")
print_values(grid.rewards, grid)

print("initial_policy:")
print_policy(policy, grid)


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

print("value_function:")
print_values(V, grid)

print("final_policy:")
print_policy(policy, grid)

if input('Would you like to see the agent play the maze? (Y / n): ').lower()[0] == 'y':
    grid.play(policy)