package de.cooperr.cooperrutils.listener;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    private final CooperrUtils plugin;

    public EntityDamageListener(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {

        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (!plugin.getConfig().getBoolean("settings.damage-indicator")) {
            return;
        }

        if (event.getFinalDamage() == 0) {
            return;
        }

        plugin.getServer().broadcast(Component.text("§9" + player.getName() + " §7hat §9" + event.getFinalDamage() +
                " §7Schaden durch §9" + event.getCause().name() + " §7bekommen!"), "cooperrutils.default");
    }
}
