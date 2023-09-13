package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.awman.waypointmod.util.storage.WaypointMap;
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
                                .executes(context -> run(context,
                                        StringArgumentType.getString(context, "waypoint_id"))))));
    }

    public static int run(CommandContext<ServerCommandSource> context, String waypointId) throws CommandSyntaxException {

        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getWorld().getServer());

        if (!serverState.waypointMap.containsKey(waypointId)) {

            context.getSource().sendMessage(Text.of("Waypoint not found!"));

            return -1;
        } else {

            WaypointMap waypointMap = serverState.waypointMap;
            waypointMap.remove(waypointId);

            context.getSource().sendMessage(Text.of("Waypoint deleted!"));

            return 1;
        }
    }
}
