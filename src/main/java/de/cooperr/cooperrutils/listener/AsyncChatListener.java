package de.cooperr.cooperrutils.listener;

import de.cooperr.cooperrutils.CooperrUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncChatListener implements Listener {

    private final CooperrUtils plugin;

    public AsyncChatListener(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {

        Player player = event.getPlayer();

        event.message(Component.text(player.getName(), NamedTextColor.GOLD)
                .append(Component.text(" » ", NamedTextColor.GRAY))
                .append(event.originalMessage().color(NamedTextColor.WHITE)));
    }
}
