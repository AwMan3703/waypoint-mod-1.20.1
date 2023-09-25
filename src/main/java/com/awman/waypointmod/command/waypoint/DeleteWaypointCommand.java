package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.WaypointMod;
import com.awman.waypointmod.command.suggestion.WaypointNameSuggestionProvider;
import com.awman.waypointmod.util.data.WaypointData;
import com.awman.waypointmod.util.data.WaypointMap;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class DeleteWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("delete")
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                .suggests((context, builder) -> new WaypointNameSuggestionProvider().getSuggestions(context, builder))
                                .executes(context -> run(context,
                                        StringArgumentType.getString(context, "waypoint_id"))))));
    }

    public static int run(CommandContext<ServerCommandSource> context, String waypointId) throws CommandSyntaxException {

        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getWorld().getServer());
        WaypointMap waypointMap = serverState.waypointMap;
        WaypointData waypointData = waypointMap.get(waypointId);

        if (!waypointMap.containsKey(waypointId)) {
            // If the waypoint doesn't exist:
            context.getSource().sendMessage(Text.of("Waypoint not found!"));
            return -1;
        } else if (
                !context.getSource().getName().equals(waypointData.author) && // If the player isn't the author
                !context.getSource().hasPermissionLevel(WaypointMod.opPermissionLevel) // and the player isn't an op
        ) {
            // If the player doesn't have the necessary permissions:
            context.getSource().sendMessage(Text.of("Insufficient permissions!"));
            return -1;
        } else {
            // If no problem is detected:
            serverState.waypointMap.remove(waypointId);
            serverState.markDirty();
            context.getSource().sendMessage(Text.of("Waypoint deleted!"));
            return 1;
        }
    }
}
