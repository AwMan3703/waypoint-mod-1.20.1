# Controls everything to do with coordinates hud

schedule function follow-waypoint_hud:tick 1t

scoreboard players add @a ch_toggleCons 0
scoreboard players set @a[scores={ch_toggleCons=0}] ch_toggleCons -1

scoreboard players enable @a ch_toggle
execute as @a[scores={ch_toggle=1..}] at @s run function follow-waypoint_hud:toggle_trigger

function follow-waypoint_hud:get_player_coords
execute as @a[scores={ch_toggleCons=1..}] at @s run function follow-waypoint_hud:display_hud