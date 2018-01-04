package ru.javainside.oauth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import ru.javainside.oauth.commands.LoginCommandExecutor;
import ru.javainside.oauth.events.FailedLoginEventListener;
import ru.javainside.oauth.events.PlayerJoinEventListener;
import ru.javainside.oauth.events.SuccessLoginEventListener;

public class OAuthPlugin extends JavaPlugin {

    private Permission permissions;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        permissions = getServer().getServicesManager().getRegistration(Permission.class).getProvider();
        Config config = new Config(getConfig());
        new PlayerJoinEventListener(this);
        new SuccessLoginEventListener(this);
        new FailedLoginEventListener(this);
        new LoginCommandExecutor(this, config, permissions);
    }

    public Permission getPermissions() {
        return permissions;
    }
}
