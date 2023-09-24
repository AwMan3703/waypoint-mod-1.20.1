package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.command.suggestion.WaypointNameSuggestionProvider;
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
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;

public class FollowWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("follow")
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                .suggests((context, builder) -> new WaypointNameSuggestionProvider().getSuggestions(context, builder))
                                .executes(context -> runFollow(context,
                                        StringArgumentType.getString(context, "waypoint_id")))))
                .then(CommandManager.literal("unfollow")
                        .executes(context -> runUnfollow(context))));
    }

    public static int runFollow(CommandContext<ServerCommandSource> context, String waypointId) throws CommandSyntaxException {
        try {
            GameRules rules = context.getSource().getWorld().getGameRules();
            GameRules.BooleanRule feedbackRule = rules.get(GameRules.SEND_COMMAND_FEEDBACK);

            MinecraftServer server = context.getSource().getServer();
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(server);

            ServerPlayerEntity player = context.getSource().getPlayer();
            String playerName = player.getName().getString();
            PlayerData playerData = serverState.playerMap.computeIfAbsent(player.getUuid().toString(), uuid -> new PlayerData());

            WaypointData waypointData = serverState.waypointMap.get(waypointId);

            CommandManager commandManager = context.getSource().getServer().getCommandManager();
            CommandDispatcher<ServerCommandSource> dispatcher = commandManager.getDispatcher();

            // Add the waypoint's position to the player's NBT, to read it from the datapack
            String command_disableOutput = "gamerule sendCommandFeedback false";
            String command_enableOutput = "gamerule sendCommandFeedback true";
            String command_objSet_X = "scoreboard players set " + playerName + " fh_waypointX " + waypointData.coordinates.getX();
            String command_objSet_Y = "scoreboard players set " + playerName + " fh_waypointY " + waypointData.coordinates.getY();
            String command_objSet_Z = "scoreboard players set " + playerName + " fh_waypointZ " + waypointData.coordinates.getZ();
            String command_fire = "trigger ch_toggle";

            feedbackRule.set(false, server);
            commandManager.execute(dispatcher.parse(command_objSet_X, context.getSource()), command_objSet_X);
            commandManager.execute(dispatcher.parse(command_objSet_Y, context.getSource()), command_objSet_Y);
            commandManager.execute(dispatcher.parse(command_objSet_Z, context.getSource()), command_objSet_Z);
            commandManager.execute(dispatcher.parse(command_fire, context.getSource()), command_fire);
            feedbackRule.set(true, server);

            playerData.followingWaypointId = waypointId;

            context.getSource().sendMessage(Text.of("Following waypoint \"" + waypointId + "\"!"));
            return 1;
        } catch (Exception e) {
            context.getSource().sendMessage(Text.of("" + e));
        }
        return -1;
    }

    public static int runUnfollow(CommandContext<ServerCommandSource> context) {
        GameRules rules = context.getSource().getWorld().getGameRules();
        GameRules.BooleanRule feedbackRule = rules.get(GameRules.SEND_COMMAND_FEEDBACK);

        MinecraftServer server = context.getSource().getServer();
        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(server);

        ServerPlayerEntity player = context.getSource().getPlayer();
        PlayerData playerData = serverState.playerMap.computeIfAbsent(player.getUuid().toString(), uuid -> new PlayerData());

        CommandManager commandManager = context.getSource().getServer().getCommandManager();
        CommandDispatcher<ServerCommandSource> dispatcher = commandManager.getDispatcher();

        String command_disableOutput = "gamerule sendCommandFeedback false";
        String command_enableOutput = "gamerule sendCommandFeedback true";
        String command_fire = "trigger ch_toggle";

        feedbackRule.set(false, server);
        commandManager.execute(dispatcher.parse(command_fire, context.getSource()), command_fire);
        feedbackRule.set(true, server);

        context.getSource().sendMessage(Text.of("Unfollowing \"" + playerData.followingWaypointId + "\""));
        return 1;
    }
}
