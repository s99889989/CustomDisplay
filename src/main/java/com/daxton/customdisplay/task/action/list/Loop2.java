package com.daxton.customdisplay.task.action.list;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.task.condition.Condition;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
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
        this.taskID = taskID;
        this.self = self;
        this.firstString = firstString;
        this.target = target;
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

                gogo(actionString);
            }
        }
    }

    public void onTime(){
        List<String> stringList = new ConfigFind().getActionKeyList(onTime);
        if(stringList.size() > 0){
            for(String actionString : stringList){


                gogo(actionString);
            }
        }
    }

    public void onEnd(){
        List<String> stringList = new ConfigFind().getActionKeyList(onEnd);
        if(stringList.size() > 0){
            for(String actionString : stringList){


                if(ActionManager.getJudgment_Loop_Map().get(taskID) != null){
                    ActionManager.getJudgment_Loop_Map().remove(taskID);
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



}
