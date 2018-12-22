# Maze-Solving
Maze solving algorithms in Java

This is just a fun project I've been working on, here are a few notes about what I've done.

I've implemented 2 algorithms so far, Dijkstra's Shortest Path, and A* Search Algorithm.

The difference between the two exists entirely in the getCost() function in the NodeManager Class. Entering a 1 for A* during user input gives the Manhattan Distance a heuristic value of x1, meaning that the function will consider the Manhattan distance as well as the walking distance.
Entering a 0 for Dijkstra's gives the Manhattan Distance a heuristic value of x0, meaning the function will only consider walking distance when finding the shortest path.

The advanced graphics are just a fun little animation that I made, turning them off will yield significantly faster solve times.

The animation is probably so slow due to the fact that I must convert the bitmap into a graphics object every time I update a single cell, which is tedious to say the least. Not to mention that I also must resize the mazes to be 500x500 bitmaps.

I'm going to include a few mazes in here that I've generated using Daedalus 3.3

To use a different maze, enter the name of it in the driver.

I don't fully understand the Fibonacci heap, so there is some code in there if you want to mess with it but for now I'm leaving it unimplemented.
