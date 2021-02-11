package com.daxton.customdisplay.listener.mythicLib;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.citizensnpcs.api.CitizensAPI;
import io.lumine.mythic.lib.api.event.PlayerAttackEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class CDMythicLibListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target;

    private String damageType = "";

    private boolean crit = false;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }

        target = (LivingEntity) event.getEntity();
        double damageNumber = event.getFinalDamage();
        player = EntityFind.convertPlayer(event.getDamager());
        if(player != null){

            String uuidString = player.getUniqueId().toString();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));

            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","Miss");
                new PlayerTrigger(player).onAtkMiss(player,target);
                return;
            }
            if(damageType.contains("PHYSICAL")){
                if(crit){
                    new PlayerTrigger(player).onCrit(player,target);
                }else {
                    new PlayerTrigger(player).onAttack(player,target);
                }
            }
            if(damageType.contains("MAGIC")){
                if(crit){
                    new PlayerTrigger(player).onMCrit(player,target);
                }else {
                    new PlayerTrigger(player).onMagic(player,target);
                }
            }

        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void c(PlayerAttackEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }
        crit = event.isCrit();
        damageType = event.getAttack().getTypes().toString();
    }

}
