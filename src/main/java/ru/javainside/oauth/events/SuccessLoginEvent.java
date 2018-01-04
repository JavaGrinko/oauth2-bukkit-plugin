package ru.javainside.oauth.events;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import ru.javainside.oauth.model.OAuthResponse;
import net.milkbowl.vault.permission.Permission;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class SuccessLoginEvent extends Event {
    private final static HandlerList handlers = new HandlerList();
    private OAuthResponse oauth;
    private Player player;

    public SuccessLoginEvent(OAuthResponse oauth, Player player) {
        this.oauth = oauth;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
