package com.awman.waypointmod.command;

import com.awman.waypointmod.util.StateSaverAnLoader;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class CreateWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("create")
                    .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                        .executes(context -> run(context, StringArgumentType.getString(context, "waypoint_id"))))));
    }

    public static int run(CommandContext<ServerCommandSource> context, String waypointId) {
        //final String waypointId = StringArgumentType.getString(context, "waypoint");
        final BlockPos playerPosition = context.getSource().getPlayer().getBlockPos();
        context.getSource().sendMessage(Text.of("Created waypoint [" + waypointId + "] at " + playerPosition.toString()));

        StateSaverAnLoader stateSaverAnLoader = StateSaverAnLoader.getServerState(context.getSource().getServer());

        return 1;
    }
}
