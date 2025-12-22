package zynix.runescuffed.UI;

import net.minecraft.item.ItemStack;

public record RadialOption(String name, ItemStack icon, Runnable action) {
    public void run() {
        if (action != null) action.run();
    }
}