package zynix.runescuffed.event;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import zynix.runescuffed.SpellDetails;

public class MagicProjectileImpact extends ThrownItemEntity {
    private final String spellName;

    public MagicProjectileImpact(World world, LivingEntity owner, String spell, ItemStack visual) {
        super(EntityType.SNOWBALL, owner, world, visual);
        this.spellName = spell;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SNOWBALL;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (this.getEntityWorld() instanceof ServerWorld serverWorld
                && entityHitResult.getEntity() instanceof LivingEntity target) {

            float damage = SpellDetails.getDamage(this.spellName);
            target.damage(serverWorld, this.getDamageSources().magic(), damage);

            switch (this.spellName) {
                case "Fire Bolt" -> target.setOnFireFor(5);
                case "Wind Bolt" -> target.takeKnockback(1.5F, -this.getVelocity().getX(), -this.getVelocity().getZ());
                case "Earth Bolt" -> target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 100, 2));
                case "Water Bolt" -> {
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 2));
                    target.extinguish();
                }

                case "Fire Strike" -> target.setOnFireFor(1);
                case "Wind Strike" -> target.takeKnockback(0.4F, -this.getVelocity().getX(), -this.getVelocity().getZ());
                case "Earth Strike" -> target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60, 1));
                case "Water Strike" -> {
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1));
                    target.extinguish();
                }
            }
        }
        this.discard();
    }
}