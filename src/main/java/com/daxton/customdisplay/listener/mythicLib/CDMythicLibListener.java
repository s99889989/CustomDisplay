package com.daxton.customdisplay.listener.mythicLib;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.PlayerTrigger2;
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

        LivingEntity target = (LivingEntity) event.getEntity();
        double damageNumber = event.getFinalDamage();
        player = Convert.convertPlayer(event.getDamager());
        if(player != null){

            String uuidString = player.getUniqueId().toString();
            String tUUIDString = target.getUniqueId().toString();


            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","Miss");
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDString,"Miss");
                new PlayerTrigger2(player).onTwo(player, target, "~onatkmiss");
                return;
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDString,String.valueOf(damageNumber));
            }
            if(damageType.contains("PHYSICAL")){
                if(crit){
                    new PlayerTrigger2(player).onTwo(player, target, "~oncrit");
                }else {
                    new PlayerTrigger2(player).onTwo(player, target, "~onattack");
                }
            }
            if(damageType.contains("MAGIC")){
                if(crit){
                    new PlayerTrigger2(player).onTwo(player, target, "~onmcrit");
                }else {
                    new PlayerTrigger2(player).onTwo(player, target, "~onmagic");
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
