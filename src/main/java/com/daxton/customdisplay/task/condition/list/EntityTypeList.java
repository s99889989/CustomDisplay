package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class EntityTypeList {

    private LivingEntity target;
    private String firstString = "";
    private String entityType = "";

    public EntityTypeList(){

    }

    public EntityTypeList(LivingEntity target ,String firstString){
        this.target = target;
        this.firstString = firstString;
        setOther();
    }

    public void setOther(){
        List<String> stringList = new StringFind().getStringList(firstString);
        for(String string1 : stringList){
            if(string1.toLowerCase().contains("entitytypelist=")){
                String[] strings = string1.replace(" ","").split("=");
                entityType = strings[1];
            }
        }
    }

    public boolean get(){
        boolean b = false;
        List<String> stringList2 = ConfigMapManager.getFileConfigurationMap().get("Character_System_EntityTypeList.yml").getStringList(entityType+".entityTypeList");
        for(String string : stringList2){
            if(string.toLowerCase().contains(target.getType().toString().toLowerCase())){
                b = true;
            }
        }
        return b;
    }
}
