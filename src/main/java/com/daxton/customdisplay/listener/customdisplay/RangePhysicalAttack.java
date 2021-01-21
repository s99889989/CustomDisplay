package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.player.damageformula.FormulaDelay;
import com.daxton.customdisplay.api.player.damageformula.FormulaChance;
import com.daxton.customdisplay.api.player.damageformula.FormulaPhysics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.File;

public class RangePhysicalAttack implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler(priority = EventPriority.LOW)
    public void onPhysical(PhysicalDamageEvent event) {

        String damageType = event.getDamageType();

        if(damageType != null && damageType.equals("RANGE_PHYSICAL_ATTACK") || damageType.equals("SKILL_RANGE_PHYSICAL_ATTACK")) {

            if (!(event.getDamager() instanceof Arrow)) {
                return;
            }

            Player player = EntityFind.convertPlayer(event.getDamager());
            if (player != null) {
                String uuidString = player.getUniqueId().toString();

                LivingEntity target = event.getTarget();

                /**攻速**/
                boolean attack_speed = new FormulaDelay().setAttackSpeed(player, target, uuidString);
                if (attack_speed) {
                    event.setCancelled(true);
                    return;
                }

                /**命中**/
                boolean hit = new FormulaChance().setHitRate(player, target);
                if (!(hit)) {
                    event.setDamageType("PHYSICAL_MISS");
                    event.setCancelled(true);
                    return;
                }

                /**目標格檔**/
                boolean block = new FormulaChance().setBlockRate(player, target);
                if (block) {
                    event.setDamageType("PHYSICAL_BLOCK");
                    event.setCancelled(true);
                    return;
                }

                /**爆擊**/
                boolean crit = new FormulaChance().setCritChange(player, target);
                double attackNumber = 0;
                if (crit) {
                    event.setDamageType("PHYSICAL_CRITICAL");
                    attackNumber = new FormulaPhysics().setRangePhysicalCriticalDamageNumber(player, target);
                    event.setDamage(attackNumber);
                    return;
                }

                /**目標迴避**/
                boolean dodge = new FormulaChance().setDodgeRate(player, target);
                if (dodge) {
                    event.setDamageType("PHYSICAL_MISS");
                    event.setCancelled(true);
                    return;
                }

                /**普通攻擊**/
                event.setDamageType("PHYSICAL_ATTACK");
                attackNumber = new FormulaPhysics().setRangePhysicalDamageNumber(player, target);
                event.setDamage(attackNumber);

            }

        }
    }


}
