package zynix.runescuffed.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import zynix.runescuffed.Runescuffed;
import zynix.runescuffed.block.ModBlocks;
import zynix.runescuffed.item.custom.ElementalTalisman;

public class ModItems {
    public static final RegistryKey<Item> PURE_RUNE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "pure_rune"));
    public static final Item PURE_RUNE = registerItem(PURE_RUNE_KEY, new Item(new Item.Settings().registryKey(PURE_RUNE_KEY)));

    public static final RegistryKey<Item> RUNE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "rune"));
    public static final Item RUNE = registerItem(RUNE_KEY, new Item(new Item.Settings().registryKey(RUNE_KEY)));

    public static final RegistryKey<Item> RUNE_CHUNK_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "rune_chunk"));
    public static final Item RUNE_CHUNK = registerItem(RUNE_CHUNK_KEY, new Item(new Item.Settings().registryKey(RUNE_CHUNK_KEY)));

    public static final RegistryKey<Item> FIRE_RUNE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "fire_rune"));
    public static final Item FIRE_RUNE = registerItem(FIRE_RUNE_KEY, new Item(new Item.Settings().registryKey(FIRE_RUNE_KEY)));

    public static final RegistryKey<Item> AIR_RUNE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "air_rune"));
    public static final Item AIR_RUNE = registerItem(AIR_RUNE_KEY, new Item(new Item.Settings().registryKey(AIR_RUNE_KEY)));

    public static final RegistryKey<Item> WATER_RUNE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "water_rune"));
    public static final Item WATER_RUNE = registerItem(WATER_RUNE_KEY, new Item(new Item.Settings().registryKey(WATER_RUNE_KEY)));

    public static final RegistryKey<Item> EARTH_RUNE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "earth_rune"));
    public static final Item EARTH_RUNE = registerItem(EARTH_RUNE_KEY, new Item(new Item.Settings().registryKey(EARTH_RUNE_KEY)));

    public static final RegistryKey<Item> NATURE_RUNE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "nature_rune"));
    public static final Item NATURE_RUNE = registerItem(NATURE_RUNE_KEY, new Item(new Item.Settings().registryKey(NATURE_RUNE_KEY)));

    public static final RegistryKey<Item> FIRE_TALISMAN_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "fire_talisman"));
    public static final Item FIRE_TALISMAN = registerItem(FIRE_TALISMAN_KEY,
            new ElementalTalisman(new Item.Settings().registryKey(FIRE_TALISMAN_KEY).maxCount(1), ModBlocks.FIRE_STONE, "Fire Stone", Formatting.RED));

    public static final RegistryKey<Item> AIR_TALISMAN_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "air_talisman"));
    public static final Item AIR_TALISMAN = registerItem(AIR_TALISMAN_KEY,
            new ElementalTalisman(new Item.Settings().registryKey(AIR_TALISMAN_KEY).maxCount(1), ModBlocks.AIR_STONE, "Air Stone", Formatting.WHITE));

    public static final RegistryKey<Item> WATER_TALISMAN_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "water_talisman"));
    public static final Item WATER_TALISMAN = registerItem(WATER_TALISMAN_KEY,
            new ElementalTalisman(new Item.Settings().registryKey(WATER_TALISMAN_KEY).maxCount(1), ModBlocks.WATER_STONE, "Water Stone", Formatting.BLUE));

    public static final RegistryKey<Item> EARTH_TALISMAN_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "earth_talisman"));
    public static final Item EARTH_TALISMAN = registerItem(EARTH_TALISMAN_KEY,
            new ElementalTalisman(new Item.Settings().registryKey(EARTH_TALISMAN_KEY).maxCount(1), ModBlocks.EARTH_STONE, "Earth Stone", Formatting.GREEN));

    public static final RegistryKey<Item> NATURE_TALISMAN_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Runescuffed.MOD_ID, "nature_talisman"));
    public static final Item NATURE_TALISMAN = registerItem(NATURE_TALISMAN_KEY,
            new ElementalTalisman(new Item.Settings().registryKey(NATURE_TALISMAN_KEY).maxCount(1), ModBlocks.NATURE_STONE, "Nature Stone", Formatting.DARK_GREEN));

    private static Item registerItem(RegistryKey<Item> key, Item item) {
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void registerModItems() {
        Runescuffed.LOGGER.info("Registering mod items");

    }
}
