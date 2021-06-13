package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class FlyCommand implements CommandExecutor, TabCompleter {

    private final CooperrUtils plugin;

    public FlyCommand(CooperrUtils plugin) {
        this.plugin = plugin;

        PluginCommand command = plugin.getCommand("fly");
        if (command == null) {
            plugin.getLogger().log(Level.SEVERE, "Cannot register command \"fly\"");
            return;
        }
        command.setExecutor(this);
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
            player.sendMessage(Component.text("Du kannst jetzt " + (player.getAllowFlight() ? "" : "nicht mehr ") + "fliegen!", NamedTextColor.GRAY));
            return true;

        } else if (args.length == 1) {

            Player target = plugin.getServer().getPlayer(args[0]);

            if (target == null || !target.isOnline()) {
                sendUsageMessage(sender);
                return true;
            }

            target.setAllowFlight(!target.getAllowFlight());
            sender.sendMessage(Component.text(target.getName(), NamedTextColor.BLUE)
                    .append(Component.text(" kann jetzt " + (target.getAllowFlight() ? "" : "nicht mehr ") + "fliegen!", NamedTextColor.GRAY)));
            target.sendMessage(Component.text("Du kannst jetzt " + (target.getAllowFlight() ? "" : "nicht mehr ") + "fliegen!", NamedTextColor.GRAY));
            return true;

        } else {

            sendUsageMessage(sender);
            return true;

        }
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage(Component.text("Usage: /fly [player]", NamedTextColor.DARK_RED));
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
