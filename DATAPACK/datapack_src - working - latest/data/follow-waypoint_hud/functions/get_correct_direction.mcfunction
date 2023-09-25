# Get wether the player is facing the correct way

function follow-waypoint_hud:get_facing_direction

tag @a remove ch_facing_correct

tag @a[tag=ch_north, tag=ch_facing_north] add ch_facing_correct
tag @a[tag=ch_south, tag=ch_facing_south] add ch_facing_correct
tag @a[tag=ch_east, tag=ch_facing_east] add ch_facing_correct
tag @a[tag=ch_west, tag=ch_facing_west] add ch_facing_correct
tag @a[tag=ch_northeast, tag=ch_facing_northeast] add ch_facing_correct
tag @a[tag=ch_northwest, tag=ch_facing_northwest] add ch_facing_correct
tag @a[tag=ch_southeast, tag=ch_facing_southeast] add ch_facing_correct
tag @a[tag=ch_southwest, tag=ch_facing_southwest] add ch_facing_correct
