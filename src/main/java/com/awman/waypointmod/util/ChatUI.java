package com.awman.waypointmod.util;

import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;

public class ChatUI {
    // <CHAT ACCESS>
    public static void sendMsg(ServerCommandSource recipient, Text text) {
        recipient.sendMessage(text);
    }
    public static void sendSpacer(ServerCommandSource recipient) { recipient.sendMessage(Text.of("")); }
    public static void sendError(ServerCommandSource recipient, String text) {
        sendMsg(recipient, ChatUI.errorText(text));
        sendMsg(recipient, ChatUI.link("Report this issue on Github", "https://github.com/AwMan3703/waypoint-mod-1.20.1/issues/new"));
    }

    // <COLOR CONSTANTS>
    public static final Formatting color_Header = Formatting.GOLD;
    public static final Formatting color_Main = Formatting.WHITE;
    public static final Formatting color_Secondary = Formatting.GRAY;
    public static final Formatting color_Bg = Formatting.DARK_GRAY;
    public static final Formatting color_Link = Formatting.BLUE;
    public static final Formatting color_Positive = Formatting.GREEN;
    public static final Formatting color_PositiveBg = Formatting.DARK_GREEN;
    public static final Formatting color_Negative = Formatting.RED;
    public static final Formatting color_NegativeBg = Formatting.DARK_RED;

    // <CUSTOM TEXT FORMATTING>
    public static MutableText styleableText(String message) {
        // Return a basic MutableText
        return MutableText.of(TextContent.EMPTY).append(message);
    }

    public static MutableText colored(String message, Formatting color) {
        // Return colored text
        return styleableText(message)
                .setStyle( Style.EMPTY.withColor(color) );
    }

    public static MutableText decorated(String message, boolean bold, boolean italic, boolean obfuscated, boolean strikethrough, boolean underline) {
        // Create decorated text
        MutableText r = styleableText(message)
                .setStyle( Style.EMPTY
                        .withBold(bold)
                        .withItalic(italic)
                        .withObfuscated(obfuscated)
                );

        // Add lines according to parameters
        if (strikethrough) r.setStyle( r.getStyle().withFormatting(Formatting.STRIKETHROUGH) );
        if (underline) r.setStyle( r.getStyle().withFormatting(Formatting.UNDERLINE) );

        // Return the text
        return r;
    }

    public static MutableText link(String message, String url) {
        return styleableText(message)
                .setStyle(
                        Style.EMPTY
                                .withColor(color_Link)
                                .withFormatting(Formatting.UNDERLINE)
                                .withHoverEvent(HoverEvent.Action.SHOW_TEXT.buildHoverEvent(Text.of("Click to open " + url)))
                                .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                );
    }

    public static MutableText styledText(String message, Formatting formatting, @Nullable HoverEvent onHover, @Nullable ClickEvent onClick) {
        MutableText r = styleableText(message); // Create the MutableText

        // Add the options that are not null
        if (formatting != null) r.setStyle( r.getStyle().withFormatting(formatting) );
        if (onHover != null) r.setStyle( r.getStyle().withHoverEvent(onHover) );
        if (onClick != null) r.setStyle( r.getStyle().withClickEvent(onClick) );

        // Return the text
        return r;
    }

    // <DEFAULT TEXT FORMATTING>
    // Text formatting for when an error occurs
    public static MutableText errorText(String message) {
        return styleableText("WaypointMod ERROR: " + message) // Get the message as MutableText
                .setStyle( // Set the style
                        Style.EMPTY
                                .withColor(color_Negative) // Make it red
                );

    // Error text overload with tooltip
    }public static MutableText errorText(String message, String tooltip) {
        return errorText(message) // Get the basic error text
                .setStyle( // Set the style
                        Style.EMPTY
                                .withColor(color_Negative) // Make it red
                                .withHoverEvent(HoverEvent.Action.SHOW_TEXT.buildHoverEvent(Text.of(tooltip))) // Add the tooltip on hover
                );
    }

    // Text formatting for confirmation messages
    public static MutableText confirmationText(String message) {
        return styleableText(message) // Get the message as MutableText
                .setStyle( // Set the style
                        Style.EMPTY
                                .withColor(color_Positive) // Make it green
                );
    }

    // Confirmation text overload with tooltip
    public static MutableText confirmationText(String message, String tooltip) {
        return confirmationText(message) // Get the basic confirmation text
                .setStyle( // Set the style
                        Style.EMPTY
                                .withColor(color_Positive) // Make it green
                                .withHoverEvent(HoverEvent.Action.SHOW_TEXT.buildHoverEvent(Text.of(tooltip))) // Add the tooltip on hover
                );
    }
}
