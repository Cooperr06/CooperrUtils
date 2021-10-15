package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class GamemodeCommand implements CommandExecutor, TabCompleter {

    private final CooperrUtils plugin;

    public GamemodeCommand(CooperrUtils plugin) {
        this.plugin = plugin;

        PluginCommand command = plugin.getCommand("gamemode");
        if (command == null) {
            plugin.getLogger().log(Level.SEVERE, "Cannot register command \"gamemode\"");
            return;
        }
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {

            if (!(sender instanceof Player)) {
                plugin.sendWrongSenderMessage(sender);
                return true;
            }

            Player player = (Player) sender;

            analyseMode(player, args[0]);
            return true;

        } else if (args.length == 2) {

            if (args[1].equals("@a")) {
                for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
                    analyseMode(onlinePlayer, args[0]);
                }
                return true;
            }

            Player target = plugin.getServer().getPlayer(args[0]);

            if (target == null || !target.isOnline()) {
                sendUsageMessage(sender);
                return true;
            }

            analyseMode(target, args[0]);
            return true;

        } else {
            sendUsageMessage(sender);
            return true;
        }
    }

    private void analyseMode(Player player, String mode) {
        switch (mode) {
            case "survival", "0" -> {
                player.setGameMode(GameMode.SURVIVAL);
                plugin.getServer().broadcast(Component.text(player.getName() + " ist jetzt im Survival Mode!",
                        NamedTextColor.DARK_GRAY, TextDecoration.OBFUSCATED), "minecraft.command.gamemode");
            }
            case "creative", "1" -> {
                player.setGameMode(GameMode.CREATIVE);
                plugin.getServer().broadcast(Component.text(player.getName() + " ist jetzt im Creative Mode!",
                        NamedTextColor.DARK_GRAY, TextDecoration.OBFUSCATED), "minecraft.command.gamemode");
            }
            case "adventure", "2" -> {
                player.setGameMode(GameMode.ADVENTURE);
                plugin.getServer().broadcast(Component.text(player.getName() + " ist jetzt im Adventure Mode!",
                        NamedTextColor.DARK_GRAY, TextDecoration.OBFUSCATED), "minecraft.command.gamemode");
            }
            case "spectator", "3" -> {
                player.setGameMode(GameMode.SPECTATOR);
                plugin.getServer().broadcast(Component.text(player.getName() + " ist jetzt im Spectator Mode!",
                        NamedTextColor.DARK_GRAY, TextDecoration.OBFUSCATED), "minecraft.command.gamemode");
            }
            default -> sendUsageMessage(player);
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage(Component.text("Usage: /gamemode <mode> [player | @a]", NamedTextColor.DARK_RED));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        final List<String> tabCompletion = new ArrayList<>();

        if (args.length == 0) {

            tabCompletion.addAll(Arrays.asList("survival", "creative", "adventure", "spectator", "0", "1", "2", "3"));

            Collections.sort(tabCompletion);
            return tabCompletion;

        } else if (args.length == 1) {

            if (args[0].equals("survival") || args[0].equals("creative") || args[0].equals("adventure") || args[0].equals("spectator") ||
                    args[0].equals("0") || args[0].equals("1") || args[0].equals("2") || args[0].equals("3")) {

                plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));

            } else {
                tabCompletion.addAll(Arrays.asList("survival", "creative", "adventure", "spectator", "0", "1", "2", "3"));
                tabCompletion.removeIf(s -> !s.startsWith(args[0]));
            }

            Collections.sort(tabCompletion);
            return tabCompletion;

        } else if (args.length == 2) {

            plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> tabCompletion.add(onlinePlayer.getName()));

            tabCompletion.removeIf(s -> !s.startsWith(args[0]));

            Collections.sort(tabCompletion);
            return tabCompletion;
        }
        return null;
    }
}
