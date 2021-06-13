package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.RenderType;
import org.bukkit.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class HeartboardCommand implements CommandExecutor, TabCompleter {

    private final CooperrUtils plugin;

    public HeartboardCommand(CooperrUtils plugin) {
        this.plugin = plugin;

        PluginCommand command = plugin.getCommand("heartboard");
        if (command == null) {
            plugin.getLogger().log(Level.SEVERE, "Cannot register command \"heartboard\"");
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length != 1) {
            sendUsageMessage(sender);
            return true;
        }

        Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getMainScoreboard();

        if (args[0].equals("on")) {

            if (scoreboard.getObjective("heartboard") != null) {
                sender.sendMessage(Component.text("Das Heartboard ist bereits an!", NamedTextColor.DARK_RED));
                return true;
            }

            Objective objective = scoreboard.registerNewObjective("heartboard", "health", Component.text("Heartboard"), RenderType.HEARTS);
            objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);

            for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                objective.getScore(onlinePlayer.getUniqueId().toString()).setScore((int) (Math.round(onlinePlayer.getHealth() * 10D) / 10D));
            }
            return true;

        } else if (args[0].equals("off")) {

            if (scoreboard.getObjective("heartboard") == null) {
                sender.sendMessage(Component.text("Das Heartboard ist bereits aus!", NamedTextColor.DARK_RED));
                return true;
            }

            scoreboard.getObjective("heartboard").unregister();
            plugin.getServer().broadcast(Component.text("Das ", NamedTextColor.GRAY)
                    .append(Component.text("Heartboard ", NamedTextColor.BLUE))
                    .append(Component.text("ist jetzt an!", NamedTextColor.GRAY)), "cooperrutils.default");
            return true;

        } else {
            sendUsageMessage(sender);
            return true;
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage(Component.text("Usage: /heartboard <action>", NamedTextColor.DARK_RED));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        final List<String> tabCompletion = new ArrayList<>();

        if (args.length == 0) {

            tabCompletion.addAll(Arrays.asList("on", "off"));

            Collections.sort(tabCompletion);
            return tabCompletion;

        } else if (args.length == 1) {

            tabCompletion.addAll(Arrays.asList("on", "off"));

            tabCompletion.removeIf(s -> !s.startsWith(args[0]));

            Collections.sort(tabCompletion);
            return tabCompletion;
        }
        return null;
    }
}
