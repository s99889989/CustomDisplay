package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.util.ContentUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class Condition {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Map<UUID,Double> targetUUIDMap = new HashMap<>();

    private Player player;
    /**條件判斷**/
    public Condition(){

    }

    public boolean getResuult(String firstString, LivingEntity target, Player player){
        this.player = player;
        boolean b = false;
        if(firstString.toLowerCase().contains("entitytype=")){
            b = findSetEntityTypeList(firstString,target);
        }
        if(firstString.toLowerCase().contains("health=targetchange")){
            b = healthChange(firstString,target,player);
        }

        return b;
    }


    public boolean healthChange(String string,LivingEntity target, Player player){
        boolean b = false;
        if(string.toLowerCase().contains("health=targetchange")){
            b = true;
        }

        return b;
    }

    /**判斷類型**/
    public boolean findSetEntityTypeList(String string,LivingEntity target){
        boolean b = false;
        List<String> stringList = new ArrayList<>();
        String entityType = "";
        StringTokenizer stringTokenizer = new StringTokenizer(string,"[;] ");
        while (stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }


        for(String string1 : stringList){
            if(string1.toLowerCase().contains("entitytype=")){
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

    /**條件判斷**/
    public boolean condition(String string){
        boolean b = false;
        String string1 = string.replace("Condition[","").replace("condition[","").replace("]","");
        if(string1.contains("<")){

            String[] strings1 = string1.split("<");
            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]) < Double.valueOf(strings1[1]))){
                b = true;
            }
        }
        if(string1.contains(">")){
            String[] strings1 = string1.split(">");
            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]) > Double.valueOf(strings1[1]))){
                b = true;
            }
        }
        if(string1.contains("=")){
            String[] strings1 = string1.split("=");
            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]).equals(Double.valueOf(strings1[1])))){
                b = true;
            }
        }

        return b;
    }

}
