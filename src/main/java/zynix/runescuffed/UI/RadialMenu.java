package zynix.runescuffed.UI;

import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class RadialMenu extends Screen {
    public static final List<RadialOption> OPTIONS = new ArrayList<>();

    static {
        OPTIONS.add(new RadialOption("Diamond", new ItemStack(Items.DIAMOND), () -> System.out.println("Selected Diamond!")));
        OPTIONS.add(new RadialOption("Sword", new ItemStack(Items.DIAMOND_SWORD), () -> System.out.println("Selected Sword!")));
        OPTIONS.add(new RadialOption("Food", new ItemStack(Items.COOKED_BEEF), () -> System.out.println("Selected Food!")));
    }

    private int cx, cy;
    private final int innerR = 60;
    private final int thickness = 40;
    private final int iconRadius = 80;

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

        int slices = Math.max(1, OPTIONS.size());
        float sector = 360f / slices;
        float startDeg = -90f - (sector / 2f);

        selected = computeSelected(mouseX, mouseY, startDeg);

        drawSimpleCircle(ctx, cx, cy, (int) ((innerR + thickness) * inEase), 0x80000000);

        if (selected >= 0) {
            drawSelectedWedge(ctx, selected, slices, startDeg, inEase);
        }

        drawIcons(ctx, inEase, startDeg);
        drawCenterText(ctx);

        super.render(ctx, mouseX, mouseY, delta);
    }

    private void drawIcons(DrawContext ctx, float pop, float startDeg) {
        if (OPTIONS.isEmpty()) return;
        float sector = 360f / OPTIONS.size();
        for (int i = 0; i < OPTIONS.size(); i++) {
            float midDeg = (i + 0.5f) * sector + startDeg;
            double rad = Math.toRadians(midDeg);
            int ix = cx + (int) (Math.cos(rad) * this.iconRadius * pop);
            int iy = cy + (int) (Math.sin(rad) * this.iconRadius * pop);

            // 1.21.1 standard drawItem call
            ctx.drawItem(OPTIONS.get(i).icon(), ix - 8, iy - 8);
        }
    }

    private void drawCenterText(DrawContext ctx) {
        if (selected >= 0 && selected < OPTIONS.size()) {
            String name = OPTIONS.get(selected).name();
            int tw = this.textRenderer.getWidth(name);
            ctx.drawText(this.textRenderer, name, cx - tw / 2, cy - 4, 0xFFFFFFFF, true);
        }
    }

    private int computeSelected(int mouseX, int mouseY, float startDeg) {
        double dx = mouseX - cx, dy = mouseY - cy;
        double dist = Math.hypot(dx, dy);
        if (dist < innerR || dist > (innerR + thickness + 20)) return -1;
        double ang = Math.toDegrees(Math.atan2(dy, dx));
        ang = (ang % 360 + 360) % 360;
        double rel = (ang - startDeg % 360 + 360) % 360;
        return (int) Math.floor(rel / (360.0 / OPTIONS.size())) % OPTIONS.size();
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        // click.button() returns the button integer (0 = Left Click)
        // click.x() and click.y() provide the mouse coordinates
        if (click.button() == GLFW.GLFW_MOUSE_BUTTON_LEFT && selected != -1) {
            OPTIONS.get(selected).run();
            this.close();
            return true;
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    public void tick() {
        if (this.client != null && this.client.getWindow() != null) {
            long handle = this.client.getWindow().getHandle();
            boolean down = GLFW.glfwGetKey(handle, openKey.getCode()) == GLFW.GLFW_PRESS;
            if (!down) {
                if (selected != -1) OPTIONS.get(selected).run();
                this.close();
            }
        }
    }

    private void drawSimpleCircle(DrawContext ctx, int x, int y, int radius, int color) {
        ctx.fill(x - radius, y - radius, x + radius, y + radius, color);
    }

    private void drawSelectedWedge(DrawContext ctx, int idx, int total, float startDeg, float pop) {
        ctx.fill(cx - 10, cy - 10, cx + 10, cy + 10, 0x40FFFFFF);
    }
}