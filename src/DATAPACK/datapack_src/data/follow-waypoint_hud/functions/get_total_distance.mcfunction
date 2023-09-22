# Add together the distances

scoreboard players add @s ch_total_distance 0

scoreboard players operation @s ch_total_distance += @s ch_distanceX
scoreboard players operation @s ch_total_distance += @s ch_distanceY
scoreboard players operation @s ch_total_distance += @s ch_distanceZ

