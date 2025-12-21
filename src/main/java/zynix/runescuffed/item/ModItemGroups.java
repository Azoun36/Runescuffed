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
                    .icon(() -> new ItemStack(ModItems.RUNE)) // 1. Use a Supplier (lambda) for the icon
                    .displayName(Text.translatable("itemgroup.runescuffed.rune_items")) // 2. Add parentheses for the method call
                    .entries((displayContext, entries) -> { // 3. Use lambda syntax for entries
                        entries.add(ModItems.RUNE);
                        entries.add(ModItems.PURE_RUNE);
                        entries.add(ModItems.RUNE_CHUNK);
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
                    })
                    .build());

    public static void registerItemGroups() {
        Runescuffed.LOGGER.info("Registering Item Groups for " + Runescuffed.MOD_ID);
    }
}
