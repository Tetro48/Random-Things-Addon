package net.random.things;

import btw.AddonHandler;
import net.fabricmc.api.ModInitializer;
/*import net.java.games.input.Keyboard;
import net.minecraft.src.StatCollector;
import java.util.logging.Logger;
import javax.swing.text.JTextComponent.KeyBinding;*/

public class RandomThingsMod implements ModInitializer {
	//private KeyBinding keyBinding;
	//keyBinding = new KeyBinding(StatCollector.translateToLocal("key.craftguide.open"), Keyboard.KEY_G);
	// This logger can be used to write text to the console and the log file.
	// That way, it's clear which mod wrote info, warnings, and errors.
	// public static final Logger LOGGER = Logger.getLogger("modid");

	@Override
	public void onInitialize() {
		//keyBinding = new KeyBinding(StatCollector.translateToLocal("key.craftguide.open"), Keyboard.KEY_G);
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		AddonHandler.logMessage("Hello Fabric world!");
	}

}
