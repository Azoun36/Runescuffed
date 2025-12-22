package zynix.runescuffed;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zynix.runescuffed.UI.RadialConfig;
import zynix.runescuffed.block.ModBlocks;
import zynix.runescuffed.item.ModItemGroups;
import zynix.runescuffed.item.ModItems;
import zynix.runescuffed.world.ModPlacedFeatures;
import zynix.runescuffed.world.feature.RuneOrePileFeature;

public class Runescuffed implements ModInitializer {
	public static final String MOD_ID = "runescuffed";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Feature<DefaultFeatureConfig> RUNE_ORE_PILE_FEATURE = new RuneOrePileFeature(DefaultFeatureConfig.CODEC);

	@Override
	public void onInitialize() {
		RadialConfig.load();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();

		Registry.register(Registries.FEATURE, Identifier.of(MOD_ID, "rune_ore_pile_feature"), RUNE_ORE_PILE_FEATURE);

		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
				GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.RUNE_ORE_PLACED_KEY);

		BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_FOREST).or(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE)),
				GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.NATURE_STONE_PLACED_KEY);

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.SAVANNA),
				GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.EARTH_STONE_PLACED_KEY);

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DESERT, BiomeKeys.BADLANDS),
				GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.FIRE_STONE_PLACED_KEY);

		BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_MOUNTAIN),
				GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.AIR_STONE_PLACED_KEY);

		BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_OCEAN).or(BiomeSelectors.tag(BiomeTags.IS_RIVER)),
				GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.WATER_STONE_PLACED_KEY);

		LOGGER.info("Runescuffed initialized for 2025.");
	}
}