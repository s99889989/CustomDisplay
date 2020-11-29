package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.util.ContentUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Compare {
    public Compare(){

    }

    public boolean judgment(String firstString, LivingEntity target, Player player){
        boolean bo = false;

        bo = condition(firstString,player);

        return bo;
    }

    public boolean condition(String string,Player player){
        boolean b = false;
        String string1 = string.replace("Condition[","").replace("condition[","").replace("]","");
        if(string1.contains("<")){

            String[] strings1 = string1.split("<");
            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]) < Double.valueOf(strings1[1]))){
                b = true;
            }
        }
        if(string1.contains(">")){
            String[] strings1 = string1.split(">");
            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]) > Double.valueOf(strings1[1]))){
                b = true;
            }
        }
        if(string1.contains("=")){
            String[] strings1 = string1.split("=");
            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]).equals(Double.valueOf(strings1[1])))){
                b = true;
            }
        }

        return b;
    }

}
