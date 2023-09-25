# Get player direction

tag @a remove ch_north
tag @a remove ch_northeast
tag @a remove ch_northwest
tag @a remove ch_south
tag @a remove ch_southeast
tag @a remove ch_southwest
tag @a remove ch_east
tag @a remove ch_west
tag @a remove ch_nsew

# Calculate the direction, relative to the player:
# N
execute if score @s selfZ > @s waypointZ if score @s selfX = @s waypointX run tag @s add ch_north
# S
execute if score @s selfZ < @s waypointZ if score @s selfX = @s waypointX run tag @s add ch_south
# E
execute if score @s selfX < @s waypointX if score @s selfZ = @s waypointZ run tag @s add ch_east
# W
execute if score @s selfX > @s waypointX if score @s selfZ = @s waypointZ run tag @s add ch_west
# NE
execute if score @s selfZ > @s waypointZ if score @s selfX < @s waypointX run tag @s add ch_northeast
# NW
execute if score @s selfZ > @s waypointZ if score @s selfX > @s waypointX run tag @s add ch_northwest
# SE
execute if score @s selfZ < @s waypointZ if score @s selfX < @s waypointX run tag @s add ch_southeast
# SW
execute if score @s selfZ < @s waypointZ if score @s selfX > @s waypointX run tag @s add ch_southwest
# Arrived
execute if score @s selfX = @s waypointX if score @s selfZ = @s waypointZ run tag @s add ch_nsew
