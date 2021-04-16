package com.daxton.customdisplay.manager;

import com.daxton.customdisplay.task.condition.list.Health;

import java.util.HashMap;
import java.util.Map;

public class ConditionManager {



    /**Condition->Health**/
    private static Map<String, Health> condition_Health_Map = new HashMap<>();



    /**Condition->Health**/
    public static Map<String, Health> getCondition_Health_Map() {
        return condition_Health_Map;
    }
}
