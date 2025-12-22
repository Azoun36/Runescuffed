package zynix.runescuffed.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import zynix.runescuffed.block.ModBlocks;

public class RuneOrePileFeature extends Feature<DefaultFeatureConfig> {
    public RuneOrePileFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        BlockState floorState = world.getBlockState(origin.down());

        // 1. Check valid floor
        boolean isValidFloor = floorState.isIn(BlockTags.DIRT) ||
                floorState.isOf(Blocks.GRASS_BLOCK) ||
                floorState.isIn(BlockTags.SAND) ||
                floorState.isIn(BlockTags.STONE_ORE_REPLACEABLES) ||
                floorState.isIn(BlockTags.TERRACOTTA) ||
                floorState.isOf(Blocks.SANDSTONE) ||
                floorState.isOf(Blocks.RED_SANDSTONE) ||
                floorState.isOf(Blocks.GRAVEL) ||
                floorState.isOf(Blocks.CALCITE) ||
                floorState.isOf(Blocks.SNOW_BLOCK) ||
                floorState.isOf(Blocks.ICE);

        if (!isValidFloor) return false;

        // 2. Generate the Spire
        int peakHeight = 6 + random.nextInt(4);
        for (int x = -2; x <= 2; x++) {
            for (int z = -2; z <= 2; z++) {
                BlockPos currentPos = origin.add(x, 0, z);
                double distance = Math.sqrt(x * x + z * z) + (random.nextDouble() * 0.4 - 0.2);
                int height = 0;

                if (distance < 0.5) height = peakHeight;
                else if (distance <= 1.4) height = (int) (peakHeight * (0.5 + (random.nextDouble() * 0.2)));
                else if (distance <= 2.2) {
                    if (random.nextFloat() < 0.7f) height = Math.max(1, (int) (peakHeight * (0.2 + (random.nextDouble() * 0.2))));
                } else if (distance <= 2.8) {
                    if (random.nextFloat() < 0.2f) height = 1;
                }

                for (int i = 0; i < height; i++) {
                    BlockPos placePos = currentPos.up(i);
                    BlockState stateAtPos = world.getBlockState(placePos);
                    if (stateAtPos.isAir() || stateAtPos.isIn(BlockTags.LEAVES) || stateAtPos.isReplaceable()) {
                        world.setBlockState(placePos, ModBlocks.RUNE_ORE.getDefaultState(), 3);
                    }
                }
            }
        }

        BlockPos bestPos = null;
        BlockState bestStone = null;
        int bestPriority = -1;

        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                if (Math.abs(x) < 2 && Math.abs(z) < 2) continue;

                BlockPos checkPos = origin.add(x, 0, z);
                BlockState currentSurface = world.getBlockState(checkPos);
                BlockState below = world.getBlockState(checkPos.down());

                int currentPriority = -1;
                BlockState currentStone = null;

                if (currentSurface.isOf(Blocks.WATER) || currentSurface.isIn(BlockTags.SNOW) || below.isOf(Blocks.ICE) || below.isOf(Blocks.PACKED_ICE)) {
                    currentStone = ModBlocks.WATER_STONE.getDefaultState();
                    currentPriority = 4;
                }
                else if (below.isIn(BlockTags.SAND) || below.isIn(BlockTags.TERRACOTTA)) {
                    currentStone = ModBlocks.FIRE_STONE.getDefaultState();
                    currentPriority = 3;
                }
                else if (below.isOf(Blocks.GRASS_BLOCK) || below.isIn(BlockTags.DIRT)) {
                    currentStone = ModBlocks.NATURE_STONE.getDefaultState();
                    currentPriority = 2;
                }
                else if (below.isIn(BlockTags.STONE_ORE_REPLACEABLES) || below.isOf(Blocks.CALCITE)) {
                    currentStone = (checkPos.getY() > 100) ? ModBlocks.AIR_STONE.getDefaultState() : ModBlocks.EARTH_STONE.getDefaultState();
                    currentPriority = 1;
                }

                if (currentPriority > bestPriority) {
                    bestPriority = currentPriority;
                    bestPos = checkPos;
                    bestStone = currentStone;
                }

                if (bestPriority >= 4) break;
            }
            if (bestPriority >= 4) break;
        }

        if (bestPos == null) {
            int rx = (random.nextBoolean() ? 1 : -1) * 2;
            int rz = (random.nextBoolean() ? 1 : -1) * 2;
            bestPos = origin.add(rx, 0, rz);
            bestStone = ModBlocks.EARTH_STONE.getDefaultState();
        }

        BlockState targetState = world.getBlockState(bestPos);
        if (targetState.isAir() || targetState.isOf(Blocks.WATER) || targetState.isReplaceable() || targetState.isIn(BlockTags.SNOW)) {
            world.setBlockState(bestPos, bestStone, 3);
        }

        return true;
    }
}