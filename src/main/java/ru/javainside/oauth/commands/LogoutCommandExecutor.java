package ru.javainside.oauth.commands;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.javainside.oauth.OAuthPlugin;

public class LogoutCommandExecutor implements CommandExecutor {
    private OAuthPlugin plugin;

    public LogoutCommandExecutor(OAuthPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("logout").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        Permission permissions = plugin.getPermissions();
        String[] playerGroups = permissions.getPlayerGroups(player);
        for (String playerGroup : playerGroups) {
            permissions.playerRemoveGroup(player, playerGroup);
        }
        plugin.getServer().getPluginManager().callEvent(
                new PlayerJoinEvent(player, "oauth2 logout"));
        return true;
    }
}
