# Get player direction

#tag @a remove ch_south
#tag @a remove ch_east
#tag @a remove ch_west
#tag @a remove ch_north

#tag @a[y_rotation=-45..45] add ch_south
#tag @a[y_rotation=45..135] add ch_west
#tag @a[y_rotation=-135..-45] add ch_east
#tag @a[y_rotation=-179.99..-135] add ch_north
#tag @a[y_rotation=135..179.99] add ch_north

tag @a remove ch_north
tag @a remove ch_northeast
tag @a remove ch_northwest
tag @a remove ch_south
tag @a remove ch_southeast
tag @a remove ch_southwest
tag @a remove ch_east
tag @a remove ch_west

scoreboard objectives add selfX dummy
scoreboard objectives add selfZ dummy
scoreboard objectives add waypointX dummy
scoreboard objectives add waypointZ dummy
scoreboard objectives add distanceX dummy
scoreboard objectives add distanceZ dummy

# Calculate the difference in X coordinates
execute store result score @s selfX run data get entity @s Pos[0] 1.0
scoreboard players set @s waypointX 21
scoreboard players set @s distanceX 21

# Calculate the difference in Z coordinates
execute store result score @s selfZ run data get entity @s Pos[2] 1.0
scoreboard players set @s waypointZ -25
scoreboard players set @s distanceZ -25

scoreboard players operation @s distanceX -= @s selfX
scoreboard players operation @s distanceZ -= @s selfZ

# Calculate the direction, relative to the player:
# N
execute if score @s distanceZ < #0 ch_constant if score @s distanceX = #0 ch_constant run tag @s add ch_north
# S
execute if score @s distanceZ > #0 ch_constant if score @s distanceX = #0 ch_constant run tag @s add ch_south
# E
execute if score @s distanceX < #0 ch_constant if score @s distanceZ = #0 ch_constant run tag @s add ch_west
# W
execute if score @s distanceX > #0 ch_constant if score @s distanceZ = #0 ch_constant run tag @s add ch_east
# NE
execute if score @s distanceX > #0 ch_constant if score @s distanceZ < #0 ch_constant run tag @s add ch_northeast
# NW--
execute if score @s distanceZ < #0 ch_constant if score @s distanceX < #0 ch_constant run tag @s add ch_northwest
# SE
execute if score @s distanceX > #0 ch_constant if score @s distanceZ > #0 ch_constant run tag @s add ch_southeast
# SW--
execute if score @s distanceZ < #0 ch_constant if score @s distanceX > #0 ch_constant run tag @s add ch_southwest
