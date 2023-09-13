package com.awman.waypointmod.argument;

import com.awman.waypointmod.util.storage.StateSaverAndLoader;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.concurrent.CompletableFuture;


public class waypointAuthorArgumentType implements ArgumentType<String> {

    public static <S> String getUsername(CommandContext<S> context, String name) {
        return "unknown_user";
    }

    public static waypointAuthorArgumentType username() {
        return new waypointAuthorArgumentType();
    }

    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        return reader.getString();
    }

    public static CompletableFuture<Suggestions> suggestWaypointAuthors(CommandContext context, SuggestionsBuilder builder) {
        String[] suggestions = {
                "no", "suggestions", "yet"
        };

        return CommandSource.suggestMatching(suggestions, builder);
        //return ArgumentType.super.listSuggestions(context, builder);
    }
}
