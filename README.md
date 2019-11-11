# 3D Maze Game

## Installation

```batch
git clone https://github.com/rishavb123/3DMazeGame.git
```

## Setup

The AI requires python 3, which can be downloaded at [https://www.python.org/downloads/](https://www.python.org/downloads/). Make sure to check the box to add python (and PIP) to your `PATH`.

Then run

```batch
pip install requests
pip install numpy
```

to install the dependencies.

## Scripts

### Run Script

```batch
run
```

This runs a batch script which will run the java application (Application.java) and will forward port [localhost:8000](https://localhost:8000) to port [maze.serveo.net:80](https://maze.serveo.net:80) for general use of the http server. If [maze.serveo.net:80](https://maze.serveo.net:80) does not work, it is likely that serveo is temporarily down and will be fixed in a couple of days. As I am writing this it is currently disabled due to phishing. The controller can still be accessed at [|your-ip|:8000/controller](https://|your-ip|:8000/controller).

Two windows will open; one is to forward the server, while the other is the game. These two windows and the original window need to be open for all of the programs functionality to work.

#### Parameters

The first parameter will specify which maze to use.

```batch
run one
```

will run the program using the maze stored in [mazes/one.txt](mazes/one.txt). If this parameter is left blank, the program will default to [mazes/seven.txt](mazes/seven.txt).

A second parameter is whether or not to forward the server to [serveo.net](https://serveo.net).

```batch
run one X
```

will run the program on [mazes/one.txt](mazes/one.txt) and will not forward the server to serveo.net. So only one window will open and you will not be able to control the player from [maze.serveo.net/controller](https://maze.serveo.net/controller). The server is still accessible on localhost and the computers ip address.

### Clean Script

This will just delete all the class files.

## Controls

### Keys

1. Pressing and holding the `UP` key will move you forwards.
2. The `LEFT` and `RIGHT` keys will turn you left and right respectively.
3. The `DOWN` key will move you backwards.
4. The `R` key will reset you to your original location.
5. The `SPACE` key will toggle between the two dimensional view and the three dimensional view.
6. The `C` key will toggle the compass in the two dimension view.
7. The `P` key will launch a python script that uses a reinforcement learning algorithm to control the explorer using the server. It will find the optimal path (maximizing your health) to the end adapting to portals and traps.

### Maze Controller

If you are forwarding the server to [serveo.net](https://serveo.net) then you will be able to control the explorer from the url [maze.serveo.net/controller](https://maze.serveo.net/controller). The `UP`, `DOWN`, `LEFT`, and `RIGHT` buttons do that same thing as the keys.

If you are not forwarding the server, this site can still be accessed at [locahost:8000/controller](https://locahost:8000/controller) locally or [|your-ip|:8000/controller](https://|your-ip|:8000/controller) on the network.

## Game Mechanics

You will start with an initial health equal to the area of the maze. Every step makes you lose 1 health. If you hit a trap ([Trap.java](Trap.java)) you will lose 10% of your maximum health. When the explorer reach 0 or less health, the explorer will "die," and you will have to restart.

### Three Dimensional View

1. A red ceiling indicates that there is a trap in that location.
2. A purple floor indicates that there is a portal in that location.
3. A green wall means that you are at the end. Just run into the green wall to win.

### Two Dimensional View

1. Red squares indicate traps.
2. Blue and green squares indicate portals. To find the corresponding portal, look for a portal with the same shade.
3. The white square is the goal state or the winning location.

## Maze Design

1. `S` is the starting location
2. `+` is the end location
3. `#` are walls
4. `_` is a space
5. `~` is a trap
6. Any numbers are portals. 1 teleports to 1, 2 teleports to 2, . . .
