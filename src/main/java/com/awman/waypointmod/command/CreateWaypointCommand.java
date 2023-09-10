package com.awman.waypointmod.command;

import com.awman.waypointmod.util.StateSaverAnLoader;
import com.awman.waypointmod.util.WaypointData;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class CreateWaypointCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("waypoint")
                .then(CommandManager.literal("create")
                    .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                            .then(CommandManager.argument("position", BlockPosArgumentType.blockPos())
                                    .then(CommandManager.argument("dimension", IdentifierArgumentType.identifier())
                        .executes(context -> run(context,
                                StringArgumentType.getString(context, "waypoint_id"),
                                BlockPosArgumentType.getBlockPos(context, "position"),
                                DimensionArgumentType.getDimensionArgument(context, "dimension").getDimension())))))));
    }

    public static int run(CommandContext<ServerCommandSource> context, String waypointId, BlockPos position, DimensionType dimension){// DimensionType dimension) {
        final String author = context.getSource().getName();
        context.getSource().sendMessage(Text.of("Creating waypoint [" + waypointId + "] at " + position.toString() + " in dimension \"" + dimension.toString() + "\"..."));

  /*      StateSaverAnLoader serverState = StateSaverAnLoader.getServerState(context.getSource().getServer());
        serverState.waypointMap.put(waypointId, new WaypointData(author, position, dimension));
*/
        return 1;
    }
}
