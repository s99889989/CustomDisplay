package com.daxton.customdisplay.task.action.entity;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.util.List;
import java.util.Map;


public class Damage3 {

    public Damage3(){

    }

    public static void setOther(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        int amount = actionMapHandle.getInt(new String[]{"amount","a"},1);

        String type = actionMapHandle.getString(new String[]{"type","t"},"");

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListTarget();

        type = type.toLowerCase();


        if(type.equals("range_multiply")){
            livingEntityList.forEach(livingEntity ->  setDamage(self, livingEntity, amount+0.3444440000000001));
            return;
        }
        if(type.equals("range_add")){
            livingEntityList.forEach(livingEntity ->  setDamage(self, livingEntity, amount+0.3333330000000001));
            return;
        }
        if(type.equals("range_attack")){
            livingEntityList.forEach(livingEntity ->  setDamage(self, livingEntity, amount+0.3222220000000001));
            return;
        }

        if(type.equals("magic_multiply")){
            livingEntityList.forEach(livingEntity ->  setDamage(self, livingEntity, amount+0.2444440000000001));
            return;
        }
        if(type.equals("magic_add")){
            livingEntityList.forEach(livingEntity ->  setDamage(self, livingEntity, amount+0.2333330000000001));
            return;
        }
        if(type.equals("magic_attack")){
            livingEntityList.forEach(livingEntity ->  setDamage(self, livingEntity, amount+0.2222220000000001));
            return;
        }

        if(type.equals("melee_multiply")){
            livingEntityList.forEach(livingEntity ->  setDamage(self, livingEntity, amount+0.1444440000000001));
            return;
        }
        if(type.equals("melee_add")){
            livingEntityList.forEach(livingEntity ->  setDamage(self, livingEntity, amount+0.1333330000000001));
            return;
        }
        if(type.equals("melee_attack")){
            livingEntityList.forEach(livingEntity ->  setDamage(self, livingEntity, amount));
            return;
        }
        livingEntityList.forEach(livingEntity ->  setDamage(self, livingEntity, amount));


    }

    public static void setDamage(LivingEntity self, LivingEntity target,double amount){
        target.damage(amount, self);
    }

    public static void setSkillDamage(LivingEntity self, LivingEntity target,double amount){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        /**鸚鵡**/
        Parrot entity = (Parrot) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.PARROT);
        entity.setSilent(true);
        entity.setInvisible(true);
        entity.setAI(false);
        entity.setCollidable(false);
        entity.setGravity(false);
        entity.setOwner((AnimalTamer) self);
        target.damage(amount,entity);
        //PlayerManager.attack_Boolean4_Map.put(self.getUniqueId().toString(),true);
        entity.remove();
//        new BukkitRunnable() {
//            @Override
//            public void run() {
//                entity.remove();
//            }
//        }.runTaskLater(cd, 5);

    }

    /**近距離物理攻擊**/
    public void giveMeleePhysicalDamage(LivingEntity self, LivingEntity target,double amount,String operate){
        if(operate.toUpperCase().contains("MULTIPLY")){
            /**鸚鵡**/
            Parrot entity = (Parrot) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.PARROT);
            entity.setSilent(true);
            entity.setInvisible(true);
            entity.setAI(false);
            entity.setCollidable(false);
            entity.setGravity(false);
            entity.setOwner((AnimalTamer) self);
            target.damage(amount,entity);
            PlayerManager.attack_Boolean4_Map.put(self.getUniqueId().toString(),true);
            entity.remove();
        }else {
            /**貓**/
            Cat entity = (Cat) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.CAT);
            entity.setSilent(true);
            entity.setInvisible(true);
            entity.setAI(false);
            entity.setCollidable(false);
            entity.setGravity(false);
            entity.setOwner((AnimalTamer) self);
            target.damage(amount,entity);
            entity.remove();
        }


    }
    /**魔法攻擊**/
    public void giveMagicDamage(LivingEntity self, LivingEntity target,double amount,String operate){
        if(operate.toUpperCase().contains("MULTIPLY")){
            /**駱駝**/
            Llama entity = (Llama) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.LLAMA);
            entity.setSilent(true);
            entity.setInvisible(true);
            entity.setAI(false);
            entity.setCollidable(false);
            entity.setGravity(false);
            entity.setOwner((AnimalTamer) self);
            target.damage(amount,entity);
            entity.remove();

        }else {
            /**殭屍馬**/
            ZombieHorse entity = (ZombieHorse) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.ZOMBIE_HORSE);
            entity.setSilent(true);
            entity.setInvisible(true);
            entity.setAI(false);
            entity.setCollidable(false);
            entity.setGravity(false);
            entity.setOwner((AnimalTamer) self);
            target.damage(amount,entity);
            entity.remove();
        }


    }
    /**遠距離物理攻擊**/
    public void giveRangePhysicalDamage(LivingEntity self, LivingEntity target,double amount,String operate){
        if(operate.toUpperCase().contains("MULTIPLY")){
            /**驢**/
            Donkey entity = (Donkey) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.DONKEY);
            entity.setSilent(true);
            entity.setInvisible(true);
            entity.setAI(false);
            entity.setCollidable(false);
            entity.setGravity(false);
            entity.setOwner((AnimalTamer) self);
            target.damage(amount,entity);
            entity.remove();
        }else {
            /**馬騾**/
            Mule entity = (Mule) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.MULE);
            entity.setSilent(true);
            entity.setInvisible(true);
            entity.setAI(false);
            entity.setCollidable(false);
            entity.setGravity(false);
            entity.setOwner((AnimalTamer) self);

            target.damage(amount,entity);

            entity.remove();
        }


    }



    public void giveNoDamage(LivingEntity self, LivingEntity target,double amount){

        target.damage(amount);

        PhysicalDamageEvent event = new PhysicalDamageEvent(self,target, amount, self instanceof Projectile,"MAGIC_ATTACK","");
        Bukkit.getPluginManager().callEvent(event);


    }



}
