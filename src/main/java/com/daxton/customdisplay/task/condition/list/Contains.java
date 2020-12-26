package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
import org.bukkit.entity.LivingEntity;

public class Contains {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private String stringLeft = "";
    private String stringRight = "";

    public Contains(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){
        for(String string : new StringFind().getStringMessageList(firstString)){
            if(string.toLowerCase().contains("contains=")){
                String[] strings = string.split("=");
                if(strings.length == 3){
                    stringLeft = new StringConversion2(self,target,strings[1],"Character").valueConv();
                    stringRight = new StringConversion2(self,target,strings[2],"Character").valueConv();
                }
            }

        }


    }
    public boolean get(){
        boolean b = false;
        if(stringLeft.toLowerCase().contains(stringRight)){
            b = true;
        }

        return b;
    }

}
