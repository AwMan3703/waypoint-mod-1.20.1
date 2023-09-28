package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.command.suggestion.WaypointNameSuggestionProvider;
import com.awman.waypointmod.util.ChatUI;
import com.awman.waypointmod.util.data.PlayerData;
import com.awman.waypointmod.util.data.WaypointData;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;

public class FollowWaypointCommand {
    // Class for the /waypoint follow command

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // Register under the /waypoint command
        dispatcher.register(CommandManager.literal("waypoint")

                // Take a literal, choosing from the following 2:
                // If the literal "follow" is passed:
                .then(CommandManager.literal("follow")

                        // Take a mandatory string argument "waypoint_id" to specify which waypoint to follow
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                // Send custom suggestions, choosing from all the existing waypoints
                                .suggests((context, builder) -> new WaypointNameSuggestionProvider().getSuggestions(context, builder))
                                // Activate the Waypoint-Follower HUD
                                .executes(context -> runFollow(context,
                                        StringArgumentType.getString(context, "waypoint_id")))))

                // If the literal "unfollow" is passed:
                .then(CommandManager.literal("unfollow")
                        // Deactivate the Waypoint-Follower HUD
                        .executes(context -> runUnfollow(context))));
    }

    public static int runFollow(CommandContext<ServerCommandSource> context, String waypointId) throws CommandSyntaxException {
        // OOF this took so long
        try {
            // Get access to the world's gamerules
            GameRules rules = context.getSource().getWorld().getGameRules();

            // Get access to the sendCommandFeedback rule
            GameRules.BooleanRule feedbackRule = rules.get(GameRules.SEND_COMMAND_FEEDBACK);

            // Get the current server
            MinecraftServer server = context.getSource().getServer();

            // Get the serverState for managing the server's PlayerMap instance
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(server);

            // Get the player who sent the follow command
            ServerPlayerEntity player = context.getSource().getPlayer();

            // Get that player's name
            String playerName = player.getName().getString();

            // Get that player's data
            PlayerData playerData = serverState.playerMap.computeIfAbsent(player.getUuid().toString(), uuid -> new PlayerData());

            // Get the server's WaypointMap instance
            WaypointData waypointData = serverState.waypointMap.get(waypointId);

            // Get the server's command manager
            CommandManager commandManager = context.getSource().getServer().getCommandManager();

            // Get the command manager's dispatcher
            CommandDispatcher<ServerCommandSource> dispatcher = commandManager.getDispatcher();

            // dimension check, only allow following waypoint if the player is in the correct dimension
            if (!(player.getEntityWorld().getDimensionKey().getValue().toString().equals(waypointData.dimension.toString()))) {
                // If not, inform them via a chat message
                ChatUI.sendMsg(
                        context.getSource(),
                        ChatUI.colored("Wrong dimension! Go to " + waypointData.dimension.toString(), ChatUI.color_Negative)
                );
                //context.getSource().sendMessage(Text.of("Wrong dimension! Go to " + waypointData.dimension.toString() + " to follow this waypoint."));
                // Return -1 (command execution failed)
                return -1;
            }
            // Otherwise, if the player IS in the correct dimension, continue

            // We need to add the waypoint's position to the player's scoreboard, in order to read it from the datapack
            // and display the correct information in the HUD.
            // This can be done by first adding the data using the /scoreboard command, and then activating the HUD using
            // the /trigger command:

            // Create 3 commands to add the waypoint's X, Y and Z coordinates to this specific player's scoreboard
            String command_objSet_X = "scoreboard players set " + playerName + " fh_waypointX " + waypointData.coordinates.getX();
            String command_objSet_Y = "scoreboard players set " + playerName + " fh_waypointY " + waypointData.coordinates.getY();
            String command_objSet_Z = "scoreboard players set " + playerName + " fh_waypointZ " + waypointData.coordinates.getZ();
            // Then create a command to /trigger the HUD
            String command_fire = "trigger ch_toggle";

            // Disable the sendCommandFeedback rule
            feedbackRule.set(false, server);
            // Send the commands we created
            commandManager.execute(dispatcher.parse(command_objSet_X, context.getSource()), command_objSet_X);
            commandManager.execute(dispatcher.parse(command_objSet_Y, context.getSource()), command_objSet_Y);
            commandManager.execute(dispatcher.parse(command_objSet_Z, context.getSource()), command_objSet_Z);
            commandManager.execute(dispatcher.parse(command_fire, context.getSource()), command_fire);
            // Re-enable the sendCommandFeedback rule
            feedbackRule.set(true, server);

            // Update this player's followingWaypointId (useful for remembering which waypoint they were following)
            playerData.followingWaypointId = waypointId;

            // Send a message in chat, confirming the start of a following session
            ChatUI.sendMsg(
                    context.getSource(),
                    ChatUI.colored("Following \"" + waypointId + "\"!", ChatUI.color_Positive)
            );
            //context.getSource().sendMessage(Text.of("Following \"" + waypointId + "\"!"));

            // Return 1 (command executed successfully)
            return 1;
        } catch (Exception e) {
            // Print any exception to the chat
            ChatUI.sendError(context.getSource(), e.toString());
            // Return -1 (command execution failed)
            return -1;
        }
    }

    public static int runUnfollow(CommandContext<ServerCommandSource> context) {
        try {
            // Get access to the world's gamerules
            GameRules rules = context.getSource().getWorld().getGameRules();

            // Get access to the sendCommandFeedback rule
            GameRules.BooleanRule feedbackRule = rules.get(GameRules.SEND_COMMAND_FEEDBACK);

            // Get the current server
            MinecraftServer server = context.getSource().getServer();

            // Get the serverState for managing the server's PlayerMap instance
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(server);

            // Get the player who sent the follow command
            ServerPlayerEntity player = context.getSource().getPlayer();

            // Get that player's data
            PlayerData playerData = serverState.playerMap.computeIfAbsent(player.getUuid().toString(), uuid -> new PlayerData());

            // Get the server's command manager
            CommandManager commandManager = context.getSource().getServer().getCommandManager();

            // Get the command manager's dispatcher
            CommandDispatcher<ServerCommandSource> dispatcher = commandManager.getDispatcher();

            // If the player is NOT following a waypoint
            if ((playerData.followingWaypointId == null) || playerData.followingWaypointId.isEmpty()) {
                // Inform them via a chat message
                ChatUI.sendMsg(
                        context.getSource(),
                        ChatUI.colored("You're not following a waypoint!", ChatUI.color_Negative)
                );
                //context.getSource().sendMessage(Text.of("You're not following a waypoint!"));
                // Return -1 (command execution failed)
                return -1;
            }

            // Generate the command we'll need to toggle off the HUD
            String command_fire = "trigger ch_toggle";

            // Disable the sendCommandFeedback rule
            feedbackRule.set(false, server);
            // Send the toggle command
            commandManager.execute(dispatcher.parse(command_fire, context.getSource()), command_fire);
            // Re-enable the sendCommandFeedback rule
            feedbackRule.set(true, server);

            // Inform the player via a chat message
            ChatUI.sendMsg(
                    context.getSource(),
                    ChatUI.colored("Unfollowed \"" + playerData.followingWaypointId + "\"!", ChatUI.color_Positive)
            );
            //context.getSource().sendMessage(Text.of("Unfollowed \"" + playerData.followingWaypointId + "\"!"));

            // Clear the player's followingWaypointId
            playerData.followingWaypointId = null;

            // Return 1 (command executed successfully)
            return 1;
        } catch (Exception e) {
            // Print any exception to the chat
            ChatUI.sendMsg(context.getSource(), ChatUI.errorText(e.getMessage()));
            // Return -1 (command execution failed)
            return -1;
        }
    }
}
