package com.awman.waypointmod.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class UsernameArgumentType implements ArgumentType<String> {
    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        return null;
    }

    public static <S> String getUsername(CommandContext<S> context, String name) {
        return "unknown_user";
    }

    public static UsernameArgumentType username() {
        return new UsernameArgumentType();
    }
}
