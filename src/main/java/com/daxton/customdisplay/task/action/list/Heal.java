package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Heal {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private int amount = 1;

    public Heal(){


    }

    public void setHeal(LivingEntity self, LivingEntity target, String firstString, String taskID){

        this.self = self;
        this.target = target;
        this.firstString = firstString;
        setOther();
    }

    public void setOther(){
        for(String allString : new StringFind().getStringMessageList(firstString)){

            if(allString.toLowerCase().contains("amount=") || allString.toLowerCase().contains("a=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    try{
                        amount = Integer.valueOf(new StringConversion2(self,target,strings[1],"Character").valueConv());
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("Heal的amount=內只能放整數數字: "+strings[1]);
                    }
                }
            }

        }

        if(self instanceof Player){
            Player player = (Player) self;
            giveHeal(player);
        }

    }

    public void giveHeal(Player player){

        double giveHealth = player.getHealth()+amount;
        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

        if(giveHealth > maxHealth){
            giveHealth = giveHealth - (giveHealth - maxHealth);
        }


        player.setHealth(giveHealth);
    }


}
