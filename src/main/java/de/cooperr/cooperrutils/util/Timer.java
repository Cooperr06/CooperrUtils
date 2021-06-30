package de.cooperr.cooperrutils.util;

import de.cooperr.cooperrutils.CooperrUtils;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
public class Timer {

    private final CooperrUtils plugin;

    private int seconds;
    private int minutes;
    private int hours;

    private int taskId;
    private boolean running;


    public Timer(CooperrUtils plugin) {
        this.plugin = plugin;
    }

    public void start() {

        if (running) {
            return;
        }

        running = true;

        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            onlinePlayer.sendTitle(ChatColor.GOLD + "Timer", ChatColor.DARK_GREEN + "startet", 10, 20, 10);
        }

        taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {

                onlinePlayer.sendActionBar(Component.text((hours < 10 ? "0" + hours : String.valueOf(hours)) + ":" +
                        (minutes < 10 ? "0" + minutes : String.valueOf(minutes)) + ":" +
                        (seconds < 10 ? "0" + seconds : String.valueOf(seconds)), NamedTextColor.GOLD, TextDecoration.BOLD));

            }
        }, 0, 20);
    }

    public void stop(boolean reset) {

        if (!running) {
            return;
        }

        running = false;

        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            onlinePlayer.sendMessage(Component.text("Die Zeit liegt bei ", NamedTextColor.DARK_GREEN)
                    .append(Component.text(getFormattedTime(), NamedTextColor.GOLD, TextDecoration.BOLD))
                    .append(Component.text("!", NamedTextColor.DARK_GREEN)));
        }

        plugin.getServer().getScheduler().cancelTask(taskId);

        if (reset) {
            seconds = 0;
            minutes = 0;
            hours = 0;
        }
    }

    @NotNull
    public String getFormattedTime() {
        return (hours < 10 ? "0" + hours : String.valueOf(hours)) + ":" +
                (minutes < 10 ? "0" + minutes : String.valueOf(minutes)) + ":" +
                (seconds < 10 ? "0" + seconds : String.valueOf(seconds));
    }
}
