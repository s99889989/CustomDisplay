package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.player.damageformula.FormulaMagic;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class MagicAttack implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    @EventHandler(priority = EventPriority.LOW)
    public void onPhysical(PhysicalDamageEvent event) {

        String damageType = event.getDamageType();
        if(damageType != null && damageType.equals("SKILL_MAGIC_ATTACK")) {
            if (!(event.getDamager() instanceof Player)) {
                return;
            }
            Player player = (Player) event.getDamager();
            if (player != null) {
                LivingEntity target = event.getTarget();
                String uuidString = player.getUniqueId().toString();

                double attackNumber = 0;
                attackNumber = new FormulaMagic().setMagicDamageNumber(player, target);
                //player.sendMessage("魔法攻擊: "+attackNumber);
                String operate = event.getOperate();

                if(operate.contains("multiply")){
                    attackNumber = attackNumber* event.getDamage();
                }else {
                    attackNumber = attackNumber + event.getDamage();
                }

                event.setDamage(attackNumber);

                event.setDamageType("MAGIC_ATTACK");



            }





        }



    }



}
