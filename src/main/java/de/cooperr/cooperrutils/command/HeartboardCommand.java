package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
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

public class HeartboardCommand implements CommandExecutor, TabCompleter {

    private final CooperrUtils plugin;

    public HeartboardCommand(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getCommand("heartboard").setExecutor(this);
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
                sender.sendMessage("§4Das Heartboard ist bereits an!");
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
                sender.sendMessage("§4Das Heartboard ist bereits aus!");
                return true;
            }

            scoreboard.getObjective("heartboard").unregister();
            plugin.getServer().broadcast(Component.text("§7Das §9Heartboard §7ist jetzt §9an§7!"), "cooperrutils.default");
            return true;

        } else {
            sendUsageMessage(sender);
            return true;
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage("§4Usage: /heartboard <action>");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String alias, @NotNull String[] args) {

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
