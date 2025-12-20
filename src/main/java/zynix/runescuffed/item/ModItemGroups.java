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
            Identifier.of(Runescuffed.MOD_ID, "runeitems"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModItems.RUNE)) // 1. Use a Supplier (lambda) for the icon
                    .displayName(Text.translatable("itemgroup.runescuffed.runeitems")) // 2. Add parentheses for the method call
                    .entries((displayContext, entries) -> { // 3. Use lambda syntax for entries
                        entries.add(ModItems.RUNE);
                        entries.add(ModItems.PURERUNE);
                    })
                    .build());
    public static final ItemGroup RuneBlocks_Group = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Runescuffed.MOD_ID, "runeblocks"),
            FabricItemGroup.builder()
                    .icon(() -> new ItemStack(ModBlocks.RUNEORE))
                    .displayName(Text.translatable("itemgroup.runescuffed.runeitems"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModBlocks.RUNEORE);
                        entries.add(ModBlocks.RUNEBLOCK);
                    })
                    .build());

    public static void registerItemGroups() {
        Runescuffed.LOGGER.info("Registering Item Groups for " + Runescuffed.MOD_ID);
    }
}
