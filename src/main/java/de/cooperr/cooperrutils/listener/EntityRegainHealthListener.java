package de.cooperr.cooperrutils.listener;

import de.cooperr.cooperrutils.CooperrUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener implements Listener {

    private final CooperrUtils plugin;

    public EntityRegainHealthListener(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {

        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective("heartboard") != null) {
            plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective("heartboard").getScore(player.getUniqueId().toString())
                    .setScore((int) (Math.round(player.getHealth() * 10D) / 10D));
        }
    }
}
