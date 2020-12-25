package com.daxton.customdisplay.listener.skillapi;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import com.sucy.skill.api.event.PhysicalDamageEvent;
import com.sucy.skill.api.event.SkillDamageEvent;
import com.sucy.skill.listener.AttributeListener;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.mmogroup.mmolib.api.event.PlayerAttackEvent;
import net.mmogroup.mmolib.api.player.MMOPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.UUID;

import static net.mmogroup.mmolib.api.stat.SharedStat.*;
import static org.bukkit.entity.EntityType.ARMOR_STAND;

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
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getTarget())){
                return;
            }
        }
        if(event.getDamager() instanceof Player & event.getTarget() instanceof LivingEntity){
            Player player = ((Player) event.getDamager()).getPlayer();
            LivingEntity target = event.getTarget();
            double damageNumber = event.getDamage();
            PlaceholderManager.getCd_Placeholder_Map().put(player.getUniqueId().toString()+"<cd_attack_number>",String.valueOf(damageNumber));
            new PlayerTrigger(player).onMagic(player,target);

        }

    }


    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
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
        String uuidString = event.getDamager().getUniqueId().toString();
        double damageNumber = event.getFinalDamage();
        PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
        Entity damager = event.getDamager();
        if(damager instanceof Player){
            Player player = ((Player) event.getDamager()).getPlayer();
            UUID playerUUID = player.getUniqueId();
            LivingEntity target = (LivingEntity) event.getEntity();


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
            return;
        }

        if(damager instanceof Arrow){
            if(((Arrow) event.getDamager()).getShooter() instanceof Player){
                player = (Player) ((Arrow) event.getDamager()).getShooter();
                uuidString = ((Player) ((Arrow) event.getDamager()).getShooter()).getUniqueId().toString();
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                new PlayerTrigger(player).onAttack(player,target);
            }
        }
        if(damager instanceof ThrownPotion){
            if(((ThrownPotion) damager).getShooter() instanceof Player){
                player = (Player) ((ThrownPotion) damager).getShooter();
                uuidString = player.getUniqueId().toString();
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                new PlayerTrigger(player).onAttack(player,target);
                return;
            }
        }
        if(damager instanceof TNTPrimed){
            if(((TNTPrimed) damager).getSource() instanceof Player){
                player = (Player) ((TNTPrimed) event.getDamager()).getSource();
                uuidString = player.getUniqueId().toString();
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                new PlayerTrigger(player).onAttack(player,target);
            }
        }

    }

    @EventHandler(
            //ignoreCancelled = true,
            //priority = EventPriority.MONITOR
    )
    public void c(PlayerAttackEvent event) {
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }
//        StatMap stats = event.getData().getStatMap();
//        cd.getLogger().info("額外魔法攻擊: "+stats.getStat(MAGICAL_DAMAGE));
        damageNumberPAE = event.getAttack().getDamage();
        damageType = event.getAttack().getTypes().toString();
    }

}
