package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.player.DamageFormula;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class RangePhysicalDamageListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler(priority = EventPriority.LOW)
    public void onPhysical(PhysicalDamageEvent event) {
        if(!(event.getDamager() instanceof Arrow)){
            return;
        }
        Player player = EntityFind.convertPlayer(event.getDamager());
        if(player != null){
            String uuidString = player.getUniqueId().toString();
            File customCoreFile = new File(cd.getDataFolder(),"Class/CustomCore.yml");
            FileConfiguration customCoreConfig = YamlConfiguration.loadConfiguration(customCoreFile);
            LivingEntity target = event.getTarget();

            /**攻速**/
            boolean attack_speed = new DamageFormula().setAttackSpeed(player,target,customCoreConfig,uuidString);
            if(attack_speed){
                event.setCancelled(true);
                return;
            }

            /**命中**/
            boolean hit = new DamageFormula().setHitRate(player,target,customCoreConfig);
            if(!(hit)){
                event.setDamageType("PHYSICAL_MISS");
                event.setCancelled(true);
                return;
            }

            /**目標格檔**/
            boolean block = new DamageFormula().setBlockRate(player,target,customCoreConfig);
            if(block){
                event.setDamageType("PHYSICAL_BLOCK");
                event.setCancelled(true);
                return;
            }

            /**爆擊**/
            boolean crit = new DamageFormula().setCritChange(player,target,customCoreConfig);
            double attackNumber = 0;
            if(crit){
                event.setDamageType("PHYSICAL_CRITICAL");
                attackNumber = new DamageFormula().setRangePhysicalCriticalDamageNumber(player,target,customCoreConfig);
                event.setDamage(attackNumber);
                return;
            }

            /**目標迴避**/
            boolean dodge = new DamageFormula().setDodgeRate(player,target,customCoreConfig);
            if(dodge){
                event.setDamageType("PHYSICAL_MISS");
                event.setCancelled(true);
                return;
            }

            /**普通攻擊**/
            event.setDamageType("PHYSICAL_ATTACK");
            attackNumber = new DamageFormula().setRangePhysicalDamageNumber(player,target,customCoreConfig);
            event.setDamage(attackNumber);

        }


    }


}
