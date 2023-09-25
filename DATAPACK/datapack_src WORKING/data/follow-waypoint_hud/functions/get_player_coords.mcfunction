# Get the player's coordinates

scoreboard objectives add selfX dummy
scoreboard objectives add selfY dummy
scoreboard objectives add selfZ dummy

execute store result score @s selfX run data get entity @s Pos[0] 1.0
execute store result score @s selfY run data get entity @s Pos[1] 1.0
execute store result score @s selfZ run data get entity @s Pos[2] 1.0

