package zynix.runescuffed;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import zynix.runescuffed.event.MagicProjectileImpact;
import zynix.runescuffed.item.ModItems;
import zynix.runescuffed.util.UpgradeStateSaveLoad;
import java.util.Objects;



public class SpellCaster {
    public static void init() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            if (world.isClient() || hand == Hand.OFF_HAND) return ActionResult.PASS;

            ItemStack mainHand = player.getStackInHand(hand);

            if (player.getItemCooldownManager().isCoolingDown(mainHand)) {
                return ActionResult.FAIL;
            }

            if (mainHand.isOf(Items.QUARTZ)) {
                UpgradeStateSaveLoad serverState = UpgradeStateSaveLoad.getServerState(Objects.requireNonNull(world.getServer()));
                if (!serverState.upgradedPlayers.contains(player.getUuid())) {
                    serverState.upgradedPlayers.add(player.getUuid());
                    serverState.markDirty();
                    SpellRegistry.init(player);
                    Runescuffed.PLAYER_SPELLS.put(player.getUuid(), "None");
                    mainHand.decrement(1);
                    player.sendMessage(Text.literal("§bYour spells have been upgraded permanently!"), false);
                    return ActionResult.SUCCESS;
                } else {
                    player.sendMessage(Text.literal("§7You have already upgraded your spells."), true);
                    return ActionResult.FAIL;
                }
            }

            if (!mainHand.isOf(ModItems.RUNE_STAFF)) return ActionResult.PASS;
            String spell = Runescuffed.PLAYER_SPELLS.getOrDefault(player.getUuid(), "None");

            if (spell.equalsIgnoreCase("None")) {
                player.sendMessage(Text.literal("§7No spell selected."), true);
                return ActionResult.FAIL;
            }

            boolean success = false;

            if (spell.contains("Bones")) {
                if (attemptTransmute(player, spell)) {
                    success = true;
                }
            } else {
                Item requiredRune = getRuneForSpell(spell);
                int runeCost = spell.contains("Bolt") ? 3 : 1;

                if (hasEnoughRunes(player, requiredRune, runeCost)) {
                    if (consumeRunes(player, requiredRune, runeCost)) {
                        spawnMagicProjectile(player, world, spell);
                        success = true;
                    }
                } else {
                    player.sendMessage(Text.literal("§cMissing " + runeCost + " " + requiredRune.getName().getString()), true);
                }
            }

            if (success) {
                player.getItemCooldownManager().set(mainHand, 20);
                return ActionResult.SUCCESS;
            }

            return ActionResult.FAIL;
        });
    }

    private static boolean attemptTransmute(PlayerEntity player, String spell) {
        ItemStack offhand = player.getOffHandStack();
        if (!offhand.isOf(Items.BONE)) {
            player.sendMessage(Text.literal("§cPlace bones in your off-hand!"), true);
            return false;
        }

        int boneCount = offhand.getCount();
        int divisor = spell.equals("Bones to Golden Carrots") ? 4 : 8;
        int runeCost = (int) Math.ceil((double) boneCount / divisor);

        if (hasEnoughRunes(player, ModItems.NATURE_RUNE, runeCost)) {
            if (consumeRunes(player, ModItems.NATURE_RUNE, runeCost)) {
                Item result = spell.equals("Bones to Golden Carrots") ? Items.GOLDEN_CARROT : ModItems.BANANA;
                player.setStackInHand(Hand.OFF_HAND, new ItemStack(result, boneCount));

                SoundEvent sound = SpellDetails.getSound(spell);
                if (sound != null) {
                    player.getEntityWorld().playSound(null, player.getBlockPos(), sound, SoundCategory.PLAYERS, 0.2f, 0.3f);
                }
            }
        } else {
            player.sendMessage(Text.literal("§cNeed " + runeCost + " Nature Runes!"), true);
        }
        return false;
    }

    private static boolean hasEnoughRunes(PlayerEntity player, Item rune, int amount) {
        if (player.getAbilities().creativeMode) return true;
        int count = 0;
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isOf(rune)) count += stack.getCount();
        }
        return count >= amount;
    }

    private static boolean consumeRunes(PlayerEntity player, Item rune, int amount) {
        if (player.getAbilities().creativeMode) return true;
        int remaining = amount;
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack stack = player.getInventory().getStack(i);
            if (stack.isOf(rune)) {
                int toTake = Math.min(stack.getCount(), remaining);
                stack.decrement(toTake);
                remaining -= toTake;
            }
            if (remaining <= 0) break;
        }
        player.getInventory().markDirty();
        return true;
    }

    private static Item getRuneForSpell(String spell) {
        if (spell.contains("Fire")) return ModItems.FIRE_RUNE;
        if (spell.contains("Wind")) return ModItems.AIR_RUNE;
        if (spell.contains("Earth")) return ModItems.EARTH_RUNE;
        if (spell.contains("Water")) return ModItems.WATER_RUNE;
        return ModItems.NATURE_RUNE;
    }

    private static void spawnMagicProjectile(PlayerEntity player, World world, String spell) {
        ItemStack visual = null;

        if (spell.contains("Fire")) {
            visual = new ItemStack(Items.BLAZE_POWDER);
        } else if (spell.contains("Wind")) {
            visual = new ItemStack(Items.WIND_CHARGE);
        } else if (spell.contains("Earth")) {
            visual = new ItemStack(Items.FIREWORK_STAR);
        } else if (spell.contains("Water")) {
            visual = new ItemStack(Items.HEART_OF_THE_SEA);
        }

        SoundEvent sound = SpellDetails.getSound(spell);
        if (sound != null) {
            world.playSound(null, player.getBlockPos(), sound, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        MagicProjectileImpact projectile = new MagicProjectileImpact(world, player, spell, visual);
        projectile.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 1.5F, 1.0F);
        world.spawnEntity(projectile);
    }
}