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


    private static Item registerItem(RegistryKey<Item> key, Item item) {
        return Registry.register(Registries.ITEM, key, item);
    }

    public static void registerModItems() {
        Runescuffed.LOGGER.info("Registering mod items for " + Runescuffed.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(PURE_RUNE);
            fabricItemGroupEntries.add(RUNE);
        });
    }
}
