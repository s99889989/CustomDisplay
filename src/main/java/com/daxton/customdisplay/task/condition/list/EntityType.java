package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.StringConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;

public class EntityType {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private String entityType = "";

    private String aims = "";

    public EntityType(){

    }

    public EntityType(LivingEntity self,LivingEntity target,String firstString,String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){

        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
            if(string.toLowerCase().contains("entitytype=")){
                String[] strings = string.replace(" ","").split("=");
                entityType = new StringConversionMain().valueOf(self,target,strings[1]);
            }

        }

    }

    public boolean get(){
        boolean b = false;

        if(aims.toLowerCase().contains("target")){
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
