package zynix.runescuffed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import zynix.runescuffed.block.ModBlocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.RUNE_BLOCK)
                .add(ModBlocks.RUNE_ORE)
                .add(ModBlocks.FIRE_STONE)
                .add(ModBlocks.AIR_STONE)
                .add(ModBlocks.WATER_STONE)
                .add(ModBlocks.EARTH_STONE)
                .add(ModBlocks.NATURE_STONE);

        valueLookupBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.RUNE_ORE);

        valueLookupBuilder(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.FIRE_STONE)
                .add(ModBlocks.AIR_STONE)
                .add(ModBlocks.WATER_STONE)
                .add(ModBlocks.EARTH_STONE)
                .add(ModBlocks.NATURE_STONE);
    }
}