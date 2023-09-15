package com.awman.waypointmod.command.suggestion;

import com.awman.waypointmod.WaypointMod;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.awman.waypointmod.util.storage.WaypointData;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class WaypointAuthorSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

    public static final Identifier ID = new Identifier(WaypointMod.MOD_ID, "WaypointAuthorSuggestionProvider");

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getServer());

        // Create the authors hashmap, to store usernames and the # of waypoints they have created
        HashMap<String, Integer> authors = new HashMap<>();

        // For each saved waypoint:
        for (Map.Entry<String, WaypointData> entry : serverState.waypointMap.entrySet()) {
            // Get the username
            String author = entry.getKey();

            // Put username in the hashmap, then:
            // If the hashmap already has an entry for this user, increment it
            // Otherwise, initialize it (just set it to 1)
            authors.put(
                    author, // The author's username
                    authors.getOrDefault(author, 0) + 1 // Increment or initialize
            );
        }

        // Take all the authors and the # of their waypoints, and suggest them
        for (Map.Entry<String, Integer> entry : authors.entrySet()) {
            builder.suggest(
                    entry.getKey(),
                    Text.of(entry.getValue().toString() + " waypoints")
            );
        }

        // Return (to) the future
        return CompletableFuture.completedFuture(builder.build());
    }
}
