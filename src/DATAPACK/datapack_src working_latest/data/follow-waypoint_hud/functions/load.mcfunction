# Creates all scores required by the datapack

scoreboard objectives add ch_toggle trigger "toggle waypoint-follower_hud"
scoreboard objectives add ch_toggleCons dummy
scoreboard players set #ch_toggleCons ch_toggleCons -1

scoreboard objectives add ch_distanceX dummy
scoreboard objectives add ch_distanceY dummy
scoreboard objectives add ch_distanceZ dummy
scoreboard objectives add ch_total_distance dummy

scoreboard objectives add ch_facing dummy

scoreboard objectives add selfX dummy
scoreboard objectives add selfY dummy
scoreboard objectives add selfZ dummy

scoreboard objectives add fh_waypointX dummy
scoreboard objectives add fh_waypointY dummy
scoreboard objectives add fh_waypointZ dummy

scoreboard objectives add ch_constant dummy
scoreboard players set #neg1 ch_constant -1
scoreboard players set #0 ch_constant 0
scoreboard players set #1 ch_constant 1
scoreboard players set #6 ch_constant 6
scoreboard players set #60 ch_constant 60
scoreboard players set #5 ch_constant 5
scoreboard players set #18 ch_constant 18
scoreboard players set #24 ch_constant 24
scoreboard players set #northeast ch_constant -160
scoreboard players set #northwest ch_constant 160
scoreboard players set #southeast ch_constant -20
scoreboard players set #southwest ch_constant 20
scoreboard players set #eastsouth ch_constant 70
scoreboard players set #eastnorth ch_constant 110
scoreboard players set #westsouth ch_constant -70
scoreboard players set #westnorth ch_constant -110
