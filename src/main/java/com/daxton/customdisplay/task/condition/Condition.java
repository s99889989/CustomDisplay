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

    public void setCondition(LivingEntity self,LivingEntity target,String firstString,double damageNumber,String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.damageNumber = damageNumber;
        this.taskID = taskID;
        if(self instanceof Player){
            player = (Player) self;
        }
    }

    /**有目標的判斷**/
    public void setCondition(Player player,LivingEntity target,String firstString,String taskID){
        this.player = player;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
    }

    /**沒有目標的判斷**/
    public void setCondition(Player player,String firstString,String taskID){
        this.player = player;
        this.firstString = firstString;
        this.taskID = taskID;

    }

    public boolean getResult2(){
        boolean b = false;

        if(firstString.toLowerCase().contains("compare=")){
            b = new Compare(self,target,firstString,damageNumber,taskID).get();
        }

        if(firstString.toLowerCase().contains("entitytypelist=")){
            b = new EntityTypeList(self,target,firstString,damageNumber,taskID).get();
        }

        if(firstString.toLowerCase().contains("entitytype=")){
            b = new EntityType(self,target,firstString,damageNumber,taskID).get();
        }

        return b;
    }


    public boolean getResult(){
        boolean b = false;

        if(firstString.toLowerCase().contains("compare=")){

            if(target == null){
                b = new Compare(player,firstString).get();
            }else {
                b = new Compare(player,target,firstString).get();
            }
        }
        if(firstString.toLowerCase().contains("entitytypelist=")){
            b = new EntityTypeList(target,firstString).get();
        }
        if(firstString.toLowerCase().contains("entitytype=")){
            b = new EntityType(target,firstString).get();
        }
        if(firstString.toLowerCase().contains("health=")){
            if(ConditionManager.getCondition_Health_Map().get(taskID) == null){
                ConditionManager.getCondition_Health_Map().put(taskID,new Health());
            }
            if(ConditionManager.getCondition_Health_Map().get(taskID) != null){
                if(target == null){
                    ConditionManager.getCondition_Health_Map().get(taskID).setHealth(player,firstString,taskID);
                }else {
                    ConditionManager.getCondition_Health_Map().get(taskID).setHealth(player,target,firstString,taskID);
                }
                b = ConditionManager.getCondition_Health_Map().get(taskID).get();
            }
        }
        return b;
    }


}
