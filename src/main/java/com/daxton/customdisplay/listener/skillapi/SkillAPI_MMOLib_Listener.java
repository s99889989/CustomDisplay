package com.daxton.customdisplay.listener.skillapi;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.sucy.skill.api.event.PhysicalDamageEvent;
import com.sucy.skill.api.event.SkillDamageEvent;
import com.sucy.skill.listener.AttributeListener;
import net.citizensnpcs.api.npc.NPC;
import net.mmogroup.mmolib.api.event.PlayerAttackEvent;
import net.mmogroup.mmolib.api.player.MMOPlayerData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

import static net.mmogroup.mmolib.api.stat.SharedStat.*;

public class SkillAPI_MMOLib_Listener extends AttributeListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target;

    private UUID playerUUID;

    private UUID targetUUID;

    private double damageNumberPAE = 0.0;

    private String damageType = "";

    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onSkillDamage(SkillDamageEvent event){

        if(event.getDamager() instanceof Player & event.getTarget() instanceof LivingEntity){
            Player player = ((Player) event.getDamager()).getPlayer();
            LivingEntity target = event.getTarget();
            double damageNumber = event.getDamage();
            PlaceholderManager.getDamage_Number_Map().put(event.getDamager().getUniqueId(),String.valueOf(damageNumber));
            new PlayerTrigger(player).onMagic(player,target);

        }

    }


    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onPhysicalDamage(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player & event.getEntity() instanceof LivingEntity){
            Player player = ((Player) event.getDamager()).getPlayer();
            UUID playerUUID = player.getUniqueId();
            LivingEntity target = (LivingEntity) event.getEntity();
            double damageNumber = event.getFinalDamage();
            PlaceholderManager.getDamage_Number_Map().put(event.getDamager().getUniqueId(),String.valueOf(damageNumber));

            /**Base damage**/
            double attack_damage = MMOPlayerData.get(playerUUID).getStatMap().getStat(ATTACK_DAMAGE);
            /**Additional physical attack**/
            double physical_damage = MMOPlayerData.get(playerUUID).getStatMap().getStat(PHYSICAL_DAMAGE);
            physical_damage = (attack_damage/100)*physical_damage;
            /**Critical boost**/
            double physical_STRIKE_POWER = MMOPlayerData.get(playerUUID).getStatMap().getStat(CRITICAL_STRIKE_POWER);
            physical_STRIKE_POWER = (attack_damage+physical_damage)*((physical_STRIKE_POWER+180)/100);
            if(damageType.contains("WEAPON")){
                //player.sendMessage("Actual value: "+damageNumber);
                //player.sendMessage("Estimated value: "+physical_STRIKE_POWER);
                if(damageNumber > physical_STRIKE_POWER ){
                    //player.sendMessage(damageNumber +">"+physical_STRIKE_POWER+"Critical strike");
                    new PlayerTrigger(player).onCrit(player,target);
                }else {
                    //player.sendMessage(damageNumber +"<"+physical_STRIKE_POWER+"No critical strike");
                    new PlayerTrigger(player).onAttack(player,target);
                }
            }

        }


    }

    @EventHandler(
            //ignoreCancelled = true,
            //priority = EventPriority.MONITOR
    )
    public void c(PlayerAttackEvent event) {
//        StatMap stats = event.getData().getStatMap();
//        cd.getLogger().info("額外魔法攻擊: "+stats.getStat(MAGICAL_DAMAGE));
        damageNumberPAE = event.getAttack().getDamage();
        damageType = event.getAttack().getTypes().toString();
    }

}
