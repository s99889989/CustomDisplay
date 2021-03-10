package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class Heal {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    public Heal(){


    }

    public void setHeal(LivingEntity self, LivingEntity target, String firstString, String taskID){

        this.self = self;
        this.target = target;
        this.firstString = firstString;
        setOther();
    }

    public void setOther(){

        /**補量**/
        double amount = 1;
        try{
            amount = Double.valueOf(new StringFind().getKeyValue2(self,target,firstString,"[];","1","amount=","a="));
        }catch (NumberFormatException exception){
            amount = 1;
            cd.getLogger().info("Heal的amount=內只能放數字");
        }

        /**目標**/
        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);

        if(!(targetList.isEmpty())){
            for(LivingEntity livingEntity : targetList){
                giveHeal(livingEntity,amount);
            }
        }

    }

    public void giveHeal(LivingEntity livingEntity,double amount){

        double giveHealth = livingEntity.getHealth()+amount;
        double maxHealth = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        if(giveHealth > maxHealth){
            giveHealth = giveHealth - (giveHealth - maxHealth);
        }

        //player.sendMessage("補血量:"+amount);
        livingEntity.setHealth(giveHealth);
    }


}
