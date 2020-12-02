package com.daxton.customdisplay.listener.player;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;

public class MMOAttackListener {

//    @EventHandler
//    public void a(PlayerAttackEvent event) {
//        if (event.getPlayer().equals((Object)this.player.getPlayer()) && event.isWeapon()) {
//            this.close();
//            LivingEntity target = event.getEntity();
//            target.getWorld().playSound(target.getLocation(), "_archer_hit", 1.0f, 1.0f);
//            target.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, target.getLocation().add(0.0, target.getHeight() / 2.0, 0.0), 32, 0.0, 0.0, 0.0, 0.2);
//            double sweep = event.getAttack().getDamage() * this.c;
//            MMOLib.plugin.getDamage().damage(this.player.getPlayer(), target, new AttackResult(sweep - event.getAttack().getDamage(), new DamageType[]{DamageType.SKILL, DamageType.PHYSICAL}));
//            Location locuser = this.player.getPlayer().getEyeLocation();
//            Location loctarget = target.getEyeLocation();
//            Vector vec = loctarget.subtract(locuser).toVector().normalize();
//            target.setVelocity(vec.multiply(5));
//        }
//    }

}
