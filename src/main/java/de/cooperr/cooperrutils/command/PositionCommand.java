package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class PositionCommand implements CommandExecutor, TabCompleter {

    private final CooperrUtils plugin;
    private final ConfigurationSection section;

    public PositionCommand(CooperrUtils plugin) {
        this.plugin = plugin;

        PluginCommand command = plugin.getCommand("position");
        if (command == null) {
            plugin.getLogger().log(Level.SEVERE, "Cannot register command \"position\"");
            section = null;
            return;
        }

        section = plugin.getConfig().getConfigurationSection("positions");
        if (section == null) {
            plugin.getLogger().log(Level.SEVERE, "Configuration Section \"positions\" does not exist");
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {

            if (args[0].equals("list")) {

                final StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(Component.text("Folgende Positionen sind verfügbar:", NamedTextColor.GRAY));

                section.getKeys(false)
                        .forEach(key -> stringBuilder.append(Component.text("\n- ", NamedTextColor.GRAY)
                                .append(Component.text(key, NamedTextColor.BLUE))));

                sender.sendMessage(stringBuilder.toString());
                return true;

            } else if (args[0].equals("reset")) {

                section.getValues(false).forEach((key, value) -> section.set(key, null));
                plugin.getServer().broadcast(Component.text("Alle Positionen wurden gelöscht!", NamedTextColor.GOLD, TextDecoration.BOLD), "cooperrutils.default");
                return true;

            } else {
                sendUsageMessage(sender);
                return true;
            }

        } else if (args.length == 2) {

            if (!(sender instanceof Player)) {
                plugin.sendWrongSenderMessage(sender);
                return true;
            }

            Player player = (Player) sender;

            switch (args[0]) {

                case "add":

                    section.set(args[1], player.getLocation().getBlock().getLocation());
                    plugin.getServer().broadcast(Component.text("Die Position ", NamedTextColor.GRAY)
                            .append(Component.text(args[1], NamedTextColor.BLUE))
                            .append(Component.text(" wurde erstellt!", NamedTextColor.GRAY)), "cooperrutils.default");
                    return true;

                case "remove":

                    if (section.contains(args[1])) {

                        section.set(args[1], null);
                        plugin.getServer().broadcast(Component.text("Die Position ", NamedTextColor.GRAY)
                                .append(Component.text(args[1], NamedTextColor.BLUE))
                                .append(Component.text(" wurde gelöscht!", NamedTextColor.GRAY)), "cooperrutils.default");

                    } else {
                        player.sendMessage(Component.text("Die Position " + args[1] + " existiert nicht!", NamedTextColor.DARK_RED));
                    }
                    return true;

                case "get":

                    if (section.contains(args[1])) {

                        Location location = section.getLocation(args[1]);
                        assert location != null;

                        player.sendMessage(Component.text(args[1], NamedTextColor.BLUE)
                                .append(Component.text(": ", NamedTextColor.GRAY))
                                .append(Component.text(location.getBlockX() + " " + location.getBlockY() + " " + location.getBlockZ() +
                                        " [" + location.getWorld().getName() + "]", NamedTextColor.BLUE)));

                    } else {
                        player.sendMessage(Component.text("Die Position " + args[1] + " existiert nicht!", NamedTextColor.DARK_RED));
                    }
                    return true;

                default:
                    sendUsageMessage(player);
                    return true;
            }
        } else {
            sendUsageMessage(sender);
            return true;
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage(Component.text("Usage: /position <action> [name]", NamedTextColor.DARK_RED));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        final List<String> tabCompletion = new ArrayList<>();

        if (args.length == 0) {

            tabCompletion.addAll(Arrays.asList("list", "reset", "add", "remove", "get"));

            Collections.sort(tabCompletion);
            return tabCompletion;

        } else if (args.length == 1) {

            if (args[0].equals("list") || args[0].equals("reset") || args[0].equals("add") || args[0].equals("remove") || args[0].equals("get")) {

                if (args[0].equals("list") || args[0].equals("reset") || args[0].equals("add")) {
                    return null;
                } else {
                    tabCompletion.addAll(Arrays.asList(section.getKeys(false).toArray(new String[0])));
                }
            } else {
                tabCompletion.addAll(Arrays.asList("list", "reset", "add", "remove", "get"));
                tabCompletion.removeIf(s -> !s.startsWith(args[0]));
            }

            Collections.sort(tabCompletion);
            return tabCompletion;

        } else if (args.length == 2) {

            if (!args[1].equals("add")) {
                tabCompletion.addAll(Arrays.asList(section.getKeys(false).toArray(new String[0])));
            }

            tabCompletion.removeIf(s -> !s.startsWith(args[0]));

            Collections.sort(tabCompletion);
            return tabCompletion;
        }
        return null;
    }
}
