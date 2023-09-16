package com.awman.waypointmod.command.suggestion;

import com.awman.waypointmod.WaypointMod;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.awman.waypointmod.util.data.WaypointData;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WaypointNameSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        ServerCommandSource source = context.getSource();

        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(source.getServer());

        // Create the waypoints hashmap, to store waypoint names
        HashMap<String, String> waypoints = new HashMap<>();

        // For each saved waypoint:
        for (Map.Entry<String, WaypointData> entry : serverState.waypointMap.entrySet()) {
            // Get the waypoint's name
            String name = entry.getKey();
            // Get the waypoint's author
            String author = entry.getValue().author;

            if (
                (context.getSource().getName().equals(author)) || // If the player is the waypoint's author OR
                (context.getSource().hasPermissionLevel(WaypointMod.opPermissionLevel)) || // If the player is an op OR
                entry.getValue().isPublic() // If the waypoint is public
            ) {
                // Put waypoint's name and author in the hashmap
                waypoints.put(
                        name, author + " // " + (entry.getValue().isPublic() ? "public" : "private")
                );
            }
        }

        // Take all the waypoints and their author's name, and suggest them
        for (Map.Entry<String, String> entry : waypoints.entrySet()) {
            builder.suggest(
                    entry.getKey(),
                    Text.of("by @" + entry.getValue())
            );
        }

        // Return the suggestions
        return CompletableFuture.completedFuture(builder.build());
    }
}
