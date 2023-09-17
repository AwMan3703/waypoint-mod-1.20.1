package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.awman.waypointmod.util.data.WaypointData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class CreateWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("create")
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                .executes(context -> run(context,
                                        StringArgumentType.getString(context, "waypoint_id"),
                                        context.getSource().getPlayer().getBlockPos(),
                                        context.getSource().getWorld().getDimensionKey().getValue().toString()))
                                .then(CommandManager.argument("position", BlockPosArgumentType.blockPos())
                                        .executes(context -> run(context,
                                                StringArgumentType.getString(context, "waypoint_id"),
                                                BlockPosArgumentType.getBlockPos(context, "position"),
                                                context.getSource().getWorld().getDimensionKey().getValue().toString()))
                                        .then(CommandManager.argument("dimension", DimensionArgumentType.dimension())
                                                .executes(context -> run(context,
                                                    StringArgumentType.getString(context, "waypoint_id"),
                                                    BlockPosArgumentType.getBlockPos(context, "position"),
                                                    DimensionArgumentType.getDimensionArgument(context, "dimension").getRegistryKey().getValue().toString())))))));
    }

    public static int run(CommandContext<ServerCommandSource> context, String waypointId, BlockPos position, String dimensionIdentifier) throws CommandSyntaxException {

        context.getSource().sendMessage(Text.of("Creating waypoint [" + waypointId + "] at " + position.toShortString() + " in dimension \"" + dimensionIdentifier + "\"..."));

        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getWorld().getServer());
        serverState.waypointMap.insert(waypointId,
                new WaypointData(
                        context.getSource().getPlayer().getName().getString(),
                        position,
                        dimensionIdentifier,
                        true) // Always public, since the feature is not yet implemented
        );
        serverState.markDirty();

        context.getSource().sendMessage(Text.of("Waypoint created!"));

        return 1;
    }
}
