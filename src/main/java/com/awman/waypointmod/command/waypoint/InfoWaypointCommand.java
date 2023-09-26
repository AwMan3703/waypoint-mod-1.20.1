package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.command.suggestion.WaypointNameSuggestionProvider;
import com.awman.waypointmod.util.data.WaypointData;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class InfoWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("info")
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                .suggests((context, builder) -> new WaypointNameSuggestionProvider().getSuggestions(context, builder))
                                .executes(context -> run(context,
                                    StringArgumentType.getString(context, "waypoint_id"))))));
    }

    public static int run(CommandContext<ServerCommandSource> context, String waypointId) throws CommandSyntaxException {
            try {
                StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getWorld().getServer());

                if (!serverState.waypointMap.containsKey(waypointId)) {

                    context.getSource().sendMessage(Text.of("Waypoint not found!"));

                    return -1;
                } else {

                    WaypointData waypointData = serverState.waypointMap.get(waypointId);

                    context.getSource().sendMessage(Text.of("Waypoint data:"));
                    context.getSource().sendMessage(Text.of(
                            "-> \"" + waypointId + "\", created by " +
                                    "@" + waypointData.author + " (" +
                                    (waypointData.isPublic() ? "public" : "private") + "): [" +
                                    waypointData.coordinates.toShortString() + " in " +
                                    waypointData.dimension.toString() + "]"));

                    return 1;
                }
            } catch (Exception e) {
                context.getSource().sendMessage(Text.of("WPM ERROR: " + e));
                return -1;
            }
    }
}

