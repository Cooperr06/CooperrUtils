package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class FlyCommand implements CommandExecutor, TabCompleter {

    private final CooperrUtils plugin;

    public FlyCommand(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getCommand("fly").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                plugin.sendWrongSenderMessage(sender);
                return true;
            }

            Player player = (Player) sender;

            player.setAllowFlight(!player.getAllowFlight());
            return true;

        } else if (args.length == 1) {

            Player target = plugin.getServer().getPlayer(args[0]);

            if (target == null || !target.isOnline()) {
                sendUsageMessage(sender);
                return true;
            }

            target.setAllowFlight(!target.getAllowFlight());
            return true;

        } else {

            sendUsageMessage(sender);
            return true;

        }
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage("§4Usage: /fly [player]");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        final List<String> tabCompletion = new ArrayList<>();

        if (args.length == 0) {

            plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));

            Collections.sort(tabCompletion);
            return tabCompletion;

        } else if (args.length == 1) {

            plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));

            tabCompletion.removeIf(s -> !s.startsWith(args[0]));

            Collections.sort(tabCompletion);
            return tabCompletion;

        }
        return null;
    }
}
