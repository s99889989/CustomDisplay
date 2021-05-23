package com.daxton.customdisplay.listener.customcore;

import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class NumberAttack implements Listener {

    //攻擊監聽
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPhysicalDamageListener(PhysicalDamageEvent event){

        if(event.getTarget().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getTarget())){
                return;
            }
        }

        if(event.getDamager() instanceof Player){

            double damageNumber = event.getDamage();
            LivingEntity target = event.getTarget();
            Player player = (Player) event.getDamager();

            String uuidString = player.getUniqueId().toString();
            String tUUIDSTring = target.getUniqueId().toString();
            String damageType = event.getDamageType();

            if(damageType.contains("PHYSICAL_MISS")){
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","Miss");
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,"Miss");
                PlayerTrigger.onPlayer(player, target, "~onatkmiss");
                return;
            }
            if(damageType.contains("PHYSICAL_BLOCK")){
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","BLOCK");
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,"BLOCK");
                PlayerTrigger.onPlayer(player, target, "~onatkmiss");
                return;
            }
            if(damageType.contains("PHYSICAL_CRITICAL")){
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,String.valueOf(damageNumber));
                PlayerTrigger.onPlayer(player, target, "~oncrit");
                return;
            }
            if(damageType.contains("Melee_ATTACK")){
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,String.valueOf(damageNumber));
                PlayerTrigger.onPlayer(player, target, "~onattack");
                return;
            }
            if(damageType.contains("RANGE_ATTACK")){
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,String.valueOf(damageNumber));
                PlayerTrigger.onPlayer(player, target, "~onattack");
                return;
            }
            if(damageType.contains("MAGIC_ATTACK")){
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,String.valueOf(damageNumber));
                PlayerTrigger.onPlayer(player, target, "~onmagic");
            }

        }



    }


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

        double damageNumber = event.getFinalDamage();
        LivingEntity target = (LivingEntity) event.getEntity();
        Player player = Convert.convertPlayer2(event.getDamager());

        if(player != null){
            if(target.getCustomName() != null && target.getCustomName().equals("ModleEngine")){
                return;
            }

            String uuidString = player.getUniqueId().toString();
            String tUUIDString = target.getUniqueId().toString();

            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","Miss");
                PlaceholderManager.cd_Attack_Number.put(uuidString+ tUUIDString,"Miss");
                PlayerTrigger.onPlayer(player, target, "~onatkmiss");
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+ tUUIDString,String.valueOf(damageNumber));
                PlayerTrigger.onPlayer(player, target, "~onattack");
            }


        }



    }

}
