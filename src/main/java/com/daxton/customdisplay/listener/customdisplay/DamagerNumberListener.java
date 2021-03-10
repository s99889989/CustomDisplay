package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.entity.EntityFind;
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

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class DamagerNumberListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onPhysicalDamageListener(PhysicalDamageEvent event){

        if(!(event.getTarget() instanceof LivingEntity) || event.getTarget().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getTarget())){
                return;
            }
        }

        double damageNumber = event.getDamage();
        LivingEntity target = event.getTarget();
        Player player = Convert.convertPlayer(event.getDamager());
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            String tUUIDSTring = target.getUniqueId().toString();
            String damageType = event.getDamageType();

            if(damageType.contains("PHYSICAL_MISS")){
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","Miss");
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,"Miss");
                new PlayerTrigger(player).onAtkMiss(player,target);
                return;
            }
            if(damageType.contains("PHYSICAL_BLOCK")){
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","BLOCK");
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,"BLOCK");
                new PlayerTrigger(player).onAtkMiss(player,target);
                return;
            }
            if(damageType.contains("PHYSICAL_CRITICAL")){
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,String.valueOf(damageNumber));
                new PlayerTrigger(player).onCrit(player,target);
                return;
            }
            if(damageType.contains("PHYSICAL_ATTACK")){
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,String.valueOf(damageNumber));
                new PlayerTrigger(player).onAttack(player,target);
            }
            if(damageType.contains("MAGIC_ATTACK")){
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,String.valueOf(damageNumber));
                new PlayerTrigger(player).onMagic(player,target);
            }

        }

    }

}
