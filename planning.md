
**Requested features**

**Commands:**

*/waypoint create <waypoint> <position> <dimension>* - This will take optional coordinates & dimension. If they aren't passed, then the players current dimension & position will be used. Ideally, this should work with modded dimensions as well. waypoint is a required parameter and will be the waypoint name.
*/waypoint list <username>* - This should use pagination and list all waypoints created on the server. It will take an optional parameter - username. When passed, all the waypoints by that player will be shown.
*/waypoint info <waypoint>* - waypoint is a required parameter. This will output the coordinates and dimension of the waypoint. Also how far away the player is if in the same dimension.
*/waypoint follow <waypoint>* - This should use the title bar (above the hotbar) to tell the player where to go. It should say the direction (with a cross or tick) and the distance. waypoint is a required parameter for the... waypoint name.
*/waypoint delete <waypoint>* - This can only be run be an operator or the owner of the waypoint. waypoint is a required parameter.
