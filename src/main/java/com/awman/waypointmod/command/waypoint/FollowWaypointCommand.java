package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.command.suggestion.WaypointNameSuggestionProvider;
import com.awman.waypointmod.util.data.WaypointData;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

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
            ServerPlayerEntity player = context.getSource().getPlayer();
            String playerName = player.getName().getString();
            CommandManager commandManager = context.getSource().getServer().getCommandManager();
            CommandDispatcher<ServerCommandSource> dispatcher = commandManager.getDispatcher();
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getServer());
            WaypointData waypointData = serverState.waypointMap.get(waypointId);

            // <DATAPACK METHOD>
            // Add the waypoint's position to the player's NBT, to read it from the datapack
            context.getSource().getServer().getScoreboard().addScoreboardObjective(new ScoreboardObjective(
                    context.getSource().getServer().getScoreboard(),
                    "fh_waypointX",
                    ScoreboardCriterion.DUMMY,
                    Text.of("FHWPX"),
                    ScoreboardCriterion.RenderType.INTEGER
            ));
            context.getSource().getPlayer().getScoreboard().updateObjective(new ScoreboardObjective(
                    context.getSource().getServer().getScoreboard(),
                    "fh_waypointX",
                    ScoreboardCriterion.DUMMY,
                    Text.of("FHWPX"),
                    ScoreboardCriterion.RenderType.INTEGER
            ));

            /* player.getScoreboard().setObjectiveSlot(waypointData.coordinates.getX(), new ScoreboardObjective(player.getScoreboard(), "fh_waypointX", ScoreboardCriterion.DUMMY, Text.of("FH - waypoint data"), ScoreboardCriterion.RenderType.INTEGER));
            player.getScoreboard().setObjectiveSlot(waypointData.coordinates.getY(), new ScoreboardObjective(player.getScoreboard(), "fh_waypointY", ScoreboardCriterion.DUMMY, Text.of("FH - waypoint data"), ScoreboardCriterion.RenderType.INTEGER));
            player.getScoreboard().setObjectiveSlot(waypointData.coordinates.getZ(), new ScoreboardObjective(player.getScoreboard(), "fh_waypointZ", ScoreboardCriterion.DUMMY, Text.of("FH - waypoint data"), ScoreboardCriterion.RenderType.INTEGER));
             */

            // <TITLE COMMAND METHOD>
            String disableOutput = "gamerule sendCommandFeedback false";

            String commandTimes =
                    "title " +
                    playerName +
                    " times " +
                    "1 " + // Fade in   \
                    "20 " + // Stay      } Time in game ticks. TRAILING SPACE IS IMPORTANT
                    "1"; // Fade out    /

            String command =
                    "execute as " +
                    playerName +
                    " run title @s actionbar {" +
                    "\"text\" : \"[Waypoint tracking info here]\"," +
                    "\"color\" : \"red\"" +
                    "}";

            // Shut up commands!!!
            commandManager.execute(dispatcher.parse(disableOutput, context.getSource()), disableOutput);
            // Set the title times
            commandManager.execute(dispatcher.parse(commandTimes, context.getSource()), commandTimes);
            // Run the actual title command
            commandManager.execute(dispatcher.parse(command, context.getSource()), command);

            context.getSource().sendMessage(Text.of("Following waypoint!!!! :)))"));
            return 1;
        } catch (Exception e) {
            context.getSource().sendMessage(Text.of("" + e));
        }
        return -1;
    }

    public static int runUnfollow(CommandContext<ServerCommandSource> context) {
        context.getSource().sendMessage(Text.of("Waypoint unfollowed :("));
        return 1;
    }
}
