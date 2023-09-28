package com.awman.waypointmod.util;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

public class ChatUI {
    public static void sendMsg(ServerCommandSource recipient, Text text) {
        recipient.sendMessage(text);
    }

    // Custom formatting text
    public static MutableText niceText(String message, @Nullable Formatting color, @Nullable HoverEvent onHover, @Nullable ClickEvent onClick) {
        return MutableText.of(TextContent.EMPTY)
                .append(message)
                .setStyle(
                        Style.EMPTY
                                .withColor(color)
                                .withHoverEvent(onHover)
                                .withClickEvent(onClick)
                );
    }

    // Some default text formatting:

    // Text formatting for when an error occurs
    public static MutableText errorText(String message) {
        return MutableText.of(TextContent.EMPTY) // Create a new MutableText object
                .append("WaypointMod ERROR: " + message) // Append the message string
                .setStyle( // Set the style
                        Style.EMPTY
                                .withColor(Formatting.RED) // Make it red
                );

    // Error text overload with tooltip
    }public static MutableText errorText(String message, String tooltip) {
        return errorText(message) // Get the basic error text
                .setStyle( // Set the style
                        Style.EMPTY
                                .withColor(Formatting.RED) // Make it red
                                .withHoverEvent(HoverEvent.Action.SHOW_TEXT.buildHoverEvent(Text.of(tooltip))) // Add the tooltip on hover
                );
    }

    // Text formatting for confirmation messages
    public static MutableText confirmationText(String message) {
        return MutableText.of(TextContent.EMPTY) // Create a new MutableText object
                .append(message) // Append the message string
                .setStyle( // Set the style
                        Style.EMPTY
                                .withColor(Formatting.RED) // Make it green
                );
    }

    // Confirmation text overload with tooltip
    public static MutableText confirmationText(String message, String tooltip) {
        return confirmationText(message) // Get the basic confirmation text
                .setStyle( // Set the style
                        Style.EMPTY
                                .withColor(Formatting.RED) // Make it green
                                .withHoverEvent(HoverEvent.Action.SHOW_TEXT.buildHoverEvent(Text.of(tooltip))) // Add the tooltip on hover
                );
    }
}
