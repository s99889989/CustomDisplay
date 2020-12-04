package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.task.condition.Condition;
import com.daxton.customdisplay.task.condition.list.Health;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConditionManager {


    /**Action->Condition**/
    private static Map<String, Condition> action_Condition_Map = new HashMap<>();
    /**Loop->Condition**/
    private static Map<String, Condition> loop_Condition_Map = new HashMap<>();
    /**Condition->Health**/
    private static Map<String, Health> condition_Health_Map = new HashMap<>();


    /**Action->Condition**/
    public static Map<String, Condition> getAction_Condition_Map() {
        return action_Condition_Map;
    }
    /**Loop->Condition**/
    public static Map<String, Condition> getLoop_Condition_Map() {
        return loop_Condition_Map;
    }
    /**Condition->Health**/
    public static Map<String, Health> getCondition_Health_Map() {
        return condition_Health_Map;
    }
}
