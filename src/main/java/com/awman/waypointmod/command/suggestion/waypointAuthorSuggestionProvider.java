package com.awman.waypointmod.command.suggestion;
import com.awman.waypointmod.WaypointMod;
import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.awman.waypointmod.util.storage.WaypointData;
import com.awman.waypointmod.util.storage.WaypointMap;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class waypointAuthorSuggestionProvider implements SuggestionProvider<ServerCommandSource> {

    public static final Identifier ID = new Identifier(WaypointMod.MOD_ID, "waypointAuthorSuggestionProvider");

    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) throws CommandSyntaxException {

        StateSaverAndLoader serverState = StateSaverAndLoader.getServerState(context.getSource().getServer());

        HashMap<String, Integer> authors = new HashMap<String, Integer>();
        for (Map.Entry<String, WaypointData> entry : serverState.waypointMap.entrySet()) {
            String author = entry.getKey();

            authors.put(
                    author, // The author's username
                    authors.get(author)!=null ? (authors.get(author) + 1) : 1 // Funny ternary to increment or initialize
            );
        }

        builder.suggest("username", Text.of("# of waypoints"));

        return builder.buildFuture();
    }
}
