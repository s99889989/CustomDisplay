package com.daxton.customdisplay.listener.bukkit;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;


import java.util.UUID;

import static org.bukkit.entity.EntityType.*;

public class AttackListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player = null;

    private LivingEntity target;

    private UUID playerUUID;

    private UUID targetUUID;


    @EventHandler(priority = EventPriority.MONITOR )
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }
        double damageNumber = event.getFinalDamage();
        target = (LivingEntity) event.getEntity();
        player = Convert.convertPlayer(event.getDamager());
        if(player != null){

            String uuidString = player.getUniqueId().toString();
            String tUUIDString = target.getUniqueId().toString();

            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","Miss");
                PlaceholderManager.cd_Attack_Number.put(uuidString+ tUUIDString,"Miss");

                new PlayerTrigger(player).onTwo(player, target, "~onatkmiss");
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+ tUUIDString,String.valueOf(damageNumber));
                new PlayerTrigger(player).onTwo(player, target, "~onattack");
            }
        }

    }


}
