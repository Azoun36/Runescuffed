package zynix.runescuffed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import zynix.runescuffed.UI.RadialConfig;
import zynix.runescuffed.UI.RadialMenu;

public class RunescuffedClient implements ClientModInitializer {
    private static KeyBinding radialKey;

    @Override
    public void onInitializeClient() {
        var categoryId = Identifier.of("runescuffed", "ui_category");

        var uiCategory = new KeyBinding.Category(categoryId);

        int keyCode = InputUtil.fromTranslationKey(RadialConfig.hotkey).getCode();

        radialKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.runescuffed.radial_menu",
                InputUtil.Type.KEYSYM,
                keyCode,
                uiCategory
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (radialKey.wasPressed() && client.currentScreen == null) {
                InputUtil.Key boundKey = InputUtil.fromTranslationKey(RadialConfig.hotkey);
                client.setScreen(new RadialMenu(boundKey));
            }
        });
    }
}
