package me.ferlo.potalert;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

@Mod(
        modid = Contants.MOD_ID,
        name = Contants.NAME,
        version = Contants.VERSION,
        acceptedMinecraftVersions = "[1.8.9]"
)
public class PotAlertMod {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final ISound sound = PositionedSoundRecord.create(Contants.SOUND);

    private Config config;
    private float lastHealth;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        config = new Config();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new Command(config));

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new GuiHandler(config));
    }

    @SubscribeEvent
    public void mouseEvent(MouseEvent event) {
        this.updateDamage();
    }

    @SubscribeEvent
    public void keyboardEvent(InputEvent.KeyInputEvent event) {
        this.updateDamage();
    }

    @SubscribeEvent
    public void damageTakenEvent(LivingHurtEvent event) {
        this.updateDamage();
    }

    private void updateDamage() {

        if (config.enabled() &&
                mc.thePlayer.getHealth() <= config.alertValue() * 2 &&
                lastHealth > mc.thePlayer.getHealth()) {
            mc.getSoundHandler().playSound(sound);
        }

        lastHealth = mc.thePlayer.getHealth();
    }
}
