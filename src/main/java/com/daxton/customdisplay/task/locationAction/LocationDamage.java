package com.daxton.customdisplay.task.locationAction;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.StringConversionMain;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;


public class LocationDamage {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    public LocationDamage(){

    }

    public void setDamage(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        if(target == null){
            return;
        }
        setOther();
    }

    public void setOther(){
        double amount = 1;
        String type = "SKILL_PHYSICAL_ATTACK";
        String operate = "ADD";
        for(String allString : new StringFind().getStringMessageList(firstString)){

            if(allString.toLowerCase().contains("type=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    type = strings[1];

                }
            }

            if(allString.toLowerCase().contains("operate=") || allString.toLowerCase().contains("opa=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    operate = strings[1];

                }
            }


            if(allString.toLowerCase().contains("amount=") || allString.toLowerCase().contains("a=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    try{
                        amount = Double.valueOf(new StringConversionMain().valueOf(self,target,strings[1]));
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("Damage的amount=內只能放數字: "+strings[1]);
                    }
                }
            }

        }

        if(self != null && target != null){
            if(type.equals("SKILL_MELEE_PHYSICAL_ATTACK")){
                giveMeleePhysicalDamage(self,target,amount,operate);
            }else if(type.equals("SKILL_RANGE_PHYSICAL_ATTACK")){
                giveRangePhysicalDamage(self,target,amount,operate);
            }else if(type.equals("SKILL_MAGIC_ATTACK")){
                giveMagicDamage(self,target,amount,operate);
            }else if(type.equals("SKILL_NO_ATTACK")){
                giveNoDamage(self,target,amount);
            }else if(type.equals("PLAYER")){
                playerDamage(self,target,amount);
            }else {
                giveMeleePhysicalDamage(self,target,amount,operate);
            }


        }


    }

    /**近距離物理攻擊**/
    public void giveMeleePhysicalDamage(LivingEntity self,LivingEntity target,double amount,String operate){
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
    public void giveMagicDamage(LivingEntity self,LivingEntity target,double amount,String operate){
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
    public void giveRangePhysicalDamage(LivingEntity self,LivingEntity target,double amount,String operate){
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



    public void giveNoDamage(LivingEntity self,LivingEntity target,double amount){

        target.damage(amount);
        PhysicalDamageEvent event = new PhysicalDamageEvent(self,target, amount, self instanceof Projectile,"MAGIC_ATTACK","");
        Bukkit.getPluginManager().callEvent(event);


    }

    public void playerDamage(LivingEntity self,LivingEntity target,double amount){
        target.damage(amount,self);
    }



}
