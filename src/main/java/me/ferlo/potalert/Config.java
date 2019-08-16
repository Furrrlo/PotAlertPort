package me.ferlo.potalert;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

import java.io.File;

public class Config {

    private final Configuration config;

    private final Property enabled;
    private final Property alertValue;

    public Config() {
        final File file = new File(Loader.instance().getConfigDir(), Contants.CONFIG_NAME);
        config = new Configuration(file);
        config.load();

        enabled = config.get("PotAlert", "enabled", false);
        alertValue = config.get("PotAlert", "alert_value", 0, "", 1, 9);
    }

    public boolean enabled() {
        return enabled.getBoolean();
    }

    public void enabled(boolean enabled) {
        this.enabled.set(enabled);
        this.config.save();
    }

    public int alertValue() {
        return alertValue.getInt();
    }

    public void alertValue(int alertValue) {
        this.alertValue.set(alertValue);
        this.config.save();
    }
}
