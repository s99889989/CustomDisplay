package com.daxton.customdisplay.task.condition;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.task.condition.list.Compare;
import com.daxton.customdisplay.task.condition.list.EntityType;
import com.daxton.customdisplay.task.condition.list.EntityTypeList;
import com.daxton.customdisplay.task.condition.list.Health;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class Condition {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player = null;

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private double damageNumber = 0;
    private String taskID = "";

    private static Map<String, Health> healthMap = new HashMap<>();

    /**條件判斷**/
    public Condition(){

    }

    public void setCondition(LivingEntity self,LivingEntity target,String firstString,String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
    }

    public boolean getResult2(){
        boolean b = false;

        if(firstString.toLowerCase().contains("compare=")){
            b = new Compare(self,target,firstString,taskID).get();
        }

        if(firstString.toLowerCase().contains("entitytypelist=")){
            b = new EntityTypeList(self,target,firstString,taskID).get();
        }

        if(firstString.toLowerCase().contains("entitytype=")){
            b = new EntityType(self,target,firstString,taskID).get();
        }

        if(firstString.toLowerCase().contains("health=")){
            if(ConditionManager.getCondition_Health_Map().get(taskID) == null){
                ConditionManager.getCondition_Health_Map().put(taskID,new Health());
            }
            if(ConditionManager.getCondition_Health_Map().get(taskID) != null){
                ConditionManager.getCondition_Health_Map().get(taskID).setHealth(self,target,firstString,taskID);
                b = ConditionManager.getCondition_Health_Map().get(taskID).get();
            }
        }

        return b;
    }


}
