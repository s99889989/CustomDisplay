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

    private String messageTarge = "self";

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
        cd.getLogger().info("c:"+firstString);
        this.player = player;
        this.firstString = firstString;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){

        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("messagetarge=") || string.toLowerCase().contains("mt=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    if(strings[1].toLowerCase().contains("target")){
                        messageTarge = "target";
                    }else {
                        messageTarge = "self";
                    }
                }
            }
        }


        try{

            for(String string1 : new StringFind().getStringMessageList(firstString)){
                if(string1.toLowerCase().contains("compare=")){
                    String[] strings = string1.split("=");
                    if(strings[1].toLowerCase().contains(">")){
                        symbol = ">";
                        String[] strings1 = strings[1].replace(" ","").split(">");
                        if(messageTarge.toLowerCase().contains("target")){
                            stringLeft = new StringConversion("Character",strings1[0],target).getResultString();
                            stringRight = new StringConversion("Character",strings1[1],target).getResultString();
                        }else {
                            stringLeft = new StringConversion("Character",strings1[0],player).getResultString();
                            stringRight = new StringConversion("Character",strings1[1],player).getResultString();
                        }
                        left = Double.valueOf(stringLeft);
                        right = Double.valueOf(stringRight);
                    }else if(strings[1].toLowerCase().contains("<")){
                        symbol = "<";
                        String[] strings1 = strings[1].replace(" ","").split("<");
                        if(messageTarge.toLowerCase().contains("target")){
                            stringLeft = new StringConversion("Character",strings1[0],target).getResultString();
                            stringRight = new StringConversion("Character",strings1[1],target).getResultString();
                        }else {
                            stringLeft = new StringConversion("Character",strings1[0],player).getResultString();
                            stringRight = new StringConversion("Character",strings1[1],player).getResultString();
                        }
                        left = Double.valueOf(stringLeft);
                        right = Double.valueOf(stringRight);
                    }else if(strings[1].toLowerCase().contains("=")){
                        symbol = "=";
                        String[] strings1 = strings[1].replace(" ","").split("=");
                        if(messageTarge.toLowerCase().contains("target")){
                            stringLeft = new StringConversion("Character",strings1[0],target).getResultString();
                            stringRight = new StringConversion("Character",strings1[1],target).getResultString();
                        }else {
                            stringLeft = new StringConversion("Character",strings1[0],player).getResultString();
                            stringRight = new StringConversion("Character",strings1[1],player).getResultString();
                        }
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
