# Add together the distances' absolute values (multiply them by -1 if negative)

tag @a remove ch_arrived

scoreboard players set @s ch_total_distance 0

scoreboard players operation @s ch_total_distance += @s ch_distanceX
scoreboard players operation @s ch_total_distance += @s ch_distanceY
scoreboard players operation @s ch_total_distance += @s ch_distanceZ

execute if score @s ch_total_distance matches 0 run tag @s add ch_arrived
