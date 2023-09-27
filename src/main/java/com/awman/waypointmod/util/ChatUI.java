package com.awman.waypointmod.util;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

public class ChatUI {
    public static void sendMsg(ServerCommandSource recipient, Text text) {
        recipient.sendMessage(text);
    }

    public static MutableText errorText(String message, @Nullable String tooltip) {
        return MutableText.of( TextContent.EMPTY )
                .append(message)
                .setStyle(
                        Style.EMPTY
                                .withColor(Formatting.RED)
                                .withHoverEvent(HoverEvent.Action.SHOW_TEXT.buildHoverEvent(Text.of(tooltip)))
                );
    }
}
