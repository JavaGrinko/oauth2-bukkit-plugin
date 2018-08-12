package ru.javainside.oauth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import static ru.javainside.oauth.Config.LOGIN_PERMISSION;

public class PlayerJoinEventListener implements Listener {

    private final Plugin plugin;

    public PlayerJoinEventListener(Plugin plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(LOGIN_PERMISSION)) {
            player.setWalkSpeed(0);
        }
    }
}
