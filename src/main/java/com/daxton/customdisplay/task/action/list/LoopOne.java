package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.task.condition.Condition;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class LoopOne extends BukkitRunnable {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private int ticksRun = 0;

    private String taskID;
    private Player player;

    private String onStart = "";
    private String onTime = "";
    private String onEnd = "";
    private boolean unlimited = true;
    private int period = 1;
    private int duration = 1;

    /**條件判斷**/
    private static Map<String, Condition> conditionMap = new HashMap<>();

    public LoopOne(){

    }

    public void onLoop(Player player,String firstString ,String taskID){
        this.taskID = taskID;
        this.player = player;
        setLoop(firstString);
        onStart();
        this.runTaskTimer(cd,0, period);
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

    public void onStart(){
        List<String> stringList = new ConfigFind().getActionKeyList(onStart);
        if(stringList.size() > 0){
            for(String actionString : stringList){
                if(condition(actionString)){
                    return;
                }
                if(TriggerManager.getLoop_Judgment_Map().get(taskID) == null){
                    TriggerManager.getLoop_Judgment_Map().put(taskID,new JudgmentAction());
                }
                if(TriggerManager.getLoop_Judgment_Map().get(taskID) != null){
                    TriggerManager.getLoop_Judgment_Map().get(taskID).execute(player,actionString,taskID);
                }
            }
        }
    }

    public void onTime(){
        List<String> stringList = new ConfigFind().getActionKeyList(onTime);
        if(stringList.size() > 0){
            for(String actionString : stringList){
                if(condition(actionString)){
                    return;
                }
                if(TriggerManager.getLoop_Judgment_Map().get(taskID) == null){
                    TriggerManager.getLoop_Judgment_Map().put(taskID,new JudgmentAction());
                }
                if(TriggerManager.getLoop_Judgment_Map().get(taskID) != null){
                    TriggerManager.getLoop_Judgment_Map().get(taskID).execute(player,actionString,taskID);
                }
            }
        }
    }

    public void onEnd(){
        List<String> stringList = new ConfigFind().getActionKeyList(onEnd);
        if(stringList.size() > 0){
            for(String actionString : stringList){
                if(condition(actionString)){
                    return;
                }
                if(TriggerManager.getLoop_Judgment_Map().get(taskID) == null){
                    TriggerManager.getLoop_Judgment_Map().put(taskID,new JudgmentAction());
                }
                if(TriggerManager.getLoop_Judgment_Map().get(taskID) != null){
                    TriggerManager.getLoop_Judgment_Map().get(taskID).execute(player,actionString,taskID);
                }
            }
        }
    }

    public void setLoop(String firstString){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[;] ");
        while (stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        for(String string1 : stringList){

            if(string1.toLowerCase().contains("onstart=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    onStart = strings[1];
                }
            }

            if(string1.toLowerCase().contains("ontime=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    onTime = strings[1];
                }
            }

            if(string1.toLowerCase().contains("onend=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    onEnd = strings[1];
                }
            }

            if(string1.toLowerCase().contains("period=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    period = Integer.valueOf(strings[1]);
                }
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

    public boolean condition(String actionString){
        boolean b = false;
        if(actionString.toLowerCase().contains("condition") && conditionMap.get(taskID) == null){
            conditionMap.put(taskID,new Condition());
            if(conditionMap.get(taskID).getResuult(actionString,player,taskID)){
                b = true;
            }
        }
        if(actionString.toLowerCase().contains("condition") && conditionMap.get(taskID) != null){
            if(conditionMap.get(taskID).getResuult(actionString,player,taskID)){
                b = true;
            }
        }
        return b;
    }


}
