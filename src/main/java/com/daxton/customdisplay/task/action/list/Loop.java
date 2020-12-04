package com.daxton.customdisplay.task.action.list;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.condition.Condition;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Loop extends BukkitRunnable {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private int ticksRun = 0;

    private Player player;
    private LivingEntity target;
    private String taskID;
    private double damageNumber = 0;

    private String onStart = "";
    private String onTime = "";
    private String onEnd = "";
    private boolean unlimited = true;
    private int period = 1;
    private int duration = 1;

    /**條件判斷**/
    private static Map<String,Condition> conditionMap = new HashMap<>();

    public Loop(){

    }

    public void onLoop(Player player, LivingEntity target,String firstString, double damageNumber,String taskID){
        this.taskID = taskID;
        this.player = player;
        this.target = target;
        this.damageNumber = damageNumber;
        setLoop(firstString);
        onStart();
        this.runTaskTimer(cd,0, period);
    }

    public void onLoop(Player player,String firstString ,String taskID){
        this.taskID = taskID;
        this.player = player;
        setLoop(firstString);
        onStart();
        this.runTaskTimer(cd,0, period);
    }

    public void setLoop(String firstString){
        List<String> stringList = new StringFind().getStringList(firstString);
        for(String string1 : stringList){

            if(string1.toLowerCase().contains("onstart=")){
                String[] strings = string1.split("=");
                onStart = strings[1];
            }

            if(string1.toLowerCase().contains("ontime=")){
                String[] strings = string1.split("=");
                onTime = strings[1];
            }

            if(string1.toLowerCase().contains("onend=")){
                String[] strings = string1.split("=");
                onEnd = strings[1];
            }

            if(string1.toLowerCase().contains("period=")){
                String[] strings = string1.split("=");
                period = Integer.valueOf(strings[1]);
            }

            if(string1.toLowerCase().contains("duration=")){
                String[] strings = string1.split("=");
                if(strings[1].toLowerCase().contains("unlimited")){
                    unlimited = false;
                }else {
                    if(strings.length == 2){
                        duration = Integer.valueOf(strings[1]);
                    }
                }
            }
        }

    }

    public void onStart(){
        List<String> stringList = new ConfigFind().getActionKeyList(onStart);
        if(stringList.size() > 0){
            for(String actionString : stringList){
                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }
                gogo(actionString);
            }
        }
    }

    public void onTime(){
        List<String> stringList = new ConfigFind().getActionKeyList(onTime);
        if(stringList.size() > 0){
            for(String actionString : stringList){
                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }
                gogo(actionString);
            }
        }
    }

    public void onEnd(){
        List<String> stringList = new ConfigFind().getActionKeyList(onEnd);
        if(stringList.size() > 0){
            for(String actionString : stringList){
                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }
                gogo(actionString);
            }
        }
    }

    public void gogo(String actionString){
        if(ActionManager.getLoop_Judgment_Map().get(taskID) == null){
            ActionManager.getLoop_Judgment_Map().put(taskID,new JudgmentAction());
        }
        if(ActionManager.getLoop_Judgment_Map().get(taskID) != null){
            if(target == null){
                ActionManager.getLoop_Judgment_Map().get(taskID).execute(player,actionString,taskID);
            }else {
                ActionManager.getLoop_Judgment_Map().get(taskID).execute(player,target,actionString,damageNumber,taskID);
            }
        }
    }

    public void run(){
        ticksRun = ticksRun + period;

        onTime();
        if(unlimited){
            if(ticksRun > duration){
                onEnd();
                cancel();
            }
        }
    }

    public boolean condition(String actionString){
        boolean b = false;
        if(ConditionManager.getAction_Condition_Map().get(taskID) == null){
            ConditionManager.getAction_Condition_Map().put(taskID,new Condition());
        }
        if(ConditionManager.getLoop_Condition_Map().get(taskID) != null){
            if(target == null){
                ConditionManager.getLoop_Condition_Map().get(taskID).setCondition(player,actionString,taskID);
            }else {
                ConditionManager.getLoop_Condition_Map().get(taskID).setCondition(player,target,actionString,taskID);
            }
            b = ConditionManager.getLoop_Condition_Map().get(taskID).getResult();
        }
        return b;
    }

}
