package zynix.runescuffed;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zynix.runescuffed.block.ModBlocks;
import zynix.runescuffed.item.ModItemGroups;
import zynix.runescuffed.item.ModItems;
import zynix.runescuffed.world.gen.ModWorldGeneration;

public class Runescuffed implements ModInitializer {
	public static final String MOD_ID = "runescuffed";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		ModWorldGeneration.generateModWorldgen();

		LOGGER.info("Runescuffed has finished loading.");
	}
}