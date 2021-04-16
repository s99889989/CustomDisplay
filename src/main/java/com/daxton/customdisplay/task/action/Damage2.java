package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.manager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.util.List;


public class Damage2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private CustomLineConfig customLineConfig;

    public Damage2(){

    }

    public void setDamage(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;



        setOther();
    }

    public void setOther(){

        String type = customLineConfig.getString(new String[]{"type"},"SKILL_PHYSICAL_ATTACK",self,target);

        String operate = customLineConfig.getString(new String[]{"operate","opa"},"ADD",self,target);

        double amount = customLineConfig.getDouble(new String[]{"amount","a"},1,self,target);

        List<LivingEntity> livingEntityList = customLineConfig.getLivingEntityList(self,target);

        if(!(livingEntityList.isEmpty())){
            for(LivingEntity livingEntity : livingEntityList){

                setDamage(self, livingEntity, amount);

            }

        }
        //PlayerDataMap.attack_Boolean4_Map.put(self.getUniqueId().toString(),false);
//        if(target != null){
//            setDamage(self, target, amount);
//        }

//        if(target != null){
//            if(type.equals("SKILL_MELEE_PHYSICAL_ATTACK")){
//                giveMeleePhysicalDamage(self,target,amount,operate);
//            }else if(type.equals("SKILL_RANGE_PHYSICAL_ATTACK")){
//                giveRangePhysicalDamage(self,target,amount,operate);
//            }else if(type.equals("SKILL_MAGIC_ATTACK")){
//                giveMagicDamage(self,target,amount,operate);
//            }else if(type.equals("SKILL_NO_ATTACK")){
//                giveNoDamage(self,target,amount);
//            }else {
//                giveMeleePhysicalDamage(self,target,amount,operate);
//            }
//        }


    }

    public void setDamage(LivingEntity self, LivingEntity target,double amount){
        target.damage(amount, self);
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