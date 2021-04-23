package com.daxton.customdisplay.task.action2.meta;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.condition2.Compare2;
import com.daxton.customdisplay.task.condition2.Contains2;
import com.daxton.customdisplay.task.condition2.Equals2;
import com.daxton.customdisplay.task.condition2.HealthChange2;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class Break {

    private boolean result = false;

    public Break(){}

    public Break(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        boolean mode = actionMapHandle.getBoolean(new String[]{"Mode","m"}, false);

        String conditionType = actionMapHandle.getString(new String[]{"ConditionType","ct"}, "");

        String conditionContent = actionMapHandle.getString(new String[]{"ConditionContent","cp"}, "");


        switch (conditionType.toLowerCase()){
            case "compare":
                result = new Compare2(self, target, conditionContent, taskID).isResult();
                break;
            case "contains":
                result = new Contains2(self, target, conditionContent, taskID).isResult();
                break;
            case "equals":
                result = new Equals2(self, target, conditionContent, taskID).isResult();
                break;
            case "healthchange":
                if(ActionManager.condition_HealthChange_Map.get(taskID) == null){
                    ActionManager.condition_HealthChange_Map.put(taskID, new HealthChange2(self, target, conditionContent, taskID));
                    ActionManager.condition_HealthChange_Map.get(taskID).chickHealth();
                    result = ActionManager.condition_HealthChange_Map.get(taskID).isResult();
                }else {
                    ActionManager.condition_HealthChange_Map.get(taskID).chickHealth();
                    result = ActionManager.condition_HealthChange_Map.get(taskID).isResult();
                }
                break;
        }

        if(mode){
            if(result){
                result = false;
            }else {
                result = true;
            }
        }


    }

    public boolean isResult() {
        return result;
    }
}
