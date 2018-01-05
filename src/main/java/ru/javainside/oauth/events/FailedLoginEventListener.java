package ru.javainside.oauth.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class FailedLoginEventListener implements Listener {

    private Plugin plugin;

    public FailedLoginEventListener(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onFailedLogin(FailedLoginEvent event) {
        plugin.getLogger().log(Level.SEVERE, event.getOauth().getError());
        plugin.getLogger().log(Level.SEVERE, event.getOauth().getErrorDescription());
        event.getPlayer().kickPlayer(event.getOauth().getErrorDescription());
    }
}
