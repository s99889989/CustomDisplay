package com.daxton.customdisplay.listener.mythicLib;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.entity.EntityFind;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.citizensnpcs.api.CitizensAPI;
import io.lumine.mythic.lib.api.player.MMOPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import io.lumine.mythic.lib.api.event.PlayerAttackEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;


import static io.lumine.mythic.lib.api.stat.SharedStat.*;
import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class MythicLibListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private UUID playerUUID;

    private UUID targetUUID;

    private double damageNumber = 0;

    private double damageNumberPAE = 0.0;

    private String damageType = "";

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
        damageNumber = event.getFinalDamage();
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



            /**基礎傷害**/
            double attack_damage = MMOPlayerData.get(playerUUID).getStatMap().getStat(ATTACK_DAMAGE);
            /**額外物理攻擊**/
            double physical_damage = MMOPlayerData.get(playerUUID).getStatMap().getStat(PHYSICAL_DAMAGE);
            physical_damage = (attack_damage/100)*physical_damage;
            /**爆擊增幅**/
            double physical_STRIKE_POWER = MMOPlayerData.get(playerUUID).getStatMap().getStat(CRITICAL_STRIKE_POWER);
            physical_STRIKE_POWER = (attack_damage+physical_damage)*((physical_STRIKE_POWER+180)/100);
//            player.sendMessage("實際: "+damageNumber);
//            player.sendMessage("預估:" +physical_STRIKE_POWER);



            if(damageType.contains("PHYSICAL")){
                if(damageNumber > physical_STRIKE_POWER ){
                    new PlayerTrigger(player).onCrit(player,target);
                }else {
                    new PlayerTrigger(player).onAttack(player,target);
                }
            }
            if(damageType.contains("MAGIC")){
                new PlayerTrigger(player).onMagic(player,target);
            }

        }

    }

    @EventHandler
    public void attack(PlayerAttackEvent event){
        if (event.isCancelled()) {
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
