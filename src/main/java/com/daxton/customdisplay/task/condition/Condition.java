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

    private LivingEntity target;

    private String firstString = "";

    private String taskID = "";

    private static Map<String, Health> healthMap = new HashMap<>();

    /**條件判斷**/
    public Condition(){

    }


    /**有目標的判斷**/
    public Condition(Player player,LivingEntity target,String firstString,String taskID){
        this.player = player;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
    }
    /**沒有目標的判斷**/
    public Condition(Player player,String firstString,String taskID){
        this.player = player;
        this.firstString = firstString;
        this.taskID = taskID;
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
        return b;
    }


}
