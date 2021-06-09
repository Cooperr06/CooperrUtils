package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PerformCommand implements CommandExecutor, TabCompleter {

    private final CooperrUtils plugin;

    public PerformCommand(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getCommand("perform").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length < 2) {
            sendUsageMessage(sender);
            return true;
        }

        Player executor = null;

        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("as")) {

                executor = plugin.getServer().getPlayer(args[i + 1]);

                if (executor == null || !executor.isOnline()) {
                    sendUsageMessage(sender);
                    return true;
                }

            } else if (args[i].equals("run")) {

                StringBuilder stringBuilder = new StringBuilder().append("/");

                for (int j = i + 1; j < args.length; j++) {
                    stringBuilder.append(args[j]).append((i == j - 1 ? "" : " "));
                }

                plugin.getServer().dispatchCommand((executor == null ? sender : executor), stringBuilder.toString());
                return true;
            }
        }

        sendUsageMessage(sender);
        return true;
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage(Component.text("Usage: /perform [as player] [at player] <run> <command> [args...]", NamedTextColor.DARK_RED));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
