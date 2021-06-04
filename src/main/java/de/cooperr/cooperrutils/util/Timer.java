package de.cooperr.cooperrutils.util;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;

public class Timer {

    private final CooperrUtils plugin;

    private int seconds;
    private int minutes;
    private int hours;

    private int taskId;
    private boolean running;


    public Timer(CooperrUtils plugin) {
        this.plugin = plugin;
        seconds = 0;
        minutes = 0;
        hours = 0;
    }

    public Timer(CooperrUtils plugin, int seconds, int minutes, int hours) {
        this.plugin = plugin;
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
    }

    public void start() {

        running = true;

        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            onlinePlayer.sendTitle("§6Timer", "§2startet", 10, 20, 10);
        }

        taskId = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {

                onlinePlayer.sendActionBar(Component.text("")
                        .color(TextColor.fromHexString("#FFAA00"))
                        .decoration(TextDecoration.BOLD, true)
                        .content((hours < 10 ? "0" + hours : String.valueOf(hours)) + ":" +
                                (minutes < 10 ? "0" + minutes : String.valueOf(minutes)) + ":" +
                                (seconds < 10 ? "0" + seconds : String.valueOf(seconds))));

            }
        }, 0, 20);
    }

    public void stop(boolean reset) {

        if (!running) {
            return;
        }

        running = false;

        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            onlinePlayer.sendTitle("§6Timer", "§4stoppt", 10, 20, 10);
            onlinePlayer.sendMessage(Component.text("")
                    .color(TextColor.fromHexString("#AAAAAA"))
                    .content("Die Zeit liegt bei ")
                    .color(TextColor.fromHexString("#FFAA00"))
                    .content(getFormattedTime())
                    .color(TextColor.fromHexString("#AAAAAA"))
                    .content("!"));
        }

        plugin.getServer().getScheduler().cancelTask(taskId);

        if (reset) {
            seconds = 0;
            minutes = 0;
            hours = 0;
        }
    }

    public String getFormattedTime() {
        return (hours < 10 ? "0" + hours : String.valueOf(hours)) + ":" +
                (minutes < 10 ? "0" + minutes : String.valueOf(minutes)) + ":" +
                (seconds < 10 ? "0" + seconds : String.valueOf(seconds));
    }

    public int getTaskId() {
        return taskId;
    }

    public boolean isRunning() {
        return running;
    }
}
