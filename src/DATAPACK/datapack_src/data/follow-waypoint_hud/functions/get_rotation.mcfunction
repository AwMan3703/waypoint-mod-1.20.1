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

tag @a remove ch_south
tag @a remove ch_east
tag @a remove ch_west
tag @a remove ch_north

scoreboard objectives add selfX dummy
scoreboard objectives add waypointX dummy
scoreboard objectives add selfZ dummy
scoreboard objectives add waypointZ dummy

# Calculate the difference in X coordinates
execute store result score @s selfX run data get entity @s Pos[0]
scoreboard players set @s waypointX 21

# Calculate the difference in Z coordinates
execute store result score @s waypointZ run data get entity @s Pos[2]
scoreboard players set @s waypointZ -25

scoreboard players operation @s waypointX -= @s selfX
scoreboard players operation @s waypointZ -= @s selfZ

execute if score @s waypointX > #0 ch_constant run tag @s add ch_east
execute if score @s waypointZ < #0 ch_constant run tag @s add ch_west
execute if score @s waypointZ > #0 ch_constant run tag @s add ch_south
execute if score @s waypointZ < #0 ch_constant run tag @s add ch_north
