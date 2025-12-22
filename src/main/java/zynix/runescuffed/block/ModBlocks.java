package zynix.runescuffed.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import zynix.runescuffed.Runescuffed;
import zynix.runescuffed.block.custom.Fire_Stone;
import zynix.runescuffed.block.custom.Air_Stone;
import zynix.runescuffed.block.custom.Water_Stone;
import zynix.runescuffed.block.custom.Earth_Stone;
import zynix.runescuffed.block.custom.Nature_Stone;

import java.util.function.Function;

public class ModBlocks {
    public static final Block RUNE_BLOCK = register("rune_block", Block::new,
            AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.IRON)
    );

    public static final Block RUNE_ORE = register("rune_ore",
            (settings) -> new ExperienceDroppingBlock(UniformIntProvider.create(2, 5), settings),
            AbstractBlock.Settings.create()
                    .strength(3f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)
    );

    public static final Block FIRE_STONE = register("fire_stone",
            Fire_Stone::new,
            AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)
    );

    public static final Block AIR_STONE = register("air_stone",
            Air_Stone::new,
            AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)
    );

    public static final Block WATER_STONE = register("water_stone",
            Water_Stone::new,
            AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)
    );

    public static final Block EARTH_STONE = register("earth_stone",
            Earth_Stone::new,
            AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)
    );

    public static final Block NATURE_STONE = register("nature_stone",
            Nature_Stone::new,
            AbstractBlock.Settings.create()
                    .strength(4f)
                    .requiresTool()
                    .sounds(BlockSoundGroup.DEEPSLATE)
    );

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings) {
        RegistryKey<Block> blockKey = keyOfBlock(name);

        Block block = blockFactory.apply(settings.registryKey(blockKey));

        RegistryKey<Item> itemKey = keyOfItem(name);

        BlockItem blockItem = new BlockItem(block, new Item.Settings()
                .registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, blockItem);

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Runescuffed.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, name));
    }

    public static void registerModBlocks() {
        Runescuffed.LOGGER.info("Registering mod blocks");

    }
}