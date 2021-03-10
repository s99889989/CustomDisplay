package com.daxton.customdisplay.listener.mythicLib;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.entity.EntityFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.sucy.skill.api.event.SkillDamageEvent;
import com.sucy.skill.listener.AttributeListener;
import net.citizensnpcs.api.CitizensAPI;
import io.lumine.mythic.lib.api.event.PlayerAttackEvent;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

import static io.lumine.mythic.lib.api.stat.SharedStat.*;
import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class MythicLib_SkillAPI_Listener extends AttributeListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private UUID playerUUID;

    private UUID targetUUID;

    private double damageNumberPAE = 0.0;

    private String damageType = "";

    @EventHandler(
            priority = EventPriority.MONITOR
    )
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
            String uuidString = player.getUniqueId().toString();
            String tUUIDString = target.getUniqueId().toString();
            double damageNumber = event.getDamage();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
            PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDString,String.valueOf(damageNumber));
            new PlayerTrigger(player).onMagic(player,target);

        }

    }


    @EventHandler(
            priority = EventPriority.MONITOR
    )
    public void onPhysicalDamage(EntityDamageByEntityEvent event){
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
                new PlayerTrigger(player).onAtkMiss(player,target);
                return;
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDString,String.valueOf(damageNumber));
            }

            playerUUID = player.getUniqueId();
            targetUUID = target.getUniqueId();

            /**Base damage**/
            double attack_damage = MMOPlayerData.get(playerUUID).getStatMap().getStat(ATTACK_DAMAGE);
            /**Additional physical attack**/
            double physical_damage = MMOPlayerData.get(playerUUID).getStatMap().getStat(PHYSICAL_DAMAGE);
            physical_damage = (attack_damage/100)*physical_damage;
            /**Critical boost**/
            double physical_STRIKE_POWER = MMOPlayerData.get(playerUUID).getStatMap().getStat(CRITICAL_STRIKE_POWER);
            physical_STRIKE_POWER = (attack_damage+physical_damage)*((physical_STRIKE_POWER+180)/100);
            if(damageType.contains("PHYSICAL")){
                if(damageNumber > physical_STRIKE_POWER ){
                    new PlayerTrigger(player).onCrit(player,target);
                }else {
                    new PlayerTrigger(player).onAttack(player,target);
                }
            }

        }

    }

    @EventHandler
    public void c(PlayerAttackEvent event) {
        if(event.isCancelled()){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }
        damageNumberPAE = event.getAttack().getDamage();
        damageType = event.getAttack().getTypes().toString();
    }


}
