package de.cooperr.cooperrutils.listener;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerJoinListener implements Listener {

    private final CooperrUtils plugin;

    public PlayerJoinListener(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        event.joinMessage(Component.text("§a» §9" + player.getName()));

        player.addAttachment(plugin, "cooperrutils.default", true);

        Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();

        if (scoreboard.getObjective("heartboard") != null) {
            scoreboard.getObjective("heartboard").getScore(player.getUniqueId().toString()).setScore((int) (Math.round(player.getHealth() * 10D) / 10D));
        }
    }
}
