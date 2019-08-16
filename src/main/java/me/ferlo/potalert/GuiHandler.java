package me.ferlo.potalert;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiHandler extends Gui {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final Config cfg;

    public GuiHandler(Config cfg) {
        this.cfg = cfg;
    }

    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE)
            return;
        if(!cfg.enabled())
            return;

        if (mc.thePlayer.getHealth() <= cfg.alertValue() * 2) {
            this.square(0, 0x77ff0000);
            this.square(1, 0x66ff0000);
            this.square(2, 0x55ff0000);
            this.square(3, 0x44ff0000);
            this.square(4, 0x33ff0000);
            this.square(5, 0x22ff0000);
            this.square(6, 0x11ff0000);
        }
    }

    private void square(int layer, int color) {
        int border = layer * 2;
        int size = 2;

        final ScaledResolution scaled = new ScaledResolution(mc);
        int width = scaled.getScaledWidth();
        int height = scaled.getScaledHeight();

        GuiHandler.drawRect(border, border, (width - border), (size + border), color);
        GuiHandler.drawRect(border, (height - (size + border)), (width - border), (height - border), color);
        GuiHandler.drawRect(border, (height - (size + border)), (size + border), (size + border), color);
        GuiHandler.drawRect((width - border), (height - (size + border)), (width - (size + border)), (size + border), color);
    }
}
