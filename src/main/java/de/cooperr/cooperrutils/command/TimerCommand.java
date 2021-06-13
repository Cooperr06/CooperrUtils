package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class TimerCommand implements CommandExecutor, TabCompleter {

    private final CooperrUtils plugin;

    public TimerCommand(CooperrUtils plugin) {
        this.plugin = plugin;

        PluginCommand command = plugin.getCommand("timer");
        if (command == null) {
            plugin.getLogger().log(Level.SEVERE, "Cannot register command \"timer\"");
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

        switch (args[0]) {

            case "start":
                plugin.getTimer().start();
                return true;
            case "stop":
                plugin.getTimer().stop(true);
                return true;
            case "pause":
                plugin.getTimer().stop(false);
                return true;
            default:
                sendUsageMessage(sender);
                return true;
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage(Component.text("Usage: /timer <action>", NamedTextColor.DARK_RED));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        final List<String> tabCompletion = new ArrayList<>();

        if (args.length == 0) {

            tabCompletion.addAll(Arrays.asList("start", "stop", "pause"));

            Collections.sort(tabCompletion);
            return tabCompletion;

        } else if (args.length == 1) {

            tabCompletion.addAll(Arrays.asList("start", "stop", "pause"));

            tabCompletion.removeIf(s -> !s.startsWith(args[0]));

            Collections.sort(tabCompletion);
            return tabCompletion;
        }
        return null;
    }
}
