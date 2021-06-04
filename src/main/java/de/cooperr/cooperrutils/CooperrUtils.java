package de.cooperr.cooperrutils;

import de.cooperr.cooperrutils.command.*;
import de.cooperr.cooperrutils.listener.*;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public final class CooperrUtils extends JavaPlugin {

    @Override
    public void onEnable() {

        saveConfig();

        getConfig().addDefault("settings.damage-indicator", false);
        getConfig().options().header("Configuration File for Plugin " + getName() + " version " + getDescription().getVersion() + "!");
        getConfig().options().copyDefaults();

        getServer().getPluginManager().addPermission(new Permission("cooperrutils.default"));

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void registerCommands() {
        new FlyCommand(this);
        new InventoryCommand(this);
        new ClientCommand(this);
        new PingCommand(this);
    }

    private void registerListeners() {
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new AsyncChatListener(this);
    }

    public void sendWrongSenderMessage(CommandSender sender) {
        sender.sendMessage("§4Du musst ein Spieler sein, um diesen Befehl nutzen zu können!");
    }
}
