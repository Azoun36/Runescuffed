package zynix.runescuffed;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import zynix.runescuffed.UI.RadialMenu;
import zynix.runescuffed.UI.RadialOption;
import zynix.runescuffed.item.ModItems;
import zynix.runescuffed.util.UpgradeStateSaveLoad;

public class SpellRegistry {

    public static void init(PlayerEntity player) {
        if (player == null) return;

        RadialMenu.OPTIONS.clear();
        boolean isUpgraded = false;

        var server = MinecraftClient.getInstance().getServer();
        if (server != null) {
            UpgradeStateSaveLoad state = UpgradeStateSaveLoad.getServerState(server);
            isUpgraded = state.upgradedPlayers.contains(player.getUuid());
        }

        if (!isUpgraded) {
            addOption("Fire Strike", Items.BLAZE_POWDER);
            addOption("Wind Strike", Items.WIND_CHARGE);
            addOption("Earth Strike", Items.FIREWORK_STAR);
            addOption("Water Strike", Items.HEART_OF_THE_SEA);
            addOption("Bones to Bananas", ModItems.BANANA);
        } else {
            addOption("Fire Bolt", Items.BLAZE_POWDER);
            addOption("Wind Bolt", Items.WIND_CHARGE);
            addOption("Earth Bolt", Items.FIREWORK_STAR);
            addOption("Water Bolt", Items.HEART_OF_THE_SEA);
            addOption("Bones to Golden Carrots", Items.GOLDEN_CARROT);
        }
    }

    private static void addOption(String name, net.minecraft.item.Item icon) {
        RadialMenu.OPTIONS.add(new RadialOption(name, new ItemStack(icon), () -> {
            RadialMenu.EQUIPPED_SPELL = name;
        }));
    }
}