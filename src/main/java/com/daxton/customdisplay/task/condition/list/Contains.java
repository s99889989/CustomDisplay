package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;

public class Contains {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private String stringLeft = "0";
    private String stringRight = "1";

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
                    stringLeft = ConversionMain.valueOf(self,target,strings[1]);
                    stringRight = ConversionMain.valueOf(self,target,strings[2]);
                }
            }

        }


    }
    public boolean get(){
        boolean b = false;
        if(stringLeft.contains(stringRight)){
            b = true;
        }
        return b;
    }

}
