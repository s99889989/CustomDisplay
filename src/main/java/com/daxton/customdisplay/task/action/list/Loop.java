package com.daxton.customdisplay.task.action.list;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.manager.player.TriggerManager;
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

    private double damageNumber = 0;

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
                actionMap.put(strings[0].toLowerCase(),strings[1]);
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
            for(String actionString : stringList){
                TriggerManager.getLoopTaskMap().put(taskID,new JudgmentAction());
                if(TriggerManager.getLoopTaskMap().get(taskID) != null){
                    TriggerManager.getLoopTaskMap().get(taskID).execute(player,target,actionString,damageNumber,taskID);
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
            for(String actionString : stringList){
                if(actionString.toLowerCase().contains("condition") && new Condition().getResuult(actionString,target,player)){
                    return;
                }
                player.sendMessage(actionString+"判斷:"+new Condition().getResuult(actionString,target,player));
                if(TriggerManager.getLoopTaskMap().get(taskID) != null){
                    TriggerManager.getLoopTaskMap().get(taskID).execute(player,target,actionString,damageNumber,taskID);
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
            for(String actionString : stringList){
                if(TriggerManager.getLoopTaskMap().get(taskID) != null){
                    TriggerManager.getLoopTaskMap().get(taskID).execute(player,target,actionString,damageNumber,taskID);
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

        if(ticksRun > duration){
            onEnd();
            cancel();
        }
    }

}
