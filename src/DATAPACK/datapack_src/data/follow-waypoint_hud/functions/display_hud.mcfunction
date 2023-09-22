
function follow-waypoint_hud:get_waypoint_distance
function follow-waypoint_hud:get_total_distance
function follow-waypoint_hud:get_waypoint_direction
function follow-waypoint_hud:get_correct_direction


execute if entity @s[tag=ch_north, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> N","color":"lime"}]
execute if entity @s[tag=ch_north, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> N","color":"gold"}]
execute if entity @s[tag=ch_northeast, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> NE","color":"lime"}]
execute if entity @s[tag=ch_northeast, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> NE","color":"gold"}]
execute if entity @s[tag=ch_northwest, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> NW","color":"lime"}]
execute if entity @s[tag=ch_northwest, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> NW","color":"gold"}]

execute if entity @s[tag=ch_south, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> S","color":"lime"}]
execute if entity @s[tag=ch_south, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> S","color":"gold"}]
execute if entity @s[tag=ch_southeast, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> SE","color":"lime"}]
execute if entity @s[tag=ch_southeast, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> SE","color":"gold"}]
execute if entity @s[tag=ch_southwest, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> SW","color":"lime"}]
execute if entity @s[tag=ch_southwest, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> SW","color":"gold"}]

execute if entity @s[tag=ch_east, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> E","color":"lime"}]
execute if entity @s[tag=ch_east, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> E","color":"gold"}]
execute if entity @s[tag=ch_west, tag=ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> W","color":"lime"}]
execute if entity @s[tag=ch_west, tag=!ch_facing_correct] run title @s actionbar ["",{"text":"Distance: ","color":"gold"},{"color":"white","score":{"name":"@s","objective":"ch_total_distance"}},{"text":" ","color":"white"},{"text":" -> W","color":"gold"}]

execute if entity @s[tag=ch_arrived] run title @s actionbar ["",{"text":"You have arrived!","color":"gold"}]
