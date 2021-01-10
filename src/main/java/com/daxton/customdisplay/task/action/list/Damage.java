package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.event.PhysicalDamageEvent;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;


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
        if(target == null){
            this.target = (LivingEntity) new EntityFind().getTarget(self,10);
        }
        setOther();
    }

    public void setOther(){
        double amount = 1;
        for(String allString : new StringFind().getStringMessageList(firstString)){

            if(allString.toLowerCase().contains("amount=") || allString.toLowerCase().contains("a=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    try{
                        amount = Double.valueOf(new StringConversion(self,target,strings[1],"Character").valueConv());
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("Heal的amount=內只能放數字: "+strings[1]);
                    }
                }
            }

        }

        if(self != null && target != null){

            givePhysicalDamage(self,target,amount);

        }


    }

    public void givePhysicalDamage(LivingEntity self,LivingEntity target,double amount){
        Wolf entity = ( Wolf) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.WOLF);

        entity.setInvisible(true);
        entity.setAI(false);
        entity.setCollidable(false);
        entity.setGravity(false);
        entity.setOwner((AnimalTamer) self);
        target.damage(amount,entity);
        entity.remove();

    }

    public void giveMagDamage(LivingEntity self,LivingEntity target,double amount){
        Cat entity = (Cat) self.getWorld().spawnEntity((self).getLocation().add(0,-10,0), EntityType.CAT);

        entity.setInvisible(true);
        entity.setAI(false);
        entity.setCollidable(false);
        entity.setGravity(false);
        entity.setOwner((AnimalTamer) self);
        target.damage(amount,entity);
        entity.remove();

    }

    public void giveMagicDamage(LivingEntity self,LivingEntity target,double amount){

        target.damage(amount);
        PhysicalDamageEvent event = new PhysicalDamageEvent(self, (LivingEntity) target, amount, self instanceof Projectile,"MAGIC_ATTACK");
        Bukkit.getPluginManager().callEvent(event);


    }

}
