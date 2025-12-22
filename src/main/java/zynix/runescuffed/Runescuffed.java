package zynix.runescuffed;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.ActionResult;
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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Runescuffed implements ModInitializer {
	public static final String MOD_ID = "runescuffed";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Feature<DefaultFeatureConfig> RUNE_ORE_PILE_FEATURE = new RuneOrePileFeature(DefaultFeatureConfig.CODEC);
	public static final Map<UUID, String> PLAYER_SPELLS = new HashMap<>();

	@Override
	public void onInitialize() {
		RadialConfig.load();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModItemGroups.registerItemGroups();
		SpellCaster.init();

		PayloadTypeRegistry.playC2S().register(SpellNetwork.SpellChangePayload.ID, SpellNetwork.SpellChangePayload.CODEC);

		ServerPlayNetworking.registerGlobalReceiver(SpellNetwork.SpellChangePayload.ID, (payload, context) -> {
			context.server().execute(() -> {
				PLAYER_SPELLS.put(context.player().getUuid(), payload.spellName());
			});
		});

		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			return ActionResult.PASS;
		});

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

		LOGGER.info("Runescuffed initialized.");
	}
}