package de.cooperr.cooperrutils.listener;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final CooperrUtils plugin;

    public PlayerJoinListener(CooperrUtils plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        event.joinMessage(Component.text("")
                .color(TextColor.fromHexString("#55FF55"))
                .content("» ")
                .color(TextColor.fromHexString("#5555FF"))
                .content(player.getName()));

        player.addAttachment(plugin, "cooperrutils.default", true);
    }
}
