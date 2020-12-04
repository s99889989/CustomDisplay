package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.entity.LivingEntity;

import java.util.List;

public class EntityType {

    private LivingEntity target;
    private String firstString = "";
    private String entityType = "";

    public EntityType(){

    }

    public EntityType(LivingEntity target ,String firstString){
        this.target = target;
        this.firstString = firstString;
        setOther();
    }

    public void setOther(){
        List<String> stringList = new StringFind().getStringList(firstString);
        for(String string1 : stringList){
            if(string1.toLowerCase().contains("entitytype=")){
                String[] strings = string1.replace(" ","").split("=");
                entityType = strings[1];
            }
        }
    }

    public boolean get(){
        boolean b = false;

        if(entityType.toLowerCase().contains(target.getType().toString().toLowerCase())){
            b = true;
        }

        return b;
    }

}
