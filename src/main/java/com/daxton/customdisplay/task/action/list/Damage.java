package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.entity.EntityFind;
import com.daxton.customdisplay.api.entity.RadiusTarget;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.List;


public class Damage {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    public Damage(){

    }

    public void setDamage(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;



        setOther();
    }

    public void setOther(){

        String type = new SetValue(self,target,firstString,"[];","SKILL_PHYSICAL_ATTACK","type=").getString();

        String operate = new SetValue(self,target,firstString,"[];","ADD","operate=","opa=").getString();

        double amount = new SetValue(self,target,firstString,"[];","1","amount=","a=").getDouble(1);

        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);

        if(self != null && !(targetList.isEmpty())){
            PlayerDataMap.attack_Boolean4_Map.put(self.getUniqueId().toString(),false);
            if(type.equals("SKILL_MELEE_PHYSICAL_ATTACK")){
                giveMeleePhysicalDamage(self,targetList,amount,operate);
            }else if(type.equals("SKILL_RANGE_PHYSICAL_ATTACK")){
                giveRangePhysicalDamage(self,targetList,amount,operate);
            }else if(type.equals("SKILL_MAGIC_ATTACK")){
                giveMagicDamage(self,targetList,amount,operate);
            }else if(type.equals("SKILL_NO_ATTACK")){
                giveNoDamage(self,targetList,amount);
            }else {
                giveMeleePhysicalDamage(self,targetList,amount,operate);
            }

        }


    }

    /**近距離物理攻擊**/
    public void giveMeleePhysicalDamage(LivingEntity self,List<LivingEntity> targetList,double amount,String operate){
        if(operate.toUpperCase().contains("MULTIPLY")){
            /**鸚鵡**/
            Parrot entity = (Parrot) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.PARROT);
            entity.setSilent(true);
            entity.setInvisible(true);
            entity.setAI(false);
            entity.setCollidable(false);
            entity.setGravity(false);
            entity.setOwner((AnimalTamer) self);
            for(LivingEntity livingEntity : targetList){
                livingEntity.damage(amount,entity);
            }
            PlayerDataMap.attack_Boolean4_Map.put(self.getUniqueId().toString(),true);
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
            for(LivingEntity livingEntity : targetList){
                livingEntity.damage(amount,entity);
            }
            entity.remove();
        }


    }
    /**魔法攻擊**/
    public void giveMagicDamage(LivingEntity self,List<LivingEntity> targetList,double amount,String operate){
        if(operate.toUpperCase().contains("MULTIPLY")){
            /**駱駝**/
            Llama entity = (Llama) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.LLAMA);
            entity.setSilent(true);
            entity.setInvisible(true);
            entity.setAI(false);
            entity.setCollidable(false);
            entity.setGravity(false);
            entity.setOwner((AnimalTamer) self);
            for(LivingEntity livingEntity : targetList){
                livingEntity.damage(amount,entity);
            }
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
            for(LivingEntity livingEntity : targetList){
                livingEntity.damage(amount,entity);
            }
            entity.remove();
        }


    }
    /**遠距離物理攻擊**/
    public void giveRangePhysicalDamage(LivingEntity self,List<LivingEntity> targetList,double amount,String operate){
        if(operate.toUpperCase().contains("MULTIPLY")){
            /**驢**/
            Donkey entity = (Donkey) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.DONKEY);
            entity.setSilent(true);
            entity.setInvisible(true);
            entity.setAI(false);
            entity.setCollidable(false);
            entity.setGravity(false);
            entity.setOwner((AnimalTamer) self);
            for(LivingEntity livingEntity : targetList){
                livingEntity.damage(amount,entity);
            }
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
            for(LivingEntity livingEntity : targetList){
                livingEntity.damage(amount,entity);
            }
            entity.remove();
        }


    }



    public void giveNoDamage(LivingEntity self,List<LivingEntity> targetList,double amount){
        for(LivingEntity livingEntity : targetList){
            livingEntity.damage(amount);
        }
        PhysicalDamageEvent event = new PhysicalDamageEvent(self,target, amount, self instanceof Projectile,"MAGIC_ATTACK","");
        Bukkit.getPluginManager().callEvent(event);


    }



}
