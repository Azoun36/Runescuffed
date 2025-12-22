package zynix.runescuffed.UI;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;
import zynix.runescuffed.SpellNetwork;
import java.util.ArrayList;
import java.util.List;

public class RadialMenu extends Screen {
    public static final List<RadialOption> OPTIONS = new ArrayList<>();
    public static String EQUIPPED_SPELL = "None";

    private int cx, cy;
    private final float innerR = 50f;
    private final float thickness = 40f;
    private final float iconRadius = 70f;

    private final InputUtil.Key openKey;
    private int selected = -1;
    private final long openedAt = Util.getMeasuringTimeMs();

    public RadialMenu(InputUtil.Key key) {
        super(Text.literal("Radial Menu"));
        this.openKey = key;
    }

    @Override
    protected void init() {
        this.cx = this.width / 2;
        this.cy = this.height / 2;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        float inEase = Math.min(1f, (Util.getMeasuringTimeMs() - openedAt) / 150f);

        if (OPTIONS.isEmpty()) {
            drawCenterText(ctx, "No Spells Available");
            super.render(ctx, mouseX, mouseY, delta);
            return;
        }

        int count = OPTIONS.size();
        float sector = 360f / count;
        float startDeg = -90f - (sector / 2f);


        selected = computeSelected(mouseX, mouseY, startDeg);

        for (int i = 0; i < count; i++) {
            float sliceStart = startDeg + (i * sector);
            int color = (i == selected) ? 0x90FFFFFF : 0x60000000;
            drawBigSlice(ctx, sliceStart, sector, innerR * inEase, (innerR + thickness) * inEase, color);
        }

        drawIcons(ctx, inEase, startDeg);
        drawCenterText(ctx, selected >= 0 ? OPTIONS.get(selected).name() : "Equipped: " + EQUIPPED_SPELL);

        super.render(ctx, mouseX, mouseY, delta);
    }

    private void drawBigSlice(DrawContext ctx, float start, float sweep, float in, float out, int color) {
        for (float a = 0; a < sweep; a += 5.0f) {
            double rad = Math.toRadians(start + a);
            int x1 = (int) (cx + Math.cos(rad) * in);
            int y1 = (int) (cy + Math.sin(rad) * in);
            int x2 = (int) (cx + Math.cos(rad) * out);
            int y2 = (int) (cy + Math.sin(rad) * out);
            ctx.fill(x1, y1, x2 + 1, y2 + 1, color);
        }
    }

    private void drawIcons(DrawContext ctx, float pop, float startDeg) {
        if (OPTIONS.isEmpty()) return;
        float sector = 360f / OPTIONS.size();
        for (int i = 0; i < OPTIONS.size(); i++) {
            float midDeg = (i + 0.5f) * sector + startDeg;
            double rad = Math.toRadians(midDeg);
            int ix = cx + (int) (Math.cos(rad) * this.iconRadius * pop);
            int iy = cy + (int) (Math.sin(rad) * this.iconRadius * pop);
            ctx.drawItem(OPTIONS.get(i).icon(), ix - 8, iy - 8);
        }
    }

    private void drawCenterText(DrawContext ctx, String text) {
        int width = this.textRenderer.getWidth(text);
        ctx.drawText(this.textRenderer, text, cx - width / 2, cy - 4, 0xFFFFFFFF, true);
    }

    private int computeSelected(int mouseX, int mouseY, float startDeg) {
        if (OPTIONS.isEmpty()) return -1;

        double dx = mouseX - cx, dy = mouseY - cy;
        double dist = Math.hypot(dx, dy);
        if (dist < innerR || dist > (innerR + thickness + 20)) return -1;

        double ang = (Math.toDegrees(Math.atan2(dy, dx)) % 360 + 360) % 360;
        float normalizedStart = (startDeg % 360 + 360) % 360;
        double rel = (ang - normalizedStart + 360) % 360;

        return (int) Math.floor(rel / (360.0 / OPTIONS.size())) % OPTIONS.size();
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (click.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT && selected != -1) {
            executeSelection();
            return true;
        }
        return super.mouseClicked(click, doubled);
    }

    private void executeSelection() {
        if (selected >= 0 && selected < OPTIONS.size()) {
            String spellName = OPTIONS.get(selected).name();

            RadialMenu.EQUIPPED_SPELL = spellName;

            net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.send(
                    new SpellNetwork.SpellChangePayload(spellName)
            );

            OPTIONS.get(selected).run();
            this.close();
        }
    }

    @Override
    public void tick() {
        if (this.client != null) {
            long handle = this.client.getWindow().getHandle();
            if (GLFW.glfwGetKey(handle, openKey.getCode()) != GLFW.GLFW_PRESS) {
                executeSelection();
            }
        }
    }

}