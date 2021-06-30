package de.cooperr.cooperrutils.listener;

import de.cooperr.cooperrutils.CooperrUtils;
import de.cooperr.cooperrutils.util.ConfigSetEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class ConfigSetListener implements Listener {

    private final CooperrUtils plugin;

    public ConfigSetListener(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onConfigSet(ConfigSetEvent event) {
        plugin.getLogger().log(Level.CONFIG, "Set value " + event.getSetValue() + " to key " + event.getSetKey());
    }
}
