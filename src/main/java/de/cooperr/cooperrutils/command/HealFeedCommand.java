package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.attribute.Attribute;
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

public class HealFeedCommand implements CommandExecutor, TabCompleter {

    private final CooperrUtils plugin;

    public HealFeedCommand(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getCommand("healfeed").setExecutor(this);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                plugin.sendWrongSenderMessage(sender);
                return true;
            }

            Player player = (Player) sender;

            player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            player.setFoodLevel(20);

            player.sendMessage(Component.text("Du hast dich geheilt!", NamedTextColor.GRAY));

            return true;

        } else if (args.length == 1) {

            if (args[0].equals("@a")) {

                for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {

                    onlinePlayer.setHealth(onlinePlayer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                    onlinePlayer.setFoodLevel(20);

                    onlinePlayer.sendMessage(Component.text("Alle wurden von ", NamedTextColor.GRAY)
                            .append(Component.text(sender.getName(), NamedTextColor.BLUE))
                            .append(Component.text(" geheilt!", NamedTextColor.GRAY)));
                }
                return true;
            }

            Player target = plugin.getServer().getPlayer(args[0]);

            if (target == null || !target.isOnline()) {
                sendUsageMessage(sender);
                return true;
            }

            target.setHealth(target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
            target.setFoodLevel(20);

            sender.sendMessage(Component.text("Du hast ", NamedTextColor.GRAY)
                    .append(Component.text(target.getName(), NamedTextColor.BLUE))
                    .append(Component.text(" geheilt!", NamedTextColor.GRAY)));
            target.sendMessage(Component.text("§7Du wurdest von §9" + sender.getName() + " geheilt§7!"));

            return true;

        } else {
            sendUsageMessage(sender);
            return true;
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage(Component.text("Usage: /healfeed [player | @a]", NamedTextColor.DARK_RED));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        final List<String> tabCompletion = new ArrayList<>();

        if (args.length == 0) {

            plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));
            tabCompletion.add("@a");

            Collections.sort(tabCompletion);
            return tabCompletion;

        } else if (args.length == 1) {

            plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));
            tabCompletion.add("@a");

            tabCompletion.removeIf(s -> !s.startsWith(args[0]));

            Collections.sort(tabCompletion);
            return tabCompletion;
        }
        return null;
    }
}
