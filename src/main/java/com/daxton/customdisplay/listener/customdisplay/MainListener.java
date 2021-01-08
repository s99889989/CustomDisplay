package com.daxton.customdisplay.listener.customdisplay;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.character.StringConversion;
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

public class MainListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPhysicalDamage(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }

        if(event.getDamager() instanceof Player){
            MeleePhysicalDamage(event);
            return;
        }
        if(event.getDamager() instanceof Arrow){
            RangePhysicalDamage(event);
            return;
        }



    }

    public void RangePhysicalDamage(EntityDamageByEntityEvent event){
        PhysicalDamageEvent e = new PhysicalDamageEvent(event.getDamager(), (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile);
        Bukkit.getPluginManager().callEvent(e);
        event.setDamage(e.getDamage());
        event.setCancelled(e.isCancelled());

    }

    public void MeleePhysicalDamage(EntityDamageByEntityEvent event){

        PhysicalDamageEvent e = new PhysicalDamageEvent(event.getDamager(), (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile);
        Bukkit.getPluginManager().callEvent(e);
        event.setDamage(e.getDamage());
        event.setCancelled(e.isCancelled());

    }

    public void PhysicalDamaged(EntityDamageByEntityEvent event){

    }


//    public static LivingEntity getDamager(EntityDamageByEntityEvent event) {
//        if (event.getDamager() instanceof LivingEntity) {
//            return (LivingEntity)event.getDamager();
//        } else {
//            if (event.getDamager() instanceof Projectile) {
//                Projectile projectile = (Projectile)event.getDamager();
//                if (projectile.getShooter() instanceof LivingEntity) {
//                    return (LivingEntity)projectile.getShooter();
//                }
//            }
//
//            return null;
//        }
//    }




}
