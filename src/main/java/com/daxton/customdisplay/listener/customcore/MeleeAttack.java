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

public class MeleeAttack implements Listener {

    //近距離攻擊
    @EventHandler(priority = EventPriority.LOW)
    public void onPhysical(PhysicalDamageEvent event) {

        String damageType = event.getDamageType();

        //近距離攻擊(倍率)
        if(damageType.equals("MELEE_MULTIPLY")){
            onMeleeMultiply(event);
            return;
        }
        //近距離攻擊(增加)
        if(damageType.equals("MELEE_ADD")){
            onMeleeAdd(event);
            return;
        }
        //近距離攻擊(攻擊)
        if(damageType.equals("MELEE_ATTACK")){
            onMeleeAttack(event);
        }

    }
    //近距離攻擊(倍率)
    public static void onMeleeMultiply(PhysicalDamageEvent event){
        if(MainAttack.deBug()){
            CustomDisplay.getCustomDisplay().getLogger().info("近距離攻擊(倍率)");
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
            attackNumber += FormulaNew.CriticalMeleeAttack(player, target);
            event.setDamage(attackNumber);
            return;
        }

        attackNumber = FormulaNew.MeleeAttack(player, target) * (attackNumber/100);

        event.setDamageType("Melee_ATTACK");

        event.setDamage(attackNumber);

    }
    //近距離攻擊(增加)
    public static void onMeleeAdd(PhysicalDamageEvent event){
        if(MainAttack.deBug()){
            CustomDisplay.getCustomDisplay().getLogger().info("近距離攻擊(增加)");
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
            attackNumber += FormulaNew.CriticalMeleeAttack(player, target);
            event.setDamage(attackNumber);
            return;
        }

        attackNumber += FormulaNew.MeleeAttack(player, target);
        event.setDamageType("Melee_ATTACK");
        event.setDamage(attackNumber);

    }

    //近距離攻擊(攻擊)
    public static void onMeleeAttack(PhysicalDamageEvent event){
        if(MainAttack.deBug()){
            CustomDisplay.getCustomDisplay().getLogger().info("近距離攻擊(攻擊)");
        }

        Player player = (Player) event.getDamager();
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
                attackNumber += FormulaNew.CriticalMeleeAttack(player, target);
                event.setDamage(attackNumber);
                return;
            }

            attackNumber += FormulaNew.MeleeAttack(player, target);

        }

        event.setDamageType("Melee_ATTACK");
        event.setDamage(attackNumber);

    }



    //            if(MobManager.mythicMobs_ActiveMob_Map.containsKey(target.getUniqueId().toString())){
//                CustomDisplay cd = CustomDisplay.getCustomDisplay();
//                ActiveMob activeMob = MobManager.mythicMobs_ActiveMob_Map.get(target.getUniqueId().toString());
//                new BukkitRunnable() {
//                    @Override
//                    public void run() {
//                        activeMob.getThreatTable().getAllThreatTargets().forEach(abstractEntity -> {
//                            String name = abstractEntity.getName();
//                            double d = activeMob.getThreatTable().getThreat(abstractEntity);
//                            player.sendMessage(name+"仇恨值2: "+d);
//                        });
//                    }
//                }.runTaskLater(cd, 10);
//
//            }



    //        Parrot parrot = (Parrot) event.getDamager();
//        if(parrot.getOwner() != null && parrot.getOwner() instanceof Player){
//            Player player = ((Player) parrot.getOwner()).getPlayer();
//            LivingEntity target = event.getTarget();
//            if(MobManager.mythicMobs_ActiveMob_Map.containsKey(target.getUniqueId().toString())){
//                CustomDisplay cd = CustomDisplay.getCustomDisplay();
//                ActiveMob activeMob = MobManager.mythicMobs_ActiveMob_Map.get(target.getUniqueId().toString());
//                new BukkitRunnable() {
//                    @Override
//                    public void run() {
//                        activeMob.getThreatTable().getAllThreatTargets().forEach(abstractEntity -> {
//                            double d = activeMob.getThreatTable().getThreat(abstractEntity);
//                            AbstractEntity abstractEntity2 = BukkitAdapter.adapt(player);
//                            activeMob.getThreatTable().threatSet(abstractEntity2, d);
//                            player.sendMessage(abstractEntity.getName()+" : "+d);
//                        });
//                    }
//                }.runTaskLater(cd, 1);
//
//            }
//
//
//        }

}
