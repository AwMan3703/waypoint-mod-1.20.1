package com.awman.waypointmod.command.waypoint;

import com.awman.waypointmod.util.ChatUI;
import com.awman.waypointmod.util.data.WaypointData;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
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
    // Class for the /waypoint create command

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // Register under the /waypoint command
        dispatcher.register(CommandManager.literal("waypoint")
                // Specify the literal "create"
                .then(CommandManager.literal("create")

                        // Take a mandatory string argument "waypoint_id" for the name
                        .then(CommandManager.argument("waypoint_id", StringArgumentType.string())
                                // If no further arguments are provided, create a waypoint with:
                                .executes(context -> run(context,
                                        // waypoint_id as the name
                                        StringArgumentType.getString(context, "waypoint_id"),
                                        // The player's position as coordinates
                                        context.getSource().getPlayer().getBlockPos(),
                                        // The player's dimension as dimension
                                        context.getSource().getWorld().getDimensionKey().getValue().toString()))

                                // Take an optional BlockPos argument "position" for the coordinates
                                .then(CommandManager.argument("position", BlockPosArgumentType.blockPos())
                                        // If no further arguments are provided, create a waypoint with:
                                        .executes(context -> run(context,
                                                // waypoint_id as the name
                                                StringArgumentType.getString(context, "waypoint_id"),
                                                // position as the coordinates
                                                BlockPosArgumentType.getBlockPos(context, "position"),
                                                // The player's dimension as dimension
                                                context.getSource().getWorld().getDimensionKey().getValue().toString()))

                                        // Take an optional DimensionArgumentType argument "dimension" for the dimension
                                        .then(CommandManager.argument("dimension", DimensionArgumentType.dimension())
                                                // This is the last argument we need, so just create a waypoint with:
                                                .executes(context -> run(context,
                                                    // waypoint_id as the name
                                                    StringArgumentType.getString(context, "waypoint_id"),
                                                    // position as the coordinates
                                                    BlockPosArgumentType.getBlockPos(context, "position"),
                                                    // dimension as the dimension
                                                    DimensionArgumentType.getDimensionArgument(context, "dimension").getRegistryKey().getValue().toString())))))));
    }

    public static int run(CommandContext<ServerCommandSource> context, String waypointId, BlockPos position, String dimensionIdentifier) throws CommandSyntaxException {
        try {
            // Send a message in chat, saying the waypoint is being created
            context.getSource().sendMessage(Text.of("Creating waypoint [" + waypointId + "] at " + position.toShortString() + " in dimension \"" + dimensionIdentifier + "\"..."));

            // Get the serverState for managing the server's WaypointMap instance
            StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getWorld().getServer());

            // Add the new waypoint to the server's WaypointMap instance
            serverState.waypointMap.insert(waypointId,
                    // Create a waypoint with the passed parameters
                    new WaypointData(
                            context.getSource().getPlayer().getName().getString(),
                            position,
                            dimensionIdentifier,
                            true) // Always public, since the feature is not implemented
            );

            // Do this so that the serverState will be saved as soon as possible
            serverState.markDirty();

            // Send a message in chat, saying the waypoint has been created
            context.getSource().sendMessage(Text.of("Waypoint created!"));

            // Return 1 (command executed successfully)
            return 1;
        } catch (Exception e) {
            // Print any exception to the chat
            ChatUI.sendMsg(context.getSource(), ChatUI.errorText(e.toString()));
            // Return -1 (command execution failed)
            return -1;
        }
    }
}
