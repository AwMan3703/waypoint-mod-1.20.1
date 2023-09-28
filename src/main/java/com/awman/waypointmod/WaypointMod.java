package com.awman.waypointmod;

import com.awman.waypointmod.util.ModRegistries;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaypointMod implements ModInitializer {
	// This class is the main entry point for the mod's initialization

	// Mod's id constant, needed in many files
	public static final String MOD_ID = "waypoint-mod";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// What permission level is considered op by the mod
	public static final int opPermissionLevel = 1;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized, but this mod doesn't really need them.

		// Register the mod's stuff
		ModRegistries.registerEverything();

	}
}