package de.cooperr.cooperrutils.listener;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
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

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {

        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective("heartboard") != null) {
            plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective("heartboard").getScore(player.getUniqueId().toString())
                    .setScore((int) (Math.round(player.getHealth() * 10D) / 10D));
        }

        if (plugin.getConfig().getBoolean("settings.damage-indicator")) {

            if (event.getFinalDamage() == 0) {
                return;
            }

            plugin.getServer().broadcast(Component.text("> " + player.getName() + " | " + event.getFinalDamage() + " | " + event.getCause().name(),
                    NamedTextColor.GRAY, TextDecoration.OBFUSCATED), "cooperrutils.default");
        }

        // > Cooperr06 | 3.5 | EXPLOSION
    }
}
