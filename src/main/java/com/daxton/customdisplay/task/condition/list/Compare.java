package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.StringConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Compare {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player = null;
    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private double left = 0;
    private double right = 0;
    private String symbol = "";
    private String stringLeft = "";
    private String stringRight = "";

    public Compare(){

    }

    public Compare(LivingEntity self,LivingEntity target,String firstString,String taskID){

        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){
        for(String string1 : new StringFind().getStringMessageList(firstString)){
            if(string1.toLowerCase().contains("compare=")){
                String[] strings = string1.split("=");
                if(strings[1].toLowerCase().contains(">")){
                    symbol = ">";
                    String[] strings1 = strings[1].replace(" ","").split(">");
                    stringLeft = new StringConversionMain().valueOf(self,target,strings1[0]);
                    stringRight = new StringConversionMain().valueOf(self,target,strings1[1]);
                }else if(strings[1].toLowerCase().contains("<")){
                    symbol = "<";
                    String[] strings1 = strings[1].replace(" ","").split("<");
                    stringLeft = new StringConversionMain().valueOf(self,target,strings1[0]);
                    stringRight = new StringConversionMain().valueOf(self,target,strings1[1]);
                }else if(strings[1].toLowerCase().contains("~")){
                    symbol = "=";
                    String[] strings1 = strings[1].replace(" ","").split("~");
                    stringLeft = new StringConversionMain().valueOf(self,target,strings1[0]);
                    stringRight = new StringConversionMain().valueOf(self,target,strings1[1]);
                }
            }
        }

        try {
            left = Double.valueOf(stringLeft);
            right = Double.valueOf(stringRight);
        }catch (NumberFormatException exceptione){

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
