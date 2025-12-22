package zynix.runescuffed.UI;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import zynix.runescuffed.item.ModItems;

public class SpellHud {
    public static void render(DrawContext drawContext) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || RadialMenu.EQUIPPED_SPELL.equals("None")) return;

        int x = 10;
        int y = drawContext.getScaledWindowHeight() - 25;
        ItemStack icon = getIconForSpell(RadialMenu.EQUIPPED_SPELL);

        drawContext.fill(x - 2, y - 2, x + 18, y + 18, 0x80000000);
        drawContext.drawItem(icon, x, y);
        drawContext.drawText(client.textRenderer, RadialMenu.EQUIPPED_SPELL, x + 22, y + 4, 0xFFFFFF, true);
    }

    private static ItemStack getIconForSpell(String spell) {
        return switch (spell) {
            case "Fire Strike", "Fire Bolt" -> new ItemStack(Items.BLAZE_POWDER);
            case "Wind Strike", "Wind Bolt" -> new ItemStack(Items.WIND_CHARGE);
            case "Earth Strike", "Earth Bolt" -> new ItemStack(Items.FIREWORK_STAR);
            case "Water Strike", "Water Bolt" -> new ItemStack(Items.HEART_OF_THE_SEA);
            case "Bones to Bananas", "Bones to Golden Carrots" -> new ItemStack(Items.BONE);
            default -> new ItemStack(ModItems.RUNE_STAFF);
        };
    }
}