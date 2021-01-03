package com.daxton.customdisplay.listener.customdisplay;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.character.Arithmetic;
import com.daxton.customdisplay.api.character.NumberUtil;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.io.File;

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class PhysicalDamageListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private int  r = (int)(Math.random()*10)+1;

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPhysicalDamage(EntityDamageByEntityEvent event){

        if(event.getDamager() instanceof Player){
            MeleePhysicalDamage(event);
        }
        if(event.getDamager() instanceof Arrow){
            RangePhysicalDamage(event);
        }

    }

    public void RangePhysicalDamage(EntityDamageByEntityEvent event){
        PhysicalDamageEvent e = new PhysicalDamageEvent(getDamager(event), (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile);
        Bukkit.getPluginManager().callEvent(e);
        String targetUUIDString = e.getTarget().getUniqueId().toString();

        Player player = EntityFind.convertPlayer(e.getDamager());
        if(player != null){
            String playerUUIDString = player.getUniqueId().toString();
            File playerFilePatch = new File(cd.getDataFolder(),"Players/"+playerUUIDString+"/"+playerUUIDString+".yml");
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
            String plysical = playerConfig.getString(playerUUIDString+".Range_physics_formula");
            plysical = new StringConversion2(player,e.getTarget(),plysical,"Character").valueConv();
            int attackNumber = 0;
            try {
                double number = Arithmetic.eval(plysical);
                String numberDec = new NumberUtil(number,"#").getDecimalString();
                attackNumber = Integer.valueOf(numberDec);
            }catch (Exception exception){
                attackNumber = 0;
            }

            event.setDamage(attackNumber);
        }else {
            event.setDamage(e.getDamage());
        }

        event.setCancelled(e.isCancelled());
    }

    public void MeleePhysicalDamage(EntityDamageByEntityEvent event){

        PhysicalDamageEvent e = new PhysicalDamageEvent(getDamager(event), (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile);
        Bukkit.getPluginManager().callEvent(e);
        String targetUUIDString = e.getTarget().getUniqueId().toString();

        Player player = EntityFind.convertPlayer(e.getDamager());
        if(player != null){
            String playerUUIDString = player.getUniqueId().toString();
            File playerFilePatch = new File(cd.getDataFolder(),"Players/"+playerUUIDString+"/"+playerUUIDString+".yml");
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
            String plysical = playerConfig.getString(playerUUIDString+".Melee_physics_formula");
            plysical = new StringConversion2(player,e.getTarget(),plysical,"Character").valueConv();
            int attackNumber = 0;
            try {
                double number = Arithmetic.eval(plysical);
                String numberDec = new NumberUtil(number,"#").getDecimalString();
                attackNumber = Integer.valueOf(numberDec);
            }catch (Exception exception){
                attackNumber = 0;
            }

            event.setDamage(attackNumber);
        }else {
            event.setDamage(e.getDamage());
        }

        event.setCancelled(e.isCancelled());


    }



    public static LivingEntity getDamager(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity) {
            return (LivingEntity)event.getDamager();
        } else {
            if (event.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile)event.getDamager();
                if (projectile.getShooter() instanceof LivingEntity) {
                    return (LivingEntity)projectile.getShooter();
                }
            }

            return null;
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPhysicalDamageListener(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }

        double damageNumber = event.getFinalDamage();
        LivingEntity target = (LivingEntity) event.getEntity();
        Player player = EntityFind.convertPlayer(event.getDamager());
        if(player != null){

            String uuidString = player.getUniqueId().toString();
            PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
            new PlayerTrigger(player).onAttack(player,target);
        }

    }


}
