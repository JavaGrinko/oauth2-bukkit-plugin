package ru.javainside.oauth.events;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import ru.javainside.oauth.OAuthPlugin;
import ru.javainside.oauth.model.Authority;

import java.util.List;

public class SuccessLoginEventListener implements Listener {

    private final OAuthPlugin plugin;

    public SuccessLoginEventListener(OAuthPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSuccessLogin(SuccessLoginEvent event) {
        List<Authority> authorities = event.getOauth().getAuthorities();
        for (Authority authority : authorities) {
            plugin.getPermissions().playerAddGroup(event.getPlayer(), authority.getAuthority());
        }
    }
}
