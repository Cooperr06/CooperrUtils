package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
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

public class InventoryCommand implements CommandExecutor, TabCompleter {

    private final CooperrUtils plugin;

    public InventoryCommand(CooperrUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            plugin.sendWrongSenderMessage(sender);
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            sendUsageMessage(sender);
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);

        if (target == null || !target.isOnline()) {
            sendUsageMessage(sender);
            return true;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            player.sendMessage("§4Du kannst nicht dein eigenes Inventar öffnen!");
            return true;
        }

        player.openInventory(target.getInventory());
        return true;
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage("§4Usage: /inventory <player>");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        final List<String> tabCompletion = new ArrayList<>();

        if (!(sender instanceof Player)) {
            return null;
        }

        Player player = (Player) sender;

        if (args.length == 0) {

            plugin.getServer().getOnlinePlayers().stream()
                    .filter(onlinePlayer -> !onlinePlayer.getUniqueId().equals(player.getUniqueId()))
                    .forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));

            Collections.sort(tabCompletion);
            return tabCompletion;

        } else if (args.length == 1) {

            plugin.getServer().getOnlinePlayers().stream()
                    .filter(onlinePlayer -> !onlinePlayer.getUniqueId().equals(player.getUniqueId()))
                    .forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));

            tabCompletion.removeIf(s -> !s.startsWith(args[0]));

            Collections.sort(tabCompletion);
            return tabCompletion;

        }
        return null;
    }
}
