package com.daxton.customdisplay.task.action.list;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.task.action.JudgmentAction2;
import com.daxton.customdisplay.task.condition.Condition;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loop2 extends BukkitRunnable {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private int ticksRun = 0;
    private int period = 1;
    private int duration = 0;

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";


    private String onStart = "";
    private String onTime = "";
    private String onEnd = "";
    private boolean unlimited = true;


    /**條件判斷**/
    private static Map<String,Condition> conditionMap = new HashMap<>();

    public Loop2(){

    }

    public void onLoop(LivingEntity self, LivingEntity target,String firstString,String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        setLoop();
        onStart();
        this.runTaskTimer(cd,0, period);
    }

    public void setLoop(){
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
            if(ActionManager2.getJudgment2_Loop2_Map().get(taskID) != null){
                ActionManager2.getJudgment2_Loop2_Map().remove(taskID);
            }
            for(String actionString : stringList){
                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }
                gogo(actionString);
            }
            if(ActionManager2.getLoop2_Judgment2_Map().get(taskID) != null){
                ActionManager2.getLoop2_Judgment2_Map().remove(taskID);
            }
            if(ConditionManager.getAction_Condition_Map().get(taskID) != null){
                ConditionManager.getAction_Condition_Map().remove(taskID);
            }

        }
    }

    public void gogo(String actionString){
        if(ActionManager2.getLoop2_Judgment2_Map().get(taskID) == null){
            ActionManager2.getLoop2_Judgment2_Map().put(taskID,new JudgmentAction2());
        }
        if(ActionManager2.getLoop2_Judgment2_Map().get(taskID) != null){
            ActionManager2.getLoop2_Judgment2_Map().get(taskID).execute(self,target,actionString,taskID);
        }
    }

    public boolean condition(String actionString){

        boolean b = false;
        if(ConditionManager.getAction_Condition_Map().get(taskID) == null){
            ConditionManager.getAction_Condition_Map().put(taskID,new Condition());
        }
        if(ConditionManager.getAction_Condition_Map().get(taskID) != null){
            ConditionManager.getAction_Condition_Map().get(taskID).setCondition(self,target,actionString,taskID);
            b = ConditionManager.getAction_Condition_Map().get(taskID).getResult2();
        }
        return b;
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



}
