package de.cooperr.cooperrutils.command;

import de.cooperr.cooperrutils.CooperrUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.logging.Level;

public class ResetCommand implements CommandExecutor {

    private final CooperrUtils plugin;

    public ResetCommand(CooperrUtils plugin) {
        this.plugin = plugin;
        plugin.getCommand("reset").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length != 0) {
            sendUsageMessage(sender);
            return true;
        }

        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            plugin.sendToBungeeCord(onlinePlayer, "Connect", "lobby");
        }

        final File[] worlds = {new File(plugin.getServer().getWorldContainer(), "world"),
                new File(plugin.getServer().getWorldContainer(), "world_nether"),
                new File(plugin.getServer().getWorldContainer(), "world_the_end")};

        final String[] worldChildren = {"data", "datapacks", "playerdata", "poi", "region"};

        try {
            for (File world : worlds) {
                Files.walk(world.toPath())
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);

                world.mkdirs();

                for (String worldChild : worldChildren) {
                    new File(world, worldChild).mkdirs();
                }
            }
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred while resetting worlds", e);
            return true;
        }

        plugin.getServer().spigot().restart();
        return true;
    }

    private void sendUsageMessage(CommandSender sender) {
        sender.sendMessage(Component.text("Usage: /reset", NamedTextColor.DARK_RED));
    }
}
