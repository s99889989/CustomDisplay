package com.daxton.customdisplay.listener.attributeplus;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onAttack(AttrEntityDamageEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }
        target = (LivingEntity) event.getEntity();
        double damageNumber = event.getDamage();
        if(event.getDamager() instanceof Player){
            player = ((Player) event.getDamager()).getPlayer();
            String uuidString = player.getUniqueId().toString();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
            new PlayerTrigger(player).onAttack(player,target);

        }else {
            return;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAttackCrit(AttrEntityCritEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND || event.getEntity() instanceof NPC){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }
        target = (LivingEntity) event.getEntity();
        double damageNumber = event.getCritDamage();
        if(event.getDamager() instanceof Player){
            player = ((Player) event.getDamager()).getPlayer();
            String uuidString = player.getUniqueId().toString();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
            new PlayerTrigger(player).onCrit(player,target);
        }else {
            return;
        }
    }

}
