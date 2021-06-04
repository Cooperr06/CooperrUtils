package de.cooperr.cooperrutils;

import de.cooperr.cooperrutils.command.*;
import de.cooperr.cooperrutils.listener.AsyncChatListener;
import de.cooperr.cooperrutils.listener.PlayerJoinListener;
import de.cooperr.cooperrutils.listener.PlayerQuitListener;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

public final class CooperrUtils extends JavaPlugin {

    @Override
    public void onEnable() {

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        saveConfig();

        getConfig().addDefault("settings.damage-indicator", false);
        getConfig().addDefault("bungeecord", false);

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
        new ResetCommand(this);
    }

    private void registerListeners() {
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new AsyncChatListener(this);
    }

    public void sendWrongSenderMessage(CommandSender sender) {
        sender.sendMessage("§4Du musst ein Spieler sein, um diesen Befehl nutzen zu können!");
    }

    public void sendToBungeeCord(Player player, String message, String... args) {

        if (!getConfig().getBoolean("bungeecord")) {
            return;
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
            dataOutputStream.write(message.getBytes());
            for (String arg : args) {
                dataOutputStream.write(arg.getBytes());
            }
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "An error occurred while sending to BungeeCord", e);
            return;
        }

        player.sendPluginMessage(this, "BungeeCord", byteArrayOutputStream.toByteArray());
    }
}
