package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Heal {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";

    private double amount = 1;

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
                        String amountString = new StringConversion(self,target,strings[1],"Character").valueConv();
                        amount = Arithmetic.eval(amountString);
                    }catch (NumberFormatException exception){
                        cd.getLogger().info("Heal的amount=內只能放數字: "+strings[1]);
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

        player.sendMessage("補血量:"+amount);
        player.setHealth(giveHealth);
    }


}
