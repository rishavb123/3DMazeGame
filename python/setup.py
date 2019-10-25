import sys
from cx_Freeze import setup, Executable

setup(
    name = "agent",
    version = "0.1",
    description = "A Reinforcement Learning Agent to Solve the Maze.",
    executables = [Executable("maze_ai.py", base = "Win32GUI")])