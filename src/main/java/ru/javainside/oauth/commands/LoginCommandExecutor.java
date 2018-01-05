package ru.javainside.oauth.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
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

import java.io.IOException;

public class LoginCommandExecutor implements CommandExecutor {

    private final Config config;
    private final OkHttpClient client = new OkHttpClient();

    public LoginCommandExecutor(JavaPlugin plugin, Config config) {
        this.config = config;
        plugin.getCommand("login").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        try {
            if (!(sender instanceof Player) && args.length < 1)
                return false;
            player = (Player) sender;
            String password = args[0];
            RequestBody formBody = new FormBody.Builder()
                    .add("grant_type", "password")
                    .add("client_id", config.getClientId())
                    .add("client_secret", config.getClientSecret())
                    .add("username", player.getName())
                    .add("password", password)
                    .build();
            Request request = new Request.Builder()
                    .url(config.getBaseUrl() + config.getGrantPath())
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            Gson gson = new GsonBuilder().create();
            OAuthResponse oauth = gson.fromJson(response.body().string(), OAuthResponse.class);
            if (oauth.getError() != null) {
                pluginManager.callEvent(FailedLoginEvent.builder()
                        .oauth(oauth)
                        .player(player)
                        .build());
            } else {
                pluginManager.callEvent(SuccessLoginEvent.builder()
                        .oauth(oauth)
                        .player(player)
                        .build());
            }
        } catch (IOException e) {
            pluginManager.callEvent(FailedLoginEvent.builder()
                    .oauth(OAuthResponse.builder()
                            .error(Messages.getMessage("errors.server-not-available"))
                            .errorDescription(Messages.getMessage("errors.server-not-available-description"))
                            .build())
                    .player(player)
                    .build());
            e.printStackTrace();
        }
        return true;
    }
}
