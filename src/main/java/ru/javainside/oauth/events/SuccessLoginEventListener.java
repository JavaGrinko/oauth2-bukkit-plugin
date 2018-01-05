package ru.javainside.oauth.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.javainside.oauth.OAuthPlugin;
import ru.javainside.oauth.model.Authority;
import ru.javainside.oauth.model.Messages;

import java.util.List;

public class SuccessLoginEventListener implements Listener {

    private final OAuthPlugin plugin;

    public SuccessLoginEventListener(OAuthPlugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSuccessLogin(SuccessLoginEvent event) {
        event.getPlayer().setWalkSpeed(0.2f);
        List<Authority> authorities = event.getOauth().getAuthorities();
        if (authorities != null) {
            for (Authority authority : authorities) {
                plugin.getPermissions().playerAddGroup(event.getPlayer(), authority.getAuthority());
            }
        }
        event.getPlayer().sendMessage(Messages.getMessage("login-success"));
    }
}
