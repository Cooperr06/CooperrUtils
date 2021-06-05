package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GamemodeCommand implements CommandExecutor, TabCompleter {

    private final CooperrUtils plugin;

    public GamemodeCommand(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getCommand("gamemode").setExecutor(this);
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
            case "survival":
            case "0":
                player.setGameMode(GameMode.SURVIVAL);
                plugin.getServer().broadcast(Component.text("§7§o" + player.getName() + " ist jetzt im Survival Mode!"), "minecraft.command.gamemode");
                return;
            case "creative":
            case "1":
                player.setGameMode(GameMode.CREATIVE);
                plugin.getServer().broadcast(Component.text("§7§o" + player.getName() + " ist jetzt im Creative Mode!"), "minecraft.command.gamemode");
                return;
            case "adventure":
            case "2":
                player.setGameMode(GameMode.ADVENTURE);
                plugin.getServer().broadcast(Component.text("§7§o" + player.getName() + " ist jetzt im Adventure Mode!"), "minecraft.command.gamemode");
                return;
            case "spectator":
            case "3":
                player.setGameMode(GameMode.SPECTATOR);
                plugin.getServer().broadcast(Component.text("§7§o" + player.getName() + " ist jetzt im Spectator Mode!"), "minecraft.command.gamemode");
                return;
            default:
                sendUsageMessage(player);
                break;
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage("§4Usage: /gamemode <mode> [player | @a]");
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