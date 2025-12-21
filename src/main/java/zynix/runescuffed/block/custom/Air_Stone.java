package zynix.runescuffed.block.custom;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import zynix.runescuffed.item.ModItems;
import zynix.runescuffed.util.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Air_Stone extends Block {
    public Air_Stone(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient() && entity instanceof ItemEntity itemEntity) {
            ItemStack inputStack = itemEntity.getStack();

            if (isValidItem(inputStack)) {
                int multiplier = inputStack.isOf(ModItems.PURE_RUNE) ? 3 : 1;

                int newCount = inputStack.getCount() * multiplier;
                itemEntity.setStack(new ItemStack(ModItems.AIR_RUNE, newCount));

                world.playSound(null, pos, SoundEvents.ENTITY_BREEZE_INHALE, SoundCategory.BLOCKS, 1f, 1f);

                if (world instanceof net.minecraft.server.world.ServerWorld serverWorld) {
                    serverWorld.spawnParticles(ParticleTypes.GUST,
                            pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5,
                            5, 0.1, 0.1, 0.1, 0.05);
                }
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    private boolean isValidItem(ItemStack stack) {
        return stack.isIn(ModTags.Items.TRANSFORMABLE_ITEMS);
    }
}