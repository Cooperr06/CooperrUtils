package de.cooperr.cooperrutils.listener;

import de.cooperr.cooperrutils.CooperrUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncChatListener implements Listener {

    private final CooperrUtils plugin;

    public AsyncChatListener(CooperrUtils plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {

        Player player = event.getPlayer();

        event.message(Component.text("")
                .color(TextColor.fromHexString("#FFAA00"))
                .content(player.getName() + " ")
                .color(TextColor.fromHexString("#AAAAAA"))
                .content("» ")
                .color(TextColor.fromHexString("#FFFFFF"))
                .content(((TextComponent) event.originalMessage()).content()));
    }
}
