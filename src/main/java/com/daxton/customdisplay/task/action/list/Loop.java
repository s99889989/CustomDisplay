package com.daxton.customdisplay.task.action.list;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.condition.Condition;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Loop extends BukkitRunnable {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID;

    private Location location;

    private Map<String,String> actionMap = new HashMap<>();

    private Map<String,List<String>> actionListMap = new HashMap<>();

    private int ticksRun = 0;

    private int period = 1;

    private int duration = 1;

    private Player player;

    private LivingEntity target;

    private boolean unlimited = true;

    private double damageNumber = 0;

    /**條件判斷**/
    private static Map<String,Condition> conditionMap = new HashMap<>();

    public Loop(){

    }

    public void onLoop(Player player, LivingEntity target,String firstString, double damageNumber,String taskID){
        this.taskID = taskID;
        this.player = player;
        this.target = target;
        this.damageNumber = damageNumber;
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[;]");
        while (stringTokenizer.hasMoreElements()){
            stringList.add(stringTokenizer.nextToken());
        }
        for(String string1 : stringList){

            if(string1.toLowerCase().contains("onstart=")){
                String[] strings = string1.split("=");
                actionListMap.put(strings[0].toLowerCase(), new ConfigFind().getActionKeyList(strings[1]));
            }

            if(string1.toLowerCase().contains("ontime=")){
                String[] strings = string1.split("=");
                actionListMap.put(strings[0].toLowerCase(), new ConfigFind().getActionKeyList(strings[1]));
            }

            if(string1.toLowerCase().contains("onend=")){
                String[] strings = string1.split("=");
                actionListMap.put(strings[0].toLowerCase(), new ConfigFind().getActionKeyList(strings[1]));
            }

            if(string1.toLowerCase().contains("period=")){
                String[] strings = string1.split("=");
                actionMap.put(strings[0].toLowerCase(),strings[1]);
            }

            if(string1.toLowerCase().contains("duration=")){
                String[] strings = string1.split("=");
                if(strings[1].toLowerCase().contains("unlimited")){
                    unlimited = false;
                }else {
                    actionMap.put(strings[0].toLowerCase(),strings[1]);
                }
            }
        }
        this.duration = Integer.valueOf(actionMap.get("duration"));
        this.period = Integer.valueOf(actionMap.get("period"));


        if(actionListMap.get("ontime") == null || actionListMap.get("onend") == null){
            return;

        }



        if(actionListMap.get("onstart") != null){
            onStart();
        }

        this.runTaskTimer(cd,0, period);

    }



    public void onStart(){
        try{
            List<String> stringList = actionListMap.get("onstart");
            if(stringList.size() > 0){
                for(String actionString : stringList){
                    if(condition(actionString)){
                        return;
                    }
                    if(TriggerManager.getLoopTaskMap().get(taskID) == null){
                        TriggerManager.getLoopTaskMap().put(taskID,new JudgmentAction());
                    }
                    if(TriggerManager.getLoopTaskMap().get(taskID) != null){
                        TriggerManager.getLoopTaskMap().get(taskID).execute(player,target,actionString,damageNumber,taskID);
                    }
                }
            }
        }catch (NullPointerException exception){
            if(player.isOnline()){
                player.sendMessage("onStart=不能為空或設定錯誤");
            }
            cd.getLogger().info(actionMap.get("onStart=不能為空或設定錯誤"));
            cancel();
        }
    }

    public void onTime(){

        try{
            List<String> stringList = actionListMap.get("ontime");
            if(stringList.size() > 0){
                for(String actionString : stringList){
                    player.sendMessage("loop"+actionString);
//                    if(condition(actionString)){
//                        return;
//                    }
//                    if(TriggerManager.getLoopTaskMap().get(taskID) != null){
//                        TriggerManager.getLoopTaskMap().get(taskID).execute(player,target,actionString,damageNumber,taskID);
//                    }
                }
            }
        }catch (NullPointerException exception){
            if(player.isOnline()){
                player.sendMessage("onTime=不能為空或設定錯誤");
            }
            cd.getLogger().info(actionMap.get("onTime=不能為空或設定錯誤"));
            cancel();
        }

    }

    public void onEnd(){
        try{
            List<String> stringList = actionListMap.get("onend");
            if(stringList.size() > 0){
                for(String actionString : stringList){
                    if(condition(actionString)){
                        return;
                    }
                    if(TriggerManager.getLoopTaskMap().get(taskID) != null){
                        TriggerManager.getLoopTaskMap().get(taskID).execute(player,target,actionString,damageNumber,taskID);
                    }
                }
            }
        }catch (NullPointerException exception){
            if(player.isOnline()){
                player.sendMessage("onEnd=不能為空或設定錯誤");
            }
            cd.getLogger().info(actionMap.get("onEnd=不能為空或設定錯誤"));
            cancel();
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
        if(actionString.toLowerCase().contains("condition") && conditionMap.get(taskID) == null){
            conditionMap.put(taskID,new Condition());
            if(conditionMap.get(taskID).getResuult(actionString,target,player,taskID)){
                b = true;
            }

        }
        if(actionString.toLowerCase().contains("condition") && conditionMap.get(taskID) != null){
            if(conditionMap.get(taskID).getResuult(actionString,target,player,taskID)){
                b = true;
            }
        }
        return b;
    }

}
