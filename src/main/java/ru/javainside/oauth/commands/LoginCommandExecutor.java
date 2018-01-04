package ru.javainside.oauth.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.milkbowl.vault.permission.Permission;
import okhttp3.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ru.javainside.oauth.Config;
import ru.javainside.oauth.events.FailedLoginEvent;
import ru.javainside.oauth.events.SuccessLoginEvent;
import ru.javainside.oauth.model.OAuthResponse;

import java.io.IOException;

public class LoginCommandExecutor implements CommandExecutor {

    private final Config config;
    private final Permission permissions;
    private final OkHttpClient client = new OkHttpClient();

    public LoginCommandExecutor(JavaPlugin plugin, Config config, Permission permissions) {
        this.config = config;
        this.permissions = permissions;
        plugin.getCommand("login").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
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
                    .url(config.getBaseUrl())
                    .post(formBody)
                    .build();
            Response response = client.newCall(request).execute();
            Gson gson = new GsonBuilder().create();
            OAuthResponse oauth = gson.fromJson(response.body().string(), OAuthResponse.class);
            if (oauth.getError() != null) {
                Bukkit.getServer().getPluginManager().callEvent(FailedLoginEvent.builder()
                        .oauth(oauth)
                        .player(player)
                        .build());
            } else {
                Bukkit.getServer().getPluginManager().callEvent(SuccessLoginEvent.builder()
                        .oauth(oauth)
                        .player(player)
                        .build());
            }
        } catch (IOException e) {
            Bukkit.getServer().getPluginManager().callEvent(FailedLoginEvent.builder()
                    .oauth(OAuthResponse.builder()
                            .error("Server error")
                            .errorDescription("Try login later")
                            .build())
                    .player(player)
                    .build());
            e.printStackTrace();
        }
        return true;
    }
}
