package zynix.runescuffed;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import zynix.runescuffed.item.ModItems;
import zynix.runescuffed.util.UpgradeStateSaveLoad;

public class SpellDetails {
    public static float getDamage(String spell) {
        if (spell.contains("Bolt")) return 12.0f;
        if (spell.contains("Strike")) return 4.0f;
        return 0.0f;
    }

    // ADD THIS METHOD
    public static SoundEvent getSound(String spell) {
        if (spell.contains("Fire")) return SoundEvents.ENTITY_GHAST_SHOOT;
        if (spell.contains("Wind")) return SoundEvents.ENTITY_BREEZE_SHOOT;
        if (spell.contains("Earth")) return SoundEvents.BLOCK_BASALT_HIT;
        if (spell.contains("Water")) return SoundEvents.ENTITY_PLAYER_SPLASH;
        if (spell.contains("Bones")) return SoundEvents.ENTITY_ILLUSIONER_CAST_SPELL;
        return null;
    }

    public static Item getTransmutationResult(PlayerEntity player) {
        if (player.getEntityWorld() != null && !player.getEntityWorld().isClient()) {
            if (player.getEntityWorld().getServer() != null) {
                UpgradeStateSaveLoad state = UpgradeStateSaveLoad.getServerState(player.getEntityWorld().getServer());
                boolean isUpgraded = state.upgradedPlayers.contains(player.getUuid());
                return isUpgraded ? Items.GOLDEN_CARROT : ModItems.BANANA;
            }
        }
        return ModItems.BANANA;
    }
}