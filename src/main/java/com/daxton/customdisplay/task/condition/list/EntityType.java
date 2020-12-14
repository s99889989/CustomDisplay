package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class EntityType {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private double damageNumber = 0;
    private String taskID = "";

    private String entityType = "";

    private String messageTarge = "self";

    public EntityType(){

    }

    public EntityType(LivingEntity self,LivingEntity target,String firstString,double damageNumber,String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.damageNumber = damageNumber;
        this.taskID = taskID;
    }

    public EntityType(LivingEntity target ,String firstString){
        this.target = target;
        this.firstString = firstString;
        setOther();
    }

    public void setOther(){

        for(String string1 : new StringFind().getStringList(firstString)){
            if(string1.toLowerCase().contains("messagetarge=") || string1.toLowerCase().contains("mt=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    if(strings[1].toLowerCase().contains("target")){
                        messageTarge = "target";
                    }else {
                        messageTarge = "self";
                    }
                }
            }
            if(string1.toLowerCase().contains("entitytype=")){
                String[] strings = string1.replace(" ","").split("=");
                entityType = strings[1];
            }

        }
    }

    public boolean get(){
        boolean b = false;
        if(messageTarge.toLowerCase().contains("target")){
            entityType = new StringConversion("Character",entityType,target).getResultString();
        }else {
            entityType = new StringConversion("Character",entityType,self).getResultString();
        }
        if(messageTarge.toLowerCase().contains("target")){
            if(entityType.toLowerCase().contains(target.getType().toString().toLowerCase())){
                b = true;
            }
        }else{
            if(entityType.toLowerCase().contains(self.getType().toString().toLowerCase())){
                b = true;
            }
        }

        return b;
    }

}
