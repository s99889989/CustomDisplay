package com.daxton.customdisplay.listener;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

import java.util.UUID;

public class EntityListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        UUID uuid = e.getEntity().getUniqueId();
        LivingEntity target = e.getEntity();


        if(ActionManager.getJudgment_Holographic_Map().get(uuid.toString()) != null){
            ActionManager.getJudgment_Holographic_Map().get(uuid.toString()).deleteHD();
        }
        action(uuid,target);
    }

    public void action(UUID uuid,LivingEntity target){
        if(ActionManager.target_getPlayer_Map.get(uuid) != null){
            Player player = ActionManager.target_getPlayer_Map.get(uuid);
            UUID playerUUID = player.getUniqueId();
            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
            if(playerData != null){
                for(String string : playerData.getPlayerActionList()){
                    if(string.toLowerCase().contains("~ondeath")){
                        new JudgmentAction().executeOneTwo(player,target,string,String.valueOf((int)(Math.random()*100000)));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event){
        final Entity entity = event.getEntity();


    }

}
