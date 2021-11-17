package com.maria.autovenderminerio.api;

import com.maria.autovenderminerio.ores.Ore;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class MinedOreEvent extends Event implements Cancellable {

    private static HandlerList handlerList = new HandlerList();
    private boolean cancelled;

    private Player player;
    private Ore ore;
    private double price;
    private String bonusVIP;
    private double[] bonus;

    public MinedOreEvent(Player player, Ore ore, double price, String bonusVIP, double[] bonus) {
        this.player = player;
        this.ore = ore;
        this.price = price;
        this.bonusVIP = bonusVIP;
        this.bonus = bonus;

        cancelled = false;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
