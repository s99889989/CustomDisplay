package com.daxton.customdisplay.task.action2.meta;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.JudgmentAction2;
import com.daxton.customdisplay.task.condition.Condition2;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Loop3 extends BukkitRunnable {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private int ticksRun = 0;
    private int period = 1;
    private int duration = 0;

    private LivingEntity self = null;
    private LivingEntity target = null;
    private Map<String, String> action_Map;
    private String taskID = "";

    private List<Map<String, String>> onStart = new ArrayList<>();
    private List<Map<String, String>> onTime = new ArrayList<>();
    private List<Map<String, String>> onEnd = new ArrayList<>();

    private boolean unlimited = false;

    public Loop3(){

    }

    public void onLoop(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        this.self = self;
        this.target = target;
        this.action_Map = action_Map;
        this.taskID = taskID;

        setLoop();
        onStart();
        this.runTaskTimer(cd,0, this.period);
    }

    public void setLoop(){
        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        this.onStart = actionMapHandle.getActionMapList(new String[]{"onstart"},null);

        this.onTime = actionMapHandle.getActionMapList(new String[]{"ontime"},null);

        this.onEnd = actionMapHandle.getActionMapList(new String[]{"onend"},null);

        this.period = actionMapHandle.getInt(new String[]{"period"},1);

        this.duration = actionMapHandle.getInt(new String[]{"duration"},20);

        if(this.period <= 0){
            this.period = 1;
        }

        if(this.duration == 0){
            this.unlimited = true;
        }

    }

    public void onStart(){



        int delay = 0;
        for(Map<String, String> stringStringMap : this.onStart){
            ActionMapHandle actionMapHandle = new ActionMapHandle(stringStringMap, this.self, this.target);
            String judgMent = actionMapHandle.getString(new String[]{"actiontype"}, "");

            if(judgMent.toLowerCase().contains("break")){
                if(!new Break(this.self, this.target, stringStringMap, this.taskID).isResult()){
                    return;
                }
            }

            if(judgMent.toLowerCase().contains("delay")){
                int delayTicks = actionMapHandle.getInt(new String[]{"ticks","t"},0);
                delay = delay + delayTicks;
            }

            if(!judgMent.toLowerCase().contains("break") && !judgMent.toLowerCase().contains("delay")){

                BukkitRunnable bukkitRunnableStart = new BukkitRunnable() {
                    @Override
                    public void run() {
                        gogo(stringStringMap);
                    }
                };
                bukkitRunnableStart.runTaskLater(cd,delay);
            }

        }



    }

    public void onTime(){

        if(this.onTime.size() > 0){

            int delay = 0;
            for(Map<String, String> stringStringMap : this.onTime){
                ActionMapHandle actionMapHandle = new ActionMapHandle(stringStringMap, this.self, this.target);
                String judgMent = actionMapHandle.getString(new String[]{"actiontype"}, "");

                if(judgMent.toLowerCase().contains("break")){
                    if(!new Break(this.self, this.target, stringStringMap, this.taskID).isResult()){
                        return;
                    }
                }

                if(judgMent.toLowerCase().contains("delay")){
                    int delayTicks = actionMapHandle.getInt(new String[]{"ticks","t"},0);
                    delay = delay + delayTicks;
                }
                if(!judgMent.toLowerCase().contains("break") && !judgMent.toLowerCase().contains("delay")){
                    BukkitRunnable bukkitRunnableTime = new BukkitRunnable() {
                        @Override
                        public void run() {

                            gogo(stringStringMap);
                        }
                    };
                    bukkitRunnableTime.runTaskLater(cd,delay);
                }

            }



        }
    }

    public void onEnd(){

        if(this.onEnd.size() > 0){
            int delay = 0;



            for(Map<String, String> stringStringMap : this.onEnd){
                ActionMapHandle actionMapHandle = new ActionMapHandle(stringStringMap, this.self, this.target);
                String judgMent = actionMapHandle.getString(new String[]{"actiontype"}, "");

                if(judgMent.toLowerCase().contains("break")){
                    if(!new Break(this.self, this.target, stringStringMap, this.taskID).isResult()){
                        return;
                    }
                }

                if(judgMent.toLowerCase().contains("delay")){
                    int delayTicks = actionMapHandle.getInt(new String[]{"ticks","t"},0);
                    delay = delay + delayTicks;
                }

                if(!judgMent.toLowerCase().contains("break") && !judgMent.toLowerCase().contains("delay")){

                    BukkitRunnable bukkitRunnableEnd = new BukkitRunnable() {
                        @Override
                        public void run() {
                            gogo(stringStringMap);
                        }
                    };
                    bukkitRunnableEnd.runTaskLater(cd,delay);
                }


            }


        }

    }

    public void gogo(Map<String, String> stringStringMap){

        if(ActionManager.loop_Judgment_Map2.get(taskID) == null){
            ActionManager.loop_Judgment_Map2.put(taskID,new JudgmentAction2());
            ActionManager.loop_Judgment_Map2.get(taskID).execute(this.self, this.target, stringStringMap, this.taskID);
        }else {
            ActionManager.loop_Judgment_Map2.get(taskID).execute(this.self, this.target, stringStringMap, this.taskID);
        }

    }

    public void run(){

        ticksRun = ticksRun + period;
        onTime();

        if(!unlimited){
            if(ticksRun > duration){
                onEnd();
                cancel();
                return;
            }
        }
    }



}
