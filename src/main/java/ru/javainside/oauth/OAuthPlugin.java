package ru.javainside.oauth;

import lombok.Getter;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import ru.javainside.oauth.commands.LoginCommandExecutor;
import ru.javainside.oauth.commands.LogoutCommandExecutor;
import ru.javainside.oauth.events.FailedLoginEventListener;
import ru.javainside.oauth.events.PlayerJoinEventListener;
import ru.javainside.oauth.events.SuccessLoginEventListener;
import ru.javainside.oauth.model.Messages;


public class OAuthPlugin extends JavaPlugin {
    
    @Getter
    private Permission permissions;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        permissions = getServer().getServicesManager().getRegistration(Permission.class).getProvider();
        Config config = new Config(getConfig());
        new Messages(this);
        new PlayerJoinEventListener(this);
        new SuccessLoginEventListener(this);
        new FailedLoginEventListener(this);
        new LoginCommandExecutor(this, config);
        new LogoutCommandExecutor(this);
    }
}
