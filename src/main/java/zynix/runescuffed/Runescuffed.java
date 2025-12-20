package zynix.runescuffed;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zynix.runescuffed.block.ModBlocks;
import zynix.runescuffed.item.ModItems;

public class Runescuffed implements ModInitializer {
	public static final String MOD_ID = "runescuffed";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		LOGGER.info("Runescuffed has finished loading.");
	}
}