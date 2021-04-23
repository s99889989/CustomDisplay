package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.player.damageformula.FormulaDelay;
import com.daxton.customdisplay.api.player.damageformula.FormulaChance;
import com.daxton.customdisplay.api.player.damageformula.FormulaPhysics;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RangePhysicalAttack implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler(priority = EventPriority.LOW)
    public void onPhysical(PhysicalDamageEvent event) {

        String damageType = event.getDamageType();

        if(damageType != null && damageType.equals("RANGE_PHYSICAL_ATTACK") || damageType.equals("SKILL_RANGE_PHYSICAL_ATTACK")) {

            if (!(event.getDamager() instanceof Arrow)) {
                return;
            }

            Player player = Convert.convertPlayer(event.getDamager());
            if (player != null) {
                String uuidString = player.getUniqueId().toString();

                LivingEntity target = event.getTarget();

                /**攻速**/
                if(PlayerManager.attack_Boolean_Map.get(uuidString) == null){
                    PlayerManager.attack_Boolean_Map.put(uuidString,false);
                }
                boolean attack_speed = PlayerManager.attack_Boolean_Map.get(uuidString);
                if (attack_speed) {
                    event.setDamageType("PHYSICAL_NON");
                    event.setCancelled(true);
                    return;
                }else {
                    if(PlayerManager.attack_Boolean2_Map.get(uuidString) == null){
                        PlayerManager.attack_Boolean2_Map.put(uuidString,true);
                    }
                    if(PlayerManager.attack_Boolean2_Map.get(uuidString)){
                        PlayerManager.attack_Boolean_Map.put(uuidString,true);
                        PlayerManager.attack_Boolean2_Map.put(uuidString,false);
                        new FormulaDelay().setAttackSpeed(player, target, uuidString);
                    }
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
