package zynix.runescuffed.world;

import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import zynix.runescuffed.Runescuffed;
import zynix.runescuffed.block.ModBlocks;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> RUNE_ORE_PILE_KEY = registerKey("rune_ore_pile");
    public static final RegistryKey<ConfiguredFeature<?, ?>> FIRE_STONE_KEY = registerKey("fire_stone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> AIR_STONE_KEY = registerKey("air_stone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> WATER_STONE_KEY = registerKey("water_stone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> EARTH_STONE_KEY = registerKey("earth_stone");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NATURE_STONE_KEY = registerKey("nature_stone");

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, RUNE_ORE_PILE_KEY, Runescuffed.RUNE_ORE_PILE_FEATURE, DefaultFeatureConfig.INSTANCE);

        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest sandstoneReplaceables = new BlockMatchRuleTest(Blocks.SANDSTONE);

        registerStone(context, FIRE_STONE_KEY, stoneReplaceables, sandstoneReplaceables, ModBlocks.FIRE_STONE);
        registerStone(context, AIR_STONE_KEY, stoneReplaceables, sandstoneReplaceables, ModBlocks.AIR_STONE);
        registerStone(context, WATER_STONE_KEY, stoneReplaceables, sandstoneReplaceables, ModBlocks.WATER_STONE);
        registerStone(context, EARTH_STONE_KEY, stoneReplaceables, sandstoneReplaceables, ModBlocks.EARTH_STONE);
        registerStone(context, NATURE_STONE_KEY, stoneReplaceables, sandstoneReplaceables, ModBlocks.NATURE_STONE);
    }

    private static void registerStone(Registerable<ConfiguredFeature<?, ?>> context,
                                      RegistryKey<ConfiguredFeature<?, ?>> key,
                                      RuleTest stone, RuleTest sandstone,
                                      net.minecraft.block.Block block) {
        register(context, key, Feature.SIMPLE_BLOCK,
                new SimpleBlockFeatureConfig(BlockStateProvider.of(block)));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Identifier.of(Runescuffed.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context, RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
