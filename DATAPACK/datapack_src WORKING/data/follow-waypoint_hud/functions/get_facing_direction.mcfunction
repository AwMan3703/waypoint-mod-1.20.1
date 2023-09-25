
# Remove all tags
tag @a remove ch_facing_north
tag @a remove ch_facing_east
tag @a remove ch_facing_south
tag @a remove ch_facing_west
tag @a remove ch_facing_northeast
tag @a remove ch_facing_northwest
tag @a remove ch_facing_southeast
tag @a remove ch_facing_southwest

# 0 is facing south, -180 is facing north
execute store result score @s ch_facing run data get entity @s Rotation[0] 1.0

# Find which direction the player is facing - yes, the values are hardcoded, but making them dynamic was a bit overkill
# N
tag @a[y_rotation=160..180] add ch_facing_north
tag @a[y_rotation=-180..-160] add ch_facing_north
# S
tag @a[y_rotation=-20..20] add ch_facing_south
# E
tag @a[y_rotation=-110..-70] add ch_facing_east
# W
tag @a[y_rotation=70..110] add ch_facing_west
# NE
tag @a[y_rotation=-160..-110] add ch_facing_northeast
# NW
tag @a[y_rotation=110..160] add ch_facing_northwest
# SE
tag @a[y_rotation=-70..-20] add ch_facing_southeast
# SW
tag @a[y_rotation=20..70] add ch_facing_southwest
