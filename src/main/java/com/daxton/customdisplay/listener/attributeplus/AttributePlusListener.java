package com.daxton.customdisplay.listener.attributeplus;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.serverct.ersha.jd.event.AttrEntityCritEvent;
import org.serverct.ersha.jd.event.AttrEntityDamageEvent;

import java.util.UUID;

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class AttributePlusListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target;

    private UUID playerUUID;

    private UUID targetUUID;

    @EventHandler
    public void onAttack(AttrEntityDamageEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        target = (LivingEntity) event.getEntity();
        double damageNumber = event.getDamage();
        PlaceholderManager.getDamage_Number_Map().put(event.getDamager().getUniqueId(),String.valueOf(damageNumber));
        if(event.getDamager() instanceof Player){
            player = ((Player) event.getDamager()).getPlayer();

            new PlayerTrigger(player).onAttack(player,target);

        }else {
            return;
        }
    }

    @EventHandler
    public void onAttackCrit(AttrEntityCritEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        target = (LivingEntity) event.getEntity();
        double damageNumber = event.getCritDamage();
        if(event.getDamager() instanceof Player){
            player = ((Player) event.getDamager()).getPlayer();

            new PlayerTrigger(player).onCrit(player,target);


        }else {
            return;
        }
    }

}
