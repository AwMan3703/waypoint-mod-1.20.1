# Get player direction

tag @a remove ch_north
tag @a remove ch_northeast
tag @a remove ch_northwest
tag @a remove ch_south
tag @a remove ch_southeast
tag @a remove ch_southwest
tag @a remove ch_east
tag @a remove ch_west
tag @a remove ch_arrived

scoreboard objectives add waypointX dummy
scoreboard objectives add waypointZ dummy
scoreboard objectives add distanceX dummy
scoreboard objectives add distanceZ dummy

# Calculate the difference in X coordinates
scoreboard players set @s waypointX 21

# Calculate the difference in Z coordinates
scoreboard players set @s waypointZ -25


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

# the player has arrived, add the arrived tag, remove all the others
execute if score @s selfX = @s waypointX if score @s selfZ = @s waypointZ run tag @s add ch_arrived
execute if score @s selfX = @s waypointX if score @s selfZ = @s waypointZ run tag @s remove ch_north
execute if score @s selfX = @s waypointX if score @s selfZ = @s waypointZ run tag @s remove ch_northeast
execute if score @s selfX = @s waypointX if score @s selfZ = @s waypointZ run tag @s remove ch_northwest
execute if score @s selfX = @s waypointX if score @s selfZ = @s waypointZ run tag @s remove ch_south
execute if score @s selfX = @s waypointX if score @s selfZ = @s waypointZ run tag @s remove ch_southeast
execute if score @s selfX = @s waypointX if score @s selfZ = @s waypointZ run tag @s remove ch_southwest
execute if score @s selfX = @s waypointX if score @s selfZ = @s waypointZ run tag @s remove ch_east
execute if score @s selfX = @s waypointX if score @s selfZ = @s waypointZ run tag @s remove ch_west
