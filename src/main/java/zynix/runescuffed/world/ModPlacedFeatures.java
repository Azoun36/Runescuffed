package zynix.runescuffed.world;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.*;
import zynix.runescuffed.Runescuffed;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> RUNE_ORE_PLACED_KEY = registerKey("rune_ore_placed");
    public static final RegistryKey<PlacedFeature> FIRE_STONE_PLACED_KEY = registerKey("fire_stone_placed");
    public static final RegistryKey<PlacedFeature> AIR_STONE_PLACED_KEY = registerKey("air_stone_placed");
    public static final RegistryKey<PlacedFeature> WATER_STONE_PLACED_KEY = registerKey("water_stone_placed");
    public static final RegistryKey<PlacedFeature> EARTH_STONE_PLACED_KEY = registerKey("earth_stone_placed");
    public static final RegistryKey<PlacedFeature> NATURE_STONE_PLACED_KEY = registerKey("nature_stone_placed");

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatures = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        register(context, RUNE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.RUNE_ORE_PILE_KEY),
                List.of(RarityFilterPlacementModifier.of(28), SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(60), YOffset.fixed(120)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12),
                        BiomePlacementModifier.of()));

        register(context, AIR_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.AIR_STONE_KEY),
                List.of(RarityFilterPlacementModifier.of(6), SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(100), YOffset.fixed(256)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12),
                        BlockFilterPlacementModifier.of(BlockPredicate.anyOf(
                                BlockPredicate.matchingBlockTag(Direction.DOWN.getVector(), BlockTags.STONE_ORE_REPLACEABLES),
                                BlockPredicate.matchingBlocks(Direction.DOWN.getVector(), Blocks.CALCITE, Blocks.TUFF)
                        )), BiomePlacementModifier.of()));

        register(context, WATER_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.WATER_STONE_KEY),
                List.of(RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(30), YOffset.fixed(120)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.hasSturdyFace(Direction.UP), 12),
                        BlockFilterPlacementModifier.of(BlockPredicate.anyOf(
                                BlockPredicate.matchingBlockTag(Direction.DOWN.getVector(), BlockTags.SNOW),
                                BlockPredicate.matchingBlocks(Direction.DOWN.getVector(), Blocks.ICE, Blocks.PACKED_ICE, Blocks.BLUE_ICE, Blocks.GRAVEL, Blocks.DIRT)
                        )), BiomePlacementModifier.of()));

        register(context, FIRE_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.FIRE_STONE_KEY),
                List.of(RarityFilterPlacementModifier.of(6), SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(60), YOffset.fixed(110)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12),
                        BlockFilterPlacementModifier.of(BlockPredicate.anyOf(
                                BlockPredicate.matchingBlockTag(Direction.DOWN.getVector(), BlockTags.SAND),
                                BlockPredicate.matchingBlockTag(Direction.DOWN.getVector(), BlockTags.TERRACOTTA),
                                BlockPredicate.matchingBlocks(Direction.DOWN.getVector(), Blocks.SANDSTONE, Blocks.RED_SANDSTONE)
                        )), BiomePlacementModifier.of()));

        register(context, EARTH_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.EARTH_STONE_KEY),
                List.of(RarityFilterPlacementModifier.of(6), SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(40), YOffset.fixed(110)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12),
                        BlockFilterPlacementModifier.of(BlockPredicate.matchingBlockTag(Direction.DOWN.getVector(), BlockTags.STONE_ORE_REPLACEABLES)),
                        BiomePlacementModifier.of()));

        register(context, NATURE_STONE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.NATURE_STONE_KEY),
                List.of(RarityFilterPlacementModifier.of(6), SquarePlacementModifier.of(),
                        HeightRangePlacementModifier.uniform(YOffset.fixed(60), YOffset.fixed(110)),
                        EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12),
                        BlockFilterPlacementModifier.of(BlockPredicate.anyOf(
                                BlockPredicate.matchingBlockTag(Direction.DOWN.getVector(), BlockTags.DIRT),
                                BlockPredicate.matchingBlocks(Direction.DOWN.getVector(), Blocks.GRASS_BLOCK, Blocks.MOSS_BLOCK, Blocks.MUD)
                        )), BiomePlacementModifier.of()));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(Runescuffed.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}