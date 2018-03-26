package ru.javainside.oauth.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import ru.javainside.oauth.model.Messages;
import ru.javainside.oauth.model.OAuthResponse;

import java.util.logging.Level;

public class FailedLoginEventListener implements Listener {

    private Plugin plugin;

    public FailedLoginEventListener(Plugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onFailedLogin(FailedLoginEvent event) {
        OAuthResponse oauth = event.getOauth();
        if (oauth != null) {
            plugin.getLogger().log(Level.SEVERE, oauth.getError());
            plugin.getLogger().log(Level.SEVERE, oauth.getErrorDescription());
            event.getPlayer().kickPlayer(oauth.getErrorDescription());
        } else {
            event.getPlayer().kickPlayer(Messages.getMessage("error.no-oauth-metadata"));
        }
    }
}
