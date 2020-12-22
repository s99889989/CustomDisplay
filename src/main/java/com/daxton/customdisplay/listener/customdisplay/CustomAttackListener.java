package com.daxton.customdisplay.listener.customdisplay;

import net.mmogroup.mmolib.MMOLib;
import net.mmogroup.mmolib.api.AttackResult;
import net.mmogroup.mmolib.api.DamageType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomAttackListener extends Event {

    private static final HandlerList handlers = new HandlerList();

    public CustomAttackListener(){
        //MMOLib.plugin.getDamage().damage(data.getPlayer(), target, new AttackResult(damage, new DamageType[]{DamageType.SKILL, DamageType.PROJECTILE, DamageType.MAGIC}));
    }



    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


}
