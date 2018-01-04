package ru.javainside.oauth.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class PlayerJoinEventListener implements Listener {

    private final Plugin plugin;

    public PlayerJoinEventListener(Plugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        plugin.getLogger().info("Player " + event.getPlayer().getName());
        event.getPlayer().sendMessage("Hello, " + event.getPlayer().getName() + "!");
        event.getPlayer().sendMessage("/login - enter your password from site minejs.ru");
    }
}
