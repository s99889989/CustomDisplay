package com.daxton.customdisplay.task.action.list;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.ConfigFind;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.task.condition.Condition;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loop extends BukkitRunnable {

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
    private BukkitRunnable bukkitRunnableStart;
    private BukkitRunnable bukkitRunnableTime;
    private BukkitRunnable bukkitRunnableEnd;



    /**條件判斷**/
    private static Map<String,Condition> conditionMap = new HashMap<>();

    public Loop(){

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
            int delay = 0;
            for(String actionString : stringList){
                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }
                if(actionString.toLowerCase().contains("delay")){
                    String[] strings = actionString.replace(" ","").toLowerCase().split("=");
                    if(strings.length == 2){
                        try{
                            delay = delay + Integer.valueOf(strings[1]);
                        }catch (NumberFormatException exception){

                        }
                    }
                }
                bukkitRunnableStart = new BukkitRunnable() {
                    @Override
                    public void run() {
                        gogo(actionString);
                    }
                };
                bukkitRunnableStart.runTaskLater(cd,delay);
            }

            if(ActionManager.getOther_Judgment_Map().get(taskID) != null){
                ActionManager.getOther_Judgment_Map().remove(taskID);
            }
        }
    }

    public void onTime(){
        List<String> stringList = new ConfigFind().getActionKeyList(onTime);
        if(stringList.size() > 0){
            int delay = 0;
            for(String actionString : stringList){

                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }
                if(actionString.toLowerCase().contains("delay")){
                    String[] strings = actionString.replace(" ","").toLowerCase().split("=");
                    if(strings.length == 2){
                        try{
                            delay = delay + Integer.valueOf(strings[1]);
                        }catch (NumberFormatException exception){

                        }
                    }
                }

                bukkitRunnableTime = new BukkitRunnable() {
                    @Override
                    public void run() {

                        gogo(actionString);
                    }
                };
                bukkitRunnableTime.runTaskLater(cd,delay);
            }


            if(ActionManager.getOther_Judgment_Map().get(taskID) != null){
                ActionManager.getOther_Judgment_Map().remove(taskID);
            }
        }
    }

    public void onEnd(){
        List<String> stringList = new ConfigFind().getActionKeyList(onEnd);
        if(stringList.size() > 0){
            int delay = 0;
            if(ActionManager.getJudgment2_Loop2_Map().get(taskID) != null){
                ActionManager.getJudgment2_Loop2_Map().remove(taskID);
            }
            for(String actionString : stringList){
                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }
                if(actionString.toLowerCase().contains("delay")){
                    String[] strings = actionString.replace(" ","").toLowerCase().split("=");
                    if(strings.length == 2){
                        try{
                            delay = delay + Integer.valueOf(strings[1]);
                        }catch (NumberFormatException exception){

                        }
                    }
                }
                bukkitRunnableEnd = new BukkitRunnable() {
                    @Override
                    public void run() {
                        gogo(actionString);
                    }
                };
                bukkitRunnableEnd.runTaskLater(cd,delay);

            }

            if(ActionManager.getOther_Judgment_Map().get(taskID) != null){
                ActionManager.getOther_Judgment_Map().remove(taskID);
            }
        }
        if(ConditionManager.getAction_Condition_Map().get(taskID) != null){
            ConditionManager.getAction_Condition_Map().remove(taskID);
        }
    }

    public void gogo(String actionString){

        if(ActionManager.getOther_Judgment_Map().get(taskID) == null){
            ActionManager.getOther_Judgment_Map().put(taskID,new JudgmentAction());
            ActionManager.getOther_Judgment_Map().get(taskID).execute(self,target,actionString,taskID);
        }else {
            ActionManager.getOther_Judgment_Map().get(taskID).execute(self,target,actionString,taskID);
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
        if(b){
            ConditionManager.getAction_Condition_Map().remove(taskID);
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
