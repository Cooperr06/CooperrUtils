package de.cooperr.cooperrutils;

import de.cooperr.cooperrutils.command.*;
import de.cooperr.cooperrutils.listener.*;
import de.cooperr.cooperrutils.util.Timer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

public final class CooperrUtils extends JavaPlugin {

    private Timer timer;

    @Override
    public void onEnable() {

        timer = new Timer(this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        saveConfig();

        getConfig().options().header("Configuration File for Plugin " + getName() + " version " + getDescription().getVersion() + "!");

        getConfig().createSection("settings");
        getConfig().createSection("positions");

        getConfig().addDefault("bungeecord", false);
        getConfig().getConfigurationSection("settings").addDefault("damage-indicator", false);
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
        new HealFeedCommand(this);
        new ResetCommand(this);
        new TimerCommand(this);
        new GamemodeCommand(this);
        new HeartboardCommand(this);
        new PerformCommand(this);
        new PositionCommand(this);
    }

    private void registerListeners() {
        new PlayerJoinListener(this);
        new PlayerQuitListener(this);
        new AsyncChatListener(this);
        new EntityDamageListener(this);
        new EntityRegainHealthListener(this);
    }

    public void sendWrongSenderMessage(CommandSender sender) {
        sender.sendMessage(Component.text("Du musst ein Spieler sein, um diesen Befehl nutzen zu können!", NamedTextColor.DARK_RED));
    }

    public Timer getTimer() {
        return timer;
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
