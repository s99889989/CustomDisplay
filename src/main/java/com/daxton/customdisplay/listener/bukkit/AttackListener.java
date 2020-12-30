package com.daxton.customdisplay.listener.bukkit;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.UUID;

import static org.bukkit.entity.EntityType.*;

public class AttackListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player = null;

    private LivingEntity target;

    private UUID playerUUID;

    private UUID targetUUID;


    @EventHandler(
            ignoreCancelled = true,
            priority = EventPriority.HIGHEST
    )
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
        player = EntityFind.convertPlayer(event.getDamager());
        if(player != null){


//            ItemStack itemStack = player.getEquipment().getItemInMainHand();
//
//            if(itemStack.hasItemMeta()){
//                if(itemStack.getLore().size() > 0){
//                    for(String s : itemStack.getLore()){
//                        player.sendMessage(s);
//                    }
//                }
//            }

            String uuidString = player.getUniqueId().toString();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
            new PlayerTrigger(player).onAttack(player,target);
        }

    }


}
