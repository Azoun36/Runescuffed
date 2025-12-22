package zynix.runescuffed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import zynix.runescuffed.block.ModBlocks;
import zynix.runescuffed.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(registryLookup, recipeExporter) {
            @Override
            public void generate() {
                StonecuttingRecipeJsonBuilder.createStonecutting(
                                Ingredient.ofItems(ModItems.RUNE_CHUNK),
                                RecipeCategory.MISC,
                                ModItems.RUNE, 3
                        )
                        .criterion(hasItem(ModItems.RUNE_CHUNK), conditionsFromItem(ModItems.RUNE_CHUNK))
                        .offerTo(exporter);

                offerReversibleCompactingRecipes(RecipeCategory.BUILDING_BLOCKS, ModItems.PURE_RUNE,
                        RecipeCategory.DECORATIONS, ModBlocks.RUNE_BLOCK);

                List<ItemConvertible> RUNE_SMELTABLES = List.of(ModItems.RUNE_CHUNK, ModItems.RUNE);

                offerSmelting(RUNE_SMELTABLES, RecipeCategory.MISC, ModItems.PURE_RUNE, 0.25f, 200, "pure_rune");
                offerBlasting(RUNE_SMELTABLES, RecipeCategory.MISC, ModItems.PURE_RUNE, 0.25f, 100, "pure_rune");
            }
        };
    }

    @Override
    public String getName() {
        return "RuneScuffed Recipes";
    }
}