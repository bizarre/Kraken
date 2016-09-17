package com.alexandeh.kraken.tab.event;

import com.alexandeh.kraken.tab.PlayerTab;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerTabRemoveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private PlayerTab playerTab;
    private Player player;

    public PlayerTabRemoveEvent(PlayerTab playerTab) {
        this.player = playerTab.getPlayer();
        this.playerTab = playerTab;
    }
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
