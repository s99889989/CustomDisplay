package com.daxton.customdisplay.listener.customdisplay;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class MainListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    @EventHandler(priority = EventPriority.LOW)
    public void onPhysicalDamage(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }

        //cd.getLogger().info(event.getDamager().getType().toString());
        Entity damager = event.getDamager();
        /**近距離物理攻擊**/
        if(damager instanceof Player || damager instanceof Parrot || damager instanceof Cat){
            MeleePhysicalAttack(event);
        }
        /**遠距離物理攻擊**/
        if(damager instanceof Arrow || damager instanceof Donkey || damager instanceof Mule){
            RangePhysicalAttack(event);
        }
        /**魔法攻擊**/
        if(damager instanceof Llama || damager instanceof ZombieHorse){
            MagicAttack(event);
        }


    }
    /**近距離物理攻擊**/
    public void MeleePhysicalAttack(EntityDamageByEntityEvent event){
        /**近距離物理普通攻擊**/
        if(event.getDamager() instanceof Player){
            PhysicalDamageEvent e = new PhysicalDamageEvent(event.getDamager(), (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile,"MELEE_PHYSICAL_ATTACK","");
            Bukkit.getPluginManager().callEvent(e);
            event.setDamage(e.getDamage());
            event.setCancelled(e.isCancelled());
            return;
        }
        /**近距離物理攻擊倍率**/
        if(event.getDamager() instanceof Parrot){
            Parrot parrot = (Parrot) event.getDamager();
            if(parrot.getOwner() != null && parrot.getOwner() instanceof Player){
                Player player = ((Player) parrot.getOwner()).getPlayer();
                PhysicalDamageEvent e = new PhysicalDamageEvent(player, (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile,"SKILL_MELEE_PHYSICAL_ATTACK","multiply");
                Bukkit.getPluginManager().callEvent(e);
                event.setDamage(e.getDamage());
                event.setCancelled(e.isCancelled());
                return;

            }
        }
        /**近距離物理攻擊增加**/
        if(event.getDamager() instanceof Cat){
            Cat cat = (Cat) event.getDamager();
            if(cat.getOwner() != null && cat.getOwner() instanceof Player){
                Player player = ((Player) cat.getOwner()).getPlayer();
                PhysicalDamageEvent e = new PhysicalDamageEvent(player, (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile,"SKILL_MELEE_PHYSICAL_ATTACK","add");
                Bukkit.getPluginManager().callEvent(e);
                event.setDamage(e.getDamage());
                event.setCancelled(e.isCancelled());
                return;

            }
        }

    }
    /**遠距離物理攻擊**/
    public void RangePhysicalAttack(EntityDamageByEntityEvent event){
        /**遠距離物理普通攻擊**/
        if(event.getDamager() instanceof Arrow){
            PhysicalDamageEvent e = new PhysicalDamageEvent(event.getDamager(), (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile,"RANGE_PHYSICAL_ATTACK","");
            Bukkit.getPluginManager().callEvent(e);
            event.setDamage(e.getDamage());
            event.setCancelled(e.isCancelled());
            return;
        }
        /**遠距離物理攻擊倍率**/
        if(event.getDamager() instanceof Donkey){
            Donkey donkey = (Donkey) event.getDamager();
            if(donkey.getOwner() != null && donkey.getOwner() instanceof Player){
                Player player = ((Player) donkey.getOwner()).getPlayer();
                PhysicalDamageEvent e = new PhysicalDamageEvent(player, (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile,"SKILL_RANGE_PHYSICAL_ATTACK","multiply");
                Bukkit.getPluginManager().callEvent(e);
                event.setDamage(e.getDamage());
                event.setCancelled(e.isCancelled());
                return;

            }
        }
        /**遠距離物理攻擊增加**/
        if(event.getDamager() instanceof Mule){
            Mule mule = (Mule) event.getDamager();
            if(mule.getOwner() != null && mule.getOwner() instanceof Player){
                Player player = ((Player) mule.getOwner()).getPlayer();
                PhysicalDamageEvent e = new PhysicalDamageEvent(player, (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile,"SKILL_RANGE_PHYSICAL_ATTACK","add");
                Bukkit.getPluginManager().callEvent(e);
                event.setDamage(e.getDamage());
                event.setCancelled(e.isCancelled());
                return;

            }
        }
    }
    /**魔法攻擊**/
    public void MagicAttack(EntityDamageByEntityEvent event){
        /**魔法攻擊倍率**/
        if(event.getDamager() instanceof Llama){
            Llama llama = (Llama) event.getDamager();
            if(llama.getOwner() != null && llama.getOwner() instanceof Player){
                Player player = ((Player) llama.getOwner()).getPlayer();
                PhysicalDamageEvent e = new PhysicalDamageEvent(player, (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile,"SKILL_MAGIC_ATTACK","multiply");
                Bukkit.getPluginManager().callEvent(e);
                event.setDamage(e.getDamage());
                event.setCancelled(e.isCancelled());
                return;

            }
        }
        /**魔法攻擊增加**/
        if(event.getDamager() instanceof ZombieHorse){
            ZombieHorse zombieHorse = (ZombieHorse) event.getDamager();
            if(zombieHorse.getOwner() != null && zombieHorse.getOwner() instanceof Player){
                Player player = ((Player) zombieHorse.getOwner()).getPlayer();
                PhysicalDamageEvent e = new PhysicalDamageEvent(player, (LivingEntity) event.getEntity(), event.getDamage(), event.getDamager() instanceof Projectile,"SKILL_MAGIC_ATTACK","add");
                Bukkit.getPluginManager().callEvent(e);
                event.setDamage(e.getDamage());
                event.setCancelled(e.isCancelled());
                return;

            }
        }
    }

}
