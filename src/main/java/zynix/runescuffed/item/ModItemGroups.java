package zynix.runescuffed.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zynix.runescuffed.Runescuffed;
import zynix.runescuffed.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup RuneItems_Group = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Runescuffed.MOD_ID, "rune_items"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.RUNE))
                    .displayName(Text.translatable("itemgroup.runescuffed.rune_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.RUNE);
                        entries.add(ModItems.PURE_RUNE);
                        entries.add(ModItems.RUNE_CHUNK);
                        entries.add(ModItems.FIRE_RUNE);
                        entries.add(ModItems.AIR_RUNE);
                        entries.add(ModItems.WATER_RUNE);
                        entries.add(ModItems.EARTH_RUNE);
                        entries.add(ModItems.NATURE_RUNE);
                        entries.add(ModItems.FIRE_TALISMAN);
                        entries.add(ModItems.AIR_TALISMAN);
                        entries.add(ModItems.WATER_TALISMAN);
                        entries.add(ModItems.EARTH_TALISMAN);
                        entries.add(ModItems.NATURE_TALISMAN);
                        entries.add(ModItems.RUNE_STAFF);
                        entries.add(ModItems.BANANA);
                    })
                    .build());
    public static final ItemGroup RuneBlocks_Group = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Runescuffed.MOD_ID, "rune_blocks"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModBlocks.RUNE_ORE))
                    .displayName(Text.translatable("itemgroup.runescuffed.rune_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.RUNE_ORE);
                        entries.add(ModBlocks.RUNE_BLOCK);
                        entries.add(ModBlocks.FIRE_STONE);
                        entries.add(ModBlocks.AIR_STONE);
                        entries.add(ModBlocks.WATER_STONE);
                        entries.add(ModBlocks.EARTH_STONE);
                        entries.add(ModBlocks.NATURE_STONE);
                    })
                    .build());

    public static void registerItemGroups() {
        Runescuffed.LOGGER.info("Registering item groups");
    }
}
