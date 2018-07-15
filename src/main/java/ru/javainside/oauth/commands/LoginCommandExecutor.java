package ru.javainside.oauth.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.javainside.oauth.Config;
import ru.javainside.oauth.events.FailedLoginEvent;
import ru.javainside.oauth.events.SuccessLoginEvent;
import ru.javainside.oauth.model.Messages;
import ru.javainside.oauth.model.OAuthResponse;
import ru.javainside.oauth.service.OAuth2Service;

import java.io.IOException;

public class LoginCommandExecutor implements CommandExecutor {

    private final Config config;
    private OAuth2Service oAuth2Service;

    public LoginCommandExecutor(JavaPlugin plugin, Config config) {
        this.config = config;
        plugin.getCommand("login").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        if (sender instanceof Player && args.length == 2) {
            try {
                Player player = (Player) sender;
                String username = args[0];
                String password = args[1];

                OAuthResponse oauth = oAuth2Service.auth(username, password, config.getClientId(), config.getClientSecret(),
                        config.getBaseUrl() + config.getGrantPath());

                if (oauth.getError() != null) {
                    pluginManager.callEvent(FailedLoginEvent.builder()
                            .oauth(oauth)
                            .player(player)
                            .build());
                } else {
                    player.setDisplayName(username);
                    player.setCustomName(username);
                    player.setPlayerListName(username);
                    pluginManager.callEvent(SuccessLoginEvent.builder()
                            .oauth(oauth)
                            .player(player)
                            .build());
                }
                return true;
            } catch (IOException e) {
                pluginManager.callEvent(FailedLoginEvent.builder()
                        .oauth(OAuthResponse.builder()
                                .error(Messages.getMessage("errors.server-not-available"))
                                .errorDescription(Messages.getMessage("errors.server-not-available-description"))
                                .build())
                        .player((Player) sender)
                        .build());
                e.printStackTrace();
            }
        }
        return false;
    }
}
