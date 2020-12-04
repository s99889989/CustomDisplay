package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class Compare {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target;

    private String firstString = "";

    private String taskID = "";

    private double left = 0;
    private double right = 0;
    private String symbol = "";
    private String stringLeft = "";
    private String stringRight = "";

    private static Map<String, Health> healthMap = new HashMap<>();

    public Compare(){

    }

    public Compare(Player player, LivingEntity target ,String firstString){
        this.player = player;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        setOther();
    }

    public Compare(Player player ,String firstString){
        this.player = player;
        this.firstString = firstString;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){
        try{
            List<String> stringList = new StringFind().getStringMessageList(firstString);
            for(String string1 : stringList){
                if(string1.toLowerCase().contains("compare=")){
                    String[] strings = string1.split("=");
                    if(strings[1].toLowerCase().contains(">")){
                        symbol = ">";
                        String[] strings1 = strings[1].replace(" ","").split(">");
                        stringLeft = new StringConversion().getString("Character",strings1[0],player);
                        stringRight = new StringConversion().getString("Character",strings1[1],player);
                        left = Double.valueOf(stringLeft);
                        right = Double.valueOf(stringRight);
                    }else if(strings[1].toLowerCase().contains("<")){
                        symbol = "<";
                        String[] strings1 = strings[1].replace(" ","").split("<");
                        stringLeft = new StringConversion().getString("Character",strings1[0],player);
                        stringRight = new StringConversion().getString("Character",strings1[1],player);
                        left = Double.valueOf(stringLeft);
                        right = Double.valueOf(stringRight);
                    }else if(strings[1].toLowerCase().contains("=")){
                        symbol = "=";
                        String[] strings1 = strings[1].replace(" ","").split("=");
                        stringLeft = new StringConversion().getString("Character",strings1[0],player);
                        stringRight = new StringConversion().getString("Character",strings1[1],player);
                        left = Double.valueOf(stringLeft);
                        right = Double.valueOf(stringRight);
                    }
                }
            }
        }catch (NumberFormatException exception){

        }

    }

    public boolean get(){
        boolean b = false;
        if(symbol.contains("<")){
            if(left < right){
                b = true;

            }

        }
        if(symbol.contains(">")){
            if(left > right){
                b = true;

            }

        }
        if(symbol.contains("=")){
            if(left == right){
                b = true;

            }

        }
        return b;
    }

}
