package zynix.runescuffed.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import zynix.runescuffed.Runescuffed;

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


    private static Item registerItem(RegistryKey<Item> key, Item item) {
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void registerModItems() {
        Runescuffed.LOGGER.info("Registering mod items");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(PURE_RUNE);
            fabricItemGroupEntries.add(RUNE);
            fabricItemGroupEntries.add(RUNE_CHUNK);
            fabricItemGroupEntries.add(FIRE_RUNE);
            fabricItemGroupEntries.add(AIR_RUNE);
            fabricItemGroupEntries.add(WATER_RUNE);
            fabricItemGroupEntries.add(EARTH_RUNE);
            fabricItemGroupEntries.add(NATURE_RUNE);
        });
    }
}
