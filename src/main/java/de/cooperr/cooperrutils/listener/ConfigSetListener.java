package de.cooperr.cooperrutils.listener;

import de.cooperr.cooperrutils.CooperrUtils;
import de.cooperr.cooperrutils.util.ConfigSetEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ConfigSetListener implements Listener {

    private final CooperrUtils plugin;

    public ConfigSetListener(CooperrUtils plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onConfigSet(ConfigSetEvent event) {
        // TODO
    }
}
