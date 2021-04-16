package com.daxton.customdisplay.task.condition;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.task.condition.list.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Condition2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player = null;

    private LivingEntity self = null;
    private LivingEntity target = null;
    private CustomLineConfig customLineConfig;
    private double damageNumber = 0;
    private String taskID = "";

    private static Map<String, Health> healthMap = new HashMap<>();

    /**條件判斷**/
    public Condition2(){

    }

    public void setCondition(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;
        this.taskID = taskID;
    }

    public boolean getResult2(){
        boolean b = false;
        String firstString = customLineConfig.getString(new String[]{"condition"},"",self,target);

        String aimsKey = customLineConfig.getAimsKey();

        if(firstString != null){
            //cd.getLogger().info("條件:" +firstString+" : "+aimsKey);
            if(firstString.toLowerCase().contains("contains=")){
                b = new Contains(self,target,firstString,taskID).get();
            }

            if(firstString.toLowerCase().contains("compare=")){
                b = new Compare(self,target,firstString,taskID).get();
            }

            if(firstString.toLowerCase().contains("entitytypelist=")){
                b = new EntityTypeList(self,target,firstString,taskID).get();
            }

            if(firstString.toLowerCase().contains("mythictypelist=")){
                b = new MythicMobTypeList().valueOf(self,target,firstString,taskID);
            }

            if(firstString.toLowerCase().contains("entitytype=")){
                b = new EntityType(self,target,firstString,taskID).get();
            }

            if(firstString.toLowerCase().contains("health=")){
                if(ConditionManager.getCondition_Health_Map().get(taskID) == null){
                    ConditionManager.getCondition_Health_Map().put(taskID,new Health());
                }
                if(ConditionManager.getCondition_Health_Map().get(taskID) != null){
                    ConditionManager.getCondition_Health_Map().get(taskID).setHealth(self,target,firstString, aimsKey,taskID);
                    b = ConditionManager.getCondition_Health_Map().get(taskID).get();
                }
            }

            if(firstString.toLowerCase().contains("equals=")){
                b = new Equals().setEquals(self,target,firstString,taskID);
            }
        }

        return b;
    }


}
