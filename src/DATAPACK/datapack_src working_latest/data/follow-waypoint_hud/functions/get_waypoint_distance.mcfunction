# Gets the coords of a player

function follow-waypoint_hud:get_waypoint_coords
function follow-waypoint_hud:get_player_coords

scoreboard players set @s ch_distanceX 0
scoreboard players set @s ch_distanceY 0 
scoreboard players set @s ch_distanceZ 0

execute store result score @s ch_distanceX run scoreboard players get @s fh_waypointX
execute store result score @s ch_distanceY run scoreboard players get @s fh_waypointY
execute store result score @s ch_distanceZ run scoreboard players get @s fh_waypointZ

scoreboard players operation @s ch_distanceX -= @s selfX
execute if score @s ch_distanceX matches ..-1 run scoreboard players operation @s ch_distanceX *= #neg1 ch_constant
scoreboard players operation @s ch_distanceY -= @s selfY
execute if score @s ch_distanceY matches ..-1 run scoreboard players operation @s ch_distanceY *= #neg1 ch_constant
scoreboard players operation @s ch_distanceZ -= @s selfZ
execute if score @s ch_distanceZ matches ..-1 run scoreboard players operation @s ch_distanceZ *= #neg1 ch_constant
