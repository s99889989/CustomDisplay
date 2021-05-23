package com.daxton.customdisplay.listener.customcore;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.player.damageformula.FormulaNew;
import com.daxton.customdisplay.api.player.data.PlayerData2;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MagicAttack implements Listener {

    //魔法攻擊
    @EventHandler(priority = EventPriority.LOW)
    public void onMagic(PhysicalDamageEvent event) {

        String damageType = event.getDamageType();

        //魔法攻擊(倍率)
        if(damageType.equals("MAGIC_MULTIPLY")){
            onMagicMultiply(event);
            return;
        }
        //魔法攻擊(增加)
        if(damageType.equals("MAGIC_ADD")){
            onMagicAdd(event);
            return;
        }
        //魔法攻擊(攻擊)
        if(damageType.equals("MAGIC_ATTACK")){
            onMagicAttack(event);
        }
//        //魔法攻擊(限速)
//        if(damageType.equals("MAGIC_SPEED")){
//            onMagicSpeed(event);
//        }

    }

    //魔法攻擊(倍率)
    public static void onMagicMultiply(PhysicalDamageEvent event){
        if(MainAttack.deBug()){
            CustomDisplay.getCustomDisplay().getLogger().info("魔法攻擊(倍率)");
        }
        Player player = (Player) event.getDamager();
        LivingEntity target = event.getTarget();
        double attackNumber = event.getDamage();

        attackNumber = FormulaNew.MagicAttack(player, target) * (attackNumber/100);
        event.setDamageType("MAGIC_ATTACK");
        event.setDamage(attackNumber);

    }

    //魔法攻擊(增加)
    public static void onMagicAdd(PhysicalDamageEvent event){
        if(MainAttack.deBug()){
            CustomDisplay.getCustomDisplay().getLogger().info("魔法攻擊(增加)");
        }

        Player player = (Player) event.getDamager();
        LivingEntity target = event.getTarget();
        double attackNumber = event.getDamage();

        attackNumber += FormulaNew.MagicAttack(player, target);
        event.setDamageType("MAGIC_ATTACK");
        event.setDamage(attackNumber);

    }

    //魔法攻擊(攻擊)
    public static void onMagicAttack(PhysicalDamageEvent event){
        if(MainAttack.deBug()){
            CustomDisplay.getCustomDisplay().getLogger().info("魔法攻擊(攻擊)");
        }
        double attackNumber = event.getDamage();
        event.setDamageType("MAGIC_ATTACK");
        event.setDamage(attackNumber);
    }

//    //魔法攻擊(限速)
//    public static void onMagicSpeed(PhysicalDamageEvent event){
//        CustomDisplay.getCustomDisplay().getLogger().info("魔法攻擊(限速)");
//        Player player = (Player) event.getDamager();
//        LivingEntity target = event.getTarget();
//        double attackNumber = event.getDamage();
//
//        String uuidString = player.getUniqueId().toString();
//        PlayerData2 playerData = PlayerManager.player_Data_Map.get(uuidString);
//        //攻速
//        if(playerData.attackSpeed){
//            FormulaNew.AttackSpeed(player, target, playerData);
//
//            attackNumber += FormulaNew.MagicAttack(player, target);
//            event.setDamageType("MAGIC_ATTACK");
//            event.setDamage(attackNumber);
//            return;
//        }
//
//        event.setCancelled(true);
//
//    }

}
