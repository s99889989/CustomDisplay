package com.daxton.customdisplay.listener.mmolib;

import net.mmogroup.mmolib.api.AttackResult;
import net.mmogroup.mmolib.api.DamageType;
import net.mmogroup.mmolib.api.event.MMOPlayerDataEvent;
import net.mmogroup.mmolib.api.event.PlayerAttackEvent;
import net.mmogroup.mmolib.api.player.MMOPlayerData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerAttackEvent2 extends MMOPlayerDataEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final EntityDamageByEntityEvent event;
    private final AttackResult attack;


    public PlayerAttackEvent2(MMOPlayerData data, EntityDamageByEntityEvent event, AttackResult attack) {
        super(data);
        this.event = event;
        this.attack = attack;
    }

    public boolean isCancelled() {
        return this.event.isCancelled();
    }

    public void setCancelled(boolean value) {
        this.event.setCancelled(value);
    }

    public AttackResult getAttack() {
        return this.attack;
    }

    /** @deprecated */
    @Deprecated
    public boolean isWeapon() {
        return this.attack.getTypes().contains(DamageType.WEAPON);
    }

    public LivingEntity getEntity() {
        return (LivingEntity)this.event.getEntity();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }



}
