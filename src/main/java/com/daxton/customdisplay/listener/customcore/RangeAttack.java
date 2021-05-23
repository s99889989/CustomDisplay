package com.daxton.customdisplay.listener.customcore;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.player.damageformula.FormulaNew;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class RangeAttack implements Listener {

    //遠距離攻擊
    @EventHandler(priority = EventPriority.LOW)
    public void onPhysical(PhysicalDamageEvent event) {

        String damageType = event.getDamageType();

        //遠距離攻擊(倍率)
        if(damageType.equals("RANGE_MULTIPLY")){
            onRangeMultiply(event);
            return;
        }
        //遠距離攻擊(增加)
        if(damageType.equals("RANGE_ADD")){
            onRangeAdd(event);
            return;
        }
        //遠距離攻擊(攻擊)
        if(damageType.equals("RANGE_ATTACK")){
            onRangeAttack(event);
        }

    }

    //遠距離攻擊(倍率)
    public static void onRangeMultiply(PhysicalDamageEvent event) {
        if(MainAttack.deBug()){
            CustomDisplay.getCustomDisplay().getLogger().info("遠距離攻擊(倍率)");
        }

        Player player = (Player) event.getDamager();
        LivingEntity target = event.getTarget();
        double attackNumber = event.getDamage();

        //命中
        boolean hit = FormulaNew.HitRate(player, target);
        if (!(hit)) {
            event.setDamageType("PHYSICAL_MISS");
            event.setCancelled(true);
            return;
        }

        //目標格檔
        boolean block = FormulaNew.BlockRate(player, target);
        if (block) {
            event.setDamageType("PHYSICAL_BLOCK");
            event.setCancelled(true);
            return;
        }

        //爆擊
        boolean crit = FormulaNew.CritChange(player, target);
        if (crit) {
            event.setDamageType("PHYSICAL_CRITICAL");
            attackNumber += FormulaNew.CriticalRangeAttack(player, target);
            event.setDamage(attackNumber);
            return;
        }

        attackNumber = FormulaNew.RangeAttack(player, target) * (attackNumber/100);
        event.setDamageType("RANGE_ATTACK");
        event.setDamage(attackNumber);

    }
    //遠距離攻擊(增加)
    public static void onRangeAdd(PhysicalDamageEvent event) {
        if(MainAttack.deBug()){
            CustomDisplay.getCustomDisplay().getLogger().info("遠距離攻擊(增加)");
        }

        Player player = (Player) event.getDamager();
        LivingEntity target = event.getTarget();
        double attackNumber = event.getDamage();

        //命中
        boolean hit = FormulaNew.HitRate(player, target);
        if (!(hit)) {
            event.setDamageType("PHYSICAL_MISS");
            event.setCancelled(true);
            return;
        }

        //目標格檔
        boolean block = FormulaNew.BlockRate(player, target);
        if (block) {
            event.setDamageType("PHYSICAL_BLOCK");
            event.setCancelled(true);
            return;
        }

        //爆擊
        boolean crit = FormulaNew.CritChange(player, target);
        if (crit) {
            event.setDamageType("PHYSICAL_CRITICAL");
            attackNumber += FormulaNew.CriticalRangeAttack(player, target);
            event.setDamage(attackNumber);
            return;
        }

        attackNumber += FormulaNew.RangeAttack(player, target);
        event.setDamageType("RANGE_ATTACK");
        event.setDamage(attackNumber);

    }
    //遠距離攻擊(攻擊)
    public static void onRangeAttack(PhysicalDamageEvent event) {
        if(MainAttack.deBug()){
            CustomDisplay.getCustomDisplay().getLogger().info("遠距離攻擊(攻擊)");
        }

        Player player = (Player) ((Arrow) event.getDamager()).getShooter();
        LivingEntity target = event.getTarget();
        double attackNumber = event.getDamage();

        String uuidString = player.getUniqueId().toString();
        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
        //攻速
        if(playerData.attackSpeed){
            FormulaNew.AttackSpeed(player, target, playerData);

            //命中
            boolean hit = FormulaNew.HitRate(player, target);
            if (!(hit)) {
                event.setDamageType("PHYSICAL_MISS");
                event.setCancelled(true);
                return;
            }

            //目標格檔
            boolean block = FormulaNew.BlockRate(player, target);
            if (block) {
                event.setDamageType("PHYSICAL_BLOCK");
                event.setCancelled(true);
                return;
            }

            //爆擊
            boolean crit = FormulaNew.CritChange(player, target);
            if (crit) {
                event.setDamageType("PHYSICAL_CRITICAL");
                attackNumber += FormulaNew.CriticalRangeAttack(player, target);
                event.setDamage(attackNumber);
                return;
            }

            attackNumber += FormulaNew.RangeAttack(player, target);

        }

        event.setDamageType("RANGE_ATTACK");
        event.setDamage(attackNumber);

    }

}
