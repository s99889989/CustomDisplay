package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class Compare {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player = null;

    private LivingEntity self = null;
    private LivingEntity target = null;
    private double damageNumber = 0;
    private String firstString = "";
    private String taskID = "";

    private String messageTarge = "self";
    private double left = 0;
    private double right = 0;
    private String symbol = "";
    private String stringLeft = "";
    private String stringRight = "";

    public Compare(){

    }

    public Compare(LivingEntity self,LivingEntity target,String firstString,double damageNumber,String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.damageNumber = damageNumber;
        this.taskID = taskID;
        if(self instanceof Player){
            player = (Player) self;
        }
        setOther2();
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

    public void setOther2(){
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
                            stringLeft = new StringConversion("Character",strings1[0],self).getResultString();
                            stringRight = new StringConversion("Character",strings1[1],self).getResultString();
                        }
                    }else if(strings[1].toLowerCase().contains("<")){
                        symbol = "<";
                        String[] strings1 = strings[1].replace(" ","").split("<");
                        if(messageTarge.toLowerCase().contains("target")){
                            stringLeft = new StringConversion("Character",strings1[0],target).getResultString();
                            stringRight = new StringConversion("Character",strings1[1],target).getResultString();
                        }else {
                            stringLeft = new StringConversion("Character",strings1[0],self).getResultString();
                            stringRight = new StringConversion("Character",strings1[1],self).getResultString();
                        }
                    }else if(strings[1].toLowerCase().contains("=")){
                        symbol = "=";
                        String[] strings1 = strings[1].replace(" ","").split("=");
                        if(messageTarge.toLowerCase().contains("target")){
                            stringLeft = new StringConversion("Character",strings1[0],target).getResultString();
                            stringRight = new StringConversion("Character",strings1[1],target).getResultString();
                        }else {
                            stringLeft = new StringConversion("Character",strings1[0],self).getResultString();
                            stringRight = new StringConversion("Character",strings1[1],self).getResultString();
                        }
                    }
                    left = Double.valueOf(stringLeft);
                    right = Double.valueOf(stringRight);
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
