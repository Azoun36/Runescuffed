    package zynix.runescuffed;

    import net.fabricmc.api.ClientModInitializer;
    import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
    import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
    import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
    import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
    import net.minecraft.client.option.KeyBinding;
    import net.minecraft.client.util.InputUtil;
    import net.minecraft.text.Text;
    import net.minecraft.util.Identifier;
    import org.lwjgl.glfw.GLFW;
    import zynix.runescuffed.UI.RadialConfig;
    import zynix.runescuffed.UI.RadialMenu;
    import zynix.runescuffed.UI.SpellHud;

    public class RunescuffedClient implements ClientModInitializer {
        private static KeyBinding radialKey;
        private static final KeyBinding.Category RUNESCUFFED_CATEGORY =
                KeyBinding.Category.create(Identifier.of("runescuffed", "ui"));

        @Override
        public void onInitializeClient() {
            HudElementRegistry.addLast(
                    Identifier.of("runescuffed", "spell_hud"),
                    (drawContext, tickCounter) -> SpellHud.render(drawContext)
            );

            int keyCode = GLFW.GLFW_KEY_V;
            if (RadialConfig.hotkey != null && !RadialConfig.hotkey.isEmpty()) {
                keyCode = InputUtil.fromTranslationKey(RadialConfig.hotkey).getCode();
            }

            radialKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                    "key.runescuffed.radial_menu",
                    InputUtil.Type.KEYSYM,
                    keyCode,
                    RUNESCUFFED_CATEGORY
            ));

            ClientTickEvents.END_CLIENT_TICK.register(client -> {
                if (client.player == null) return;

                if (radialKey.wasPressed()) {
                    if (client.player.isSneaking()) {
                        RadialMenu.EQUIPPED_SPELL = "None";
                        ClientPlayNetworking.send(new SpellNetwork.SpellChangePayload("None"));
                        client.player.sendMessage(Text.literal("§eSpell Cleared"), true);
                    }
                    else if (client.currentScreen == null) {
                        SpellRegistry.init(client.player);

                        InputUtil.Key boundKey = InputUtil.fromTranslationKey(radialKey.getBoundKeyTranslationKey());
                        client.setScreen(new RadialMenu(boundKey));
                    }
                }
            });
        }
    }
