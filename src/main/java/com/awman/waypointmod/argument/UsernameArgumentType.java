package com.awman.waypointmod.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.text.Text;


public class UsernameArgumentType implements ArgumentType<String> {
    @Override
    public String parse(StringReader reader) throws CommandSyntaxException {
        // Get the start index of the argument
        int argBeginning = reader.getCursor();
        // Move to a position where the reader can read
        if (!reader.canRead()) {
            reader.skip();
        }

        // Then move all the way to the end of the argument, we know it's the end when either:
        // reader.canRead() is false (end of the line)
        // reader.peek() is " " (space, the argument has ended)
        while (reader.canRead() && reader.peek() != ' ') {
            reader.skip();
        }

        String argument = reader.getString().substring(argBeginning, reader.getCursor());
        try {
            // The actual logic
            return "";

        } catch (Exception ex) {
            // Throw in case of exception in the logic
            throw new SimpleCommandExceptionType(Text.literal(ex.getMessage())).createWithContext(reader);
        }
    }

    public static <S> String getUsername(CommandContext<S> context, String name) {
        return "unknown_user";
    }

    public static UsernameArgumentType username() {
        return new UsernameArgumentType();
    }
}
