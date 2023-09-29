
function follow-waypoint_hud:get_waypoint_distance
function follow-waypoint_hud:get_total_distance
function follow-waypoint_hud:get_waypoint_direction
function follow-waypoint_hud:get_correct_direction

execute if entity @s[tag=ch_north, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"green"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> N","color":"green"}]
execute if entity @s[tag=ch_north, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> N","color":"gold"}]

execute if entity @s[tag=ch_northeast, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"green"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> NE","color":"green"}]
execute if entity @s[tag=ch_northeast, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> NE","color":"gold"}]

execute if entity @s[tag=ch_northwest, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"green"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> NW","color":"green"}]
execute if entity @s[tag=ch_northwest, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> NW","color":"gold"}]


execute if entity @s[tag=ch_south, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"green"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> S","color":"green"}]
execute if entity @s[tag=ch_south, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> S","color":"gold"}]

execute if entity @s[tag=ch_southeast, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"green"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> SE","color":"green"}]
execute if entity @s[tag=ch_southeast, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> SE","color":"gold"}]

execute if entity @s[tag=ch_southwest, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"green"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> SW","color":"green"}]
execute if entity @s[tag=ch_southwest, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> SW","color":"gold"}]


execute if entity @s[tag=ch_east, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"green"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> E","color":"green"}]
execute if entity @s[tag=ch_east, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> E","color":"gold"}]

execute if entity @s[tag=ch_west, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"green"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> W","color":"green"}]
execute if entity @s[tag=ch_west, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" -> W","color":"gold"}]


execute if entity @s[tag=ch_nsew] run title @s actionbar ["",{"text":"Distance: ","color":"green"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":"m","color":"white"},{"text":" (vertical)","color":"green"}]


execute if entity @s[tag=ch_arrived] run title @s actionbar ["",{"text":"You have arrived!","color":"gold"}]
execute if entity @s[tag=ch_arrived] run waypoint unfollow
