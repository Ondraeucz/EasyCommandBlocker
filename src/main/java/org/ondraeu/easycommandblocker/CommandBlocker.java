package org.ondraeu.easycommandblocker;

import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.ondraeu.easycommandblocker.Commands.reload;

public final class CommandBlocker extends JavaPlugin implements Listener {
    private static CommandBlocker instance;
    public CommandBlocker() {
    }

    public void onEnable() {
        this.getLogger().info("------------------------------------------------");
        this.getLogger().info("EasyCommandBlocker 1.0-SNAPSHOT version - ENABLE");
        this.getLogger().info("------------------------------------------------");
        Bukkit.getPluginManager().registerEvents(this, this);
        instance = this;
        this.saveDefaultConfig();
        this.getCommand("easycommandblocker").setExecutor(new reload());
    }

    public void onDisable() {
        this.getLogger().info("------------------------------------------------");
        this.getLogger().info("EasyCommandBlocker 1.0-SNAPSHOT version - DISABLE");
        this.getLogger().info("------------------------------------------------");{
        HandlerList.unregisterAll((Listener) this);
    }
    }
    public static CommandBlocker getInstance() {
        return instance;
    }

    @EventHandler
    private void onSend(PlayerCommandSendEvent event) {
        if (!event.getPlayer().hasPermission("ecb.bypass")) {
            if (this.getConfig().getBoolean("block_all_commands_send")) {
                event.getCommands().clear();
            } else {
                this.getConfig().getStringList("blocked_commands").forEach((s) -> {
                    event.getCommands().remove(s);
                });
            }
        }
    }

    @EventHandler
    private void onProcess(PlayerCommandPreprocessEvent event) {
        if (!event.getPlayer().hasPermission("ecb.bypass")) {
            String msg = ChatColor.translateAlternateColorCodes('&', (String)Objects.requireNonNull(this.getConfig().getString("Message")));
            this.getConfig().getStringList("blocked_commands").stream().filter((s) -> {
                return event.getMessage().toLowerCase().split(" ")[0].equals("/" + s);
            }).forEach((s) -> {
                event.setCancelled(true);
                event.getPlayer().sendMessage(msg);
            });
        }
    }
}
