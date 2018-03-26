package ru.javainside.oauth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
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
        Player player = event.getPlayer();
        player.setWalkSpeed(0.2f);
        player.setMetadata("oauth", new FixedMetadataValue(plugin, event.getOauth()));
        List<Authority> authorities = event.getOauth().getAuthorities();
        if (authorities != null) {
            for (Authority authority : authorities) {
                plugin.getPermissions().playerAddGroup(player, authority.getAuthority());
            }
        }
        player.sendMessage(Messages.getMessage("login-success"));
    }
}
