package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ConfigFind;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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

    public LoopOne(){

    }

    public void onLoop(Player player,String firstString ,String taskID){
        this.taskID = taskID;
        this.player = player;
        setLoop(firstString);

        player.sendMessage("loopOne:"+firstString);

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

            }
        }
    }

    public void onTime(){
        List<String> stringList = new ConfigFind().getActionKeyList(onTime);
        if(stringList.size() > 0){
            for(String actionString : stringList){
                player.sendMessage("t:"+actionString);
            }
        }
    }

    public void onEnd(){
        List<String> stringList = new ConfigFind().getActionKeyList(onEnd);
        if(stringList.size() > 0){
            for(String actionString : stringList){

            }
        }
    }

    public void setLoop(String firstString){
        List<String> stringList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[;]");
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

}
