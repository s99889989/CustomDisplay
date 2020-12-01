package com.daxton.customdisplay.task.condition.list;

import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class EntityTypeList {

    public EntityTypeList(){

    }

    public boolean findSetEntityTypeList(String string, LivingEntity target){
        boolean b = false;
        List<String> stringList = new ArrayList<>();
        String entityType = "";
        StringTokenizer stringTokenizer = new StringTokenizer(string,"[;] ");
        while (stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }


        for(String string1 : stringList){
            if(string1.toLowerCase().contains("entitytypelist=")){
                String[] strings = string1.split("=");
                entityType = strings[1];
            }
        }

        List<String> stringList2 = ConfigMapManager.getFileConfigurationMap().get("Character_System_EntityType.yml").getStringList(entityType.toLowerCase()+".entityType");
        for(String s : stringList2){
            if(s.toLowerCase().contains(target.getType().toString().toLowerCase())){
                b = true;
            }
        }

        return b;
    }
}
