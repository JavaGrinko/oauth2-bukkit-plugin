package ru.javainside.oauth.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class FailedLoginEventListener implements Listener {

    public FailedLoginEventListener(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onFailedLogin(FailedLoginEvent event) {
        event.getPlayer().kickPlayer(event.getOauth().getErrorDescription());
    }
}
