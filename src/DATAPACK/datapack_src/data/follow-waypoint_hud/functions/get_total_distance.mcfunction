# Add together the distances

scoreboard players set @s ch_total_distance 0

scoreboard objectives add selfX dummy
scoreboard objectives add selfY dummy
scoreboard objectives add selfZ dummy

execute if score @s selfX < #0 ch_constant run scoreboard players operation @s ch_total_distance -= @s ch_distanceX
execute if score @s selfX > #0 ch_constant run scoreboard players operation @s ch_total_distance += @s ch_distanceX

execute if score @s selfY < #0 ch_constant run scoreboard players operation @s ch_total_distance += @s ch_distanceY
execute if score @s selfY > #0 ch_constant run scoreboard players operation @s ch_total_distance -= @s ch_distanceY

execute if score @s selfZ < #0 ch_constant run scoreboard players operation @s ch_total_distance -= @s ch_distanceZ
execute if score @s selfZ > #0 ch_constant run scoreboard players operation @s ch_total_distance += @s ch_distanceZ

