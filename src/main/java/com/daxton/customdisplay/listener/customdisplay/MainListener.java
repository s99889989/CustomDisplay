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
import org.bukkit.entity.*;
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

        cd.getLogger().info(event.getDamager().getType().toString());
        if(event.getDamager() instanceof Player){
            PhysicalDamageEvent e = new PhysicalDamageEvent(event.getDamager(), (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile,"MELEE_PHYSICAL_ATTACK");
            Bukkit.getPluginManager().callEvent(e);
            event.setDamage(e.getDamage());
            event.setCancelled(e.isCancelled());
            return;
        }
        if(event.getDamager() instanceof Arrow){
            PhysicalDamageEvent e = new PhysicalDamageEvent(event.getDamager(), (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile,"RANGE_PHYSICAL_ATTACK");
            Bukkit.getPluginManager().callEvent(e);
            event.setDamage(e.getDamage());
            event.setCancelled(e.isCancelled());
            return;
        }
        if(event.getDamager() instanceof Wolf){
            Wolf wolf = (Wolf) event.getDamager();
            if(wolf.getOwner() != null && wolf.getOwner() instanceof Player){
                Player player = ((Player) wolf.getOwner()).getPlayer();
                PhysicalDamageEvent e = new PhysicalDamageEvent(player, (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile,"SKILL_PHYSICAL_ATTACK");
                Bukkit.getPluginManager().callEvent(e);
                event.setDamage(e.getDamage());
                event.setCancelled(e.isCancelled());
                return;

            }
        }
        if(event.getDamager() instanceof Cat){
            Cat cat = (Cat) event.getDamager();
            if(cat.getOwner() != null && cat.getOwner() instanceof Player){
                Player player = ((Player) cat.getOwner()).getPlayer();
                player.sendMessage("貓");

            }
        }

    }





}
