package com.daxton.customdisplay.listener.skillapi;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.PlayerTrigger2;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.sucy.skill.api.event.PhysicalDamageEvent;
import com.sucy.skill.api.event.SkillDamageEvent;
import com.sucy.skill.listener.AttributeListener;

import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SkillAPIListener extends AttributeListener implements Listener{

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSkillDamage(SkillDamageEvent event){
        if(event.isCancelled()){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getTarget())){
                return;
            }
        }
        if(event.getDamager() instanceof Player & event.getTarget() instanceof LivingEntity){
            Player player = ((Player) event.getDamager()).getPlayer();
            LivingEntity target = event.getTarget();
            double damageNumber = event.getDamage();
            String uuidString = player.getUniqueId().toString();
            String tUUIDString = target.getUniqueId().toString();
            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","Miss");
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDString,"Miss");
                new PlayerTrigger2(player).onTwo(player, target, "~onatkmiss");
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDString,String.valueOf(damageNumber));
                new PlayerTrigger2(player).onTwo(player, target, "~onmagic");
            }


        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPhysicalDamage(PhysicalDamageEvent event){
        if(event.isCancelled()){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getTarget())){
                return;
            }
        }
        if(event.getDamager() instanceof Player & event.getTarget() instanceof LivingEntity){
            Player player = ((Player) event.getDamager()).getPlayer();
            LivingEntity target = event.getTarget();
            double damageNumber = event.getDamage();
            String uuidString = player.getUniqueId().toString();
            String tUUIDString = target.getUniqueId().toString();
            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","Miss");
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDString,"Miss");
                new PlayerTrigger2(player).onTwo(player, target, "~onatkmiss");
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDString,String.valueOf(damageNumber));
                new PlayerTrigger2(player).onTwo(player, target, "~onattack");
            }


        }



    }


}
