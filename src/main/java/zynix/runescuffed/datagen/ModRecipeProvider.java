package zynix.runescuffed.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.recipe.StonecuttingRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
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
                                ModItems.RUNE, 2
                        )
                        .criterion(hasItem(ModItems.RUNE_CHUNK), conditionsFromItem(ModItems.RUNE_CHUNK))
                        .offerTo(exporter);

                StonecuttingRecipeJsonBuilder.createStonecutting(
                                Ingredient.ofItems(ModBlocks.FIRE_STONE),
                                RecipeCategory.MISC,
                                ModItems.FIRE_RUNE, 18
                        )
                        .criterion(hasItem(ModBlocks.FIRE_STONE), conditionsFromItem(ModBlocks.FIRE_STONE))
                        .offerTo(exporter);

                StonecuttingRecipeJsonBuilder.createStonecutting(
                                Ingredient.ofItems(ModBlocks.AIR_STONE),
                                RecipeCategory.MISC,
                                ModItems.AIR_RUNE, 18
                        )
                        .criterion(hasItem(ModBlocks.AIR_STONE), conditionsFromItem(ModBlocks.AIR_STONE))
                        .offerTo(exporter);

                StonecuttingRecipeJsonBuilder.createStonecutting(
                                Ingredient.ofItems(ModBlocks.WATER_STONE),
                                RecipeCategory.MISC,
                                ModItems.WATER_RUNE, 18
                        )
                        .criterion(hasItem(ModBlocks.WATER_STONE), conditionsFromItem(ModBlocks.WATER_STONE))
                        .offerTo(exporter);

                StonecuttingRecipeJsonBuilder.createStonecutting(
                                Ingredient.ofItems(ModBlocks.EARTH_STONE),
                                RecipeCategory.MISC,
                                ModItems.EARTH_RUNE, 18
                        )
                        .criterion(hasItem(ModBlocks.EARTH_STONE), conditionsFromItem(ModBlocks.EARTH_STONE))
                        .offerTo(exporter);

                StonecuttingRecipeJsonBuilder.createStonecutting(
                                Ingredient.ofItems(ModBlocks.NATURE_STONE),
                                RecipeCategory.MISC,
                                ModItems.NATURE_RUNE, 18
                        )
                        .criterion(hasItem(ModBlocks.NATURE_STONE), conditionsFromItem(ModBlocks.NATURE_STONE))
                        .offerTo(exporter);

                offerReversibleCompactingRecipes(RecipeCategory.BUILDING_BLOCKS, ModItems.PURE_RUNE,
                        RecipeCategory.DECORATIONS, ModBlocks.RUNE_BLOCK);

                List<ItemConvertible> RUNE_SMELTABLES = List.of(ModItems.RUNE_CHUNK, ModItems.RUNE);

                offerSmelting(RUNE_SMELTABLES, RecipeCategory.MISC, ModItems.PURE_RUNE, 0.25f, 200, "pure_rune");
                offerBlasting(RUNE_SMELTABLES, RecipeCategory.MISC, ModItems.PURE_RUNE, 0.25f, 100, "pure_rune");

                ShapedRecipeJsonBuilder.create(registryLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModItems.RUNE_STAFF, 1)
                        .pattern(" B ")
                        .pattern(" A ")
                        .pattern(" A ")
                        .input('A', Items.STICK)
                        .input('B', ModItems.PURE_RUNE)
                        .criterion(hasItem(ModItems.PURE_RUNE), conditionsFromItem(ModItems.PURE_RUNE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(registryLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModItems.FIRE_TALISMAN, 1)
                        .pattern(" C ")
                        .pattern(" B ")
                        .pattern(" A ")
                        .input('A', Items.COAL)
                        .input('B', Items.STICK)
                        .input('C', ModItems.PURE_RUNE)
                        .criterion(hasItem(ModItems.PURE_RUNE), conditionsFromItem(ModItems.PURE_RUNE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(registryLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModItems.AIR_TALISMAN, 1)
                        .pattern(" C ")
                        .pattern(" B ")
                        .pattern(" A ")
                        .input('A', Items.FEATHER)
                        .input('B', Items.STICK)
                        .input('C', ModItems.PURE_RUNE)
                        .criterion(hasItem(ModItems.PURE_RUNE), conditionsFromItem(ModItems.PURE_RUNE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(registryLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModItems.WATER_TALISMAN, 1)
                        .pattern(" C ")
                        .pattern(" B ")
                        .pattern(" A ")
                        .input('A', Ingredient.ofItems(Items.LILY_PAD, Items.KELP))
                        .input('B', Items.STICK)
                        .input('C', ModItems.PURE_RUNE)
                        .criterion(hasItem(ModItems.PURE_RUNE), conditionsFromItem(ModItems.PURE_RUNE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(registryLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModItems.EARTH_TALISMAN, 1)
                        .pattern(" C ")
                        .pattern(" B ")
                        .pattern(" A ")
                        .input('A', Blocks.STONE)
                        .input('B', Items.STICK)
                        .input('C', ModItems.PURE_RUNE)
                        .criterion(hasItem(ModItems.PURE_RUNE), conditionsFromItem(ModItems.PURE_RUNE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(registryLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModItems.NATURE_TALISMAN, 1)
                        .pattern(" C ")
                        .pattern(" B ")
                        .pattern(" A ")
                        .input('A', ItemTags.SAPLINGS)
                        .input('B', Items.STICK)
                        .input('C', ModItems.PURE_RUNE)
                        .criterion(hasItem(ModItems.PURE_RUNE), conditionsFromItem(ModItems.PURE_RUNE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(registryLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModBlocks.FIRE_STONE, 1)
                        .pattern("AAA")
                        .pattern("ABA")
                        .pattern("AAA")
                        .input('A', ModItems.PURE_RUNE)
                        .input('B', Items.MAGMA_CREAM)
                        .criterion(hasItem(ModItems.PURE_RUNE), conditionsFromItem(ModItems.PURE_RUNE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(registryLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModBlocks.AIR_STONE, 1)
                        .pattern("AAA")
                        .pattern("ABA")
                        .pattern("AAA")
                        .input('A', ModItems.PURE_RUNE)
                        .input('B', Items.GOAT_HORN)
                        .criterion(hasItem(ModItems.PURE_RUNE), conditionsFromItem(ModItems.PURE_RUNE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(registryLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModBlocks.WATER_STONE, 1)
                        .pattern("AAA")
                        .pattern("ABA")
                        .pattern("AAA")
                        .input('A', ModItems.PURE_RUNE)
                        .input('B', Items.HEART_OF_THE_SEA)
                        .criterion(hasItem(ModItems.PURE_RUNE), conditionsFromItem(ModItems.PURE_RUNE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(registryLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModBlocks.EARTH_STONE, 1)
                        .pattern("AAA")
                        .pattern("ABA")
                        .pattern("AAA")
                        .input('A', ModItems.PURE_RUNE)
                        .input('B', Blocks.AMETHYST_BLOCK)
                        .criterion(hasItem(ModItems.PURE_RUNE), conditionsFromItem(ModItems.PURE_RUNE))
                        .offerTo(exporter);

                ShapedRecipeJsonBuilder.create(registryLookup.getOrThrow(RegistryKeys.ITEM), RecipeCategory.MISC, ModBlocks.NATURE_STONE, 1)
                        .pattern("AAA")
                        .pattern("ABA")
                        .pattern("AAA")
                        .input('A', ModItems.PURE_RUNE)
                        .input('B', Items.COMPASS)
                        .criterion(hasItem(ModItems.PURE_RUNE), conditionsFromItem(ModItems.PURE_RUNE))
                        .offerTo(exporter);

            }
        };
    }

    @Override
    public String getName() {
        return "RuneScuffed Recipes";
    }
}