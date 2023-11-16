package net.random.things;

import btw.AddonHandler;
import btw.community.randomthings.RandomThingsAddon;
import net.fabricmc.api.ModInitializer;

public class RandomThingsMod implements ModInitializer {
	// This logger can be used to write text to the console and the log file.
	// That way, it's clear which mod wrote info, warnings, and errors.
	// public static final Logger LOGGER = Logger.getLogger("modid");

	@Override
	public void onInitialize() {
		//keyBinding = new KeyBinding(StatCollector.translateToLocal("key.craftguide.open"), Keyboard.KEY_G);
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		RandomThingsAddon.getInstance().initKeybind();
		AddonHandler.logMessage("Hello Fabric world!");
	}
}
