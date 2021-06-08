package de.cooperr.cooperrutils.listener;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerQuitListener implements Listener {

    private final CooperrUtils plugin;

    public PlayerQuitListener(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        event.quitMessage(Component.text("« ", NamedTextColor.RED)
                .append(Component.text(player.getName(), NamedTextColor.GOLD)));

        Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();

        if (scoreboard.getObjective("heartboard") != null) {
            scoreboard.getObjective("heartboard").getScore(player.getUniqueId().toString()).setScore(0);
        }
    }
}
