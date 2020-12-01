package com.daxton.customdisplay.task.condition;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.task.condition.list.Compare;
import com.daxton.customdisplay.task.condition.list.EntityTypeList;
import com.daxton.customdisplay.task.condition.list.Health;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class Condition {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private static Map<String, Health> healthMap = new HashMap<>();

    /**條件判斷**/
    public Condition(){

    }

    public boolean getResuult(String firstString, LivingEntity target, Player player,String taskID){

        this.player = player;
        boolean b = false;
        if(firstString.toLowerCase().contains("entitytype=")){
            b = findSetEntityTypeList(firstString,target);
        }
        if(firstString.toLowerCase().contains("health=")){
            if(healthMap.get(taskID) == null){
                healthMap.put(taskID,new Health());
                if(healthMap.get(taskID).judgment(firstString,target,player,taskID)){
                    b = true;
                }
            }else {
                if(healthMap.get(taskID).judgment(firstString,target,player,taskID)){
                    b = true;
                }
            }
        }
        if(firstString.toLowerCase().contains("compare=")){
            b = new Compare().judgment(firstString,target,player);
        }
        if(firstString.toLowerCase().contains("entitytypelist=")){
            b = !(new EntityTypeList().findSetEntityTypeList(firstString,target));
        }

        return b;
    }

    public boolean getResuult(String firstString, Player player,String taskID){
        this.player = player;
        boolean b = false;
//        if(firstString.toLowerCase().contains("entitytype=")){
//            b = findSetEntityTypeList(firstString,player);
//        }
//        if(firstString.toLowerCase().contains("health=")){
//            if(healthMap.get(taskID) == null){
//                healthMap.put(taskID,new Health());
//                if(healthMap.get(taskID).judgment(firstString,player,taskID)){
//                    b = true;
//                }
//            }else {
//                if(healthMap.get(taskID).judgment(firstString,player,taskID)){
//                    b = true;
//                }
//            }
//        }
        if(firstString.toLowerCase().contains("compare=")){
            b = new Compare().judgment(firstString,player);
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


}
