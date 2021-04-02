package com.daxton.customdisplay.task.action2;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.task.JudgmentAction2;
import com.daxton.customdisplay.task.condition.Condition;
import com.daxton.customdisplay.task.condition.Condition2;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
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
    private CustomLineConfig customLineConfig;
    private String taskID = "";


    private List<CustomLineConfig> onStart = new ArrayList<>();
    private List<CustomLineConfig> onTime = new ArrayList<>();
    private List<CustomLineConfig> onEnd = new ArrayList<>();


    private boolean unlimited = true;
    private BukkitRunnable bukkitRunnableStart;
    private BukkitRunnable bukkitRunnableTime;
    private BukkitRunnable bukkitRunnableEnd;



    /**條件判斷**/
    private static Map<String,Condition> conditionMap = new HashMap<>();

    public Loop2(){

    }

    public void onLoop(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;
        this.taskID = taskID;

        setLoop();
        onStart();
        this.runTaskTimer(cd,0, period);
    }

    public void setLoop(){

        onStart = customLineConfig.getActionKeyList(new String[]{"onstart"});

        onTime = customLineConfig.getActionKeyList(new String[]{"ontime"});

        onEnd = customLineConfig.getActionKeyList(new String[]{"onend"});

        period = customLineConfig.getInt(new String[]{"period"},5, self, target);

        unlimited = customLineConfig.getBoolean(new String[]{"unlimitedtime","ut"}, self, target);

        duration = customLineConfig.getInt(new String[]{"duration"},20, self, target);

    }

    public void onStart(){
        List<CustomLineConfig> stringList = onStart;
        if(stringList.size() > 0){
            int delay = 0;
            for(CustomLineConfig customLineConfig : stringList){
                String judgMent = customLineConfig.getActionKey();
                if(judgMent.toLowerCase().contains("condition")){

                    if(!(condition(customLineConfig))){
                        return;
                    }
                }
                if(judgMent.toLowerCase().contains("delay")){
                    String delayTicks = customLineConfig.getString(new String[]{"ticks","t"},null, self, target);
                    if(delayTicks != null){
                        try{
                            delay = delay + Integer.valueOf(delayTicks);
                        }catch (NumberFormatException exception){
                            delay = delay + 0;
                        }
                    }
                }
                if(!judgMent.toLowerCase().contains("condition") && !judgMent.toLowerCase().contains("delay")){
                    bukkitRunnableStart = new BukkitRunnable() {
                        @Override
                        public void run() {
                            gogo(customLineConfig);
                        }
                    };
                    bukkitRunnableStart.runTaskLater(cd,delay);
                }

            }


        }
    }

    public void onTime(){
        List<CustomLineConfig> stringList = onTime;
        if(stringList.size() > 0){
            int delay = 0;
            for(CustomLineConfig customLineConfig : stringList){
                String judgMent = customLineConfig.getActionKey();
                if(judgMent.toLowerCase().contains("condition")){

                    if(!(condition(customLineConfig))){
                        return;
                    }
                }
                if(judgMent.toLowerCase().contains("delay")){
                    String delayTicks = customLineConfig.getString(new String[]{"ticks","t"},null, self, target);
                    if(delayTicks != null){
                        try{
                            delay = delay + Integer.valueOf(delayTicks);
                        }catch (NumberFormatException exception){
                            delay = delay + 0;
                        }
                    }
                }
                if(!judgMent.toLowerCase().contains("condition") && !judgMent.toLowerCase().contains("delay")){
                    bukkitRunnableTime = new BukkitRunnable() {
                        @Override
                        public void run() {

                            gogo(customLineConfig);
                        }
                    };
                    bukkitRunnableTime.runTaskLater(cd,delay);
                }

            }



        }
    }

    public void onEnd(){
        List<CustomLineConfig> stringList = onEnd;
        if(stringList.size() > 0){
            int delay = 0;

            for(CustomLineConfig customLineConfig : stringList){
                String judgMent = customLineConfig.getActionKey();
                if(judgMent.toLowerCase().contains("condition")){

                    if(!(condition(customLineConfig))){
                        return;
                    }
                }
                if(judgMent.toLowerCase().contains("delay")){
                    String delayTicks = customLineConfig.getString(new String[]{"ticks","t"},null, self, target);
                    if(delayTicks != null){
                        try{
                            delay = delay + Integer.valueOf(delayTicks);
                        }catch (NumberFormatException exception){
                            delay = delay + 0;
                        }
                    }
                }
                if(!judgMent.toLowerCase().contains("condition") && !judgMent.toLowerCase().contains("delay")){
                    bukkitRunnableEnd = new BukkitRunnable() {
                        @Override
                        public void run() {
                            gogo(customLineConfig);
                        }
                    };
                    bukkitRunnableEnd.runTaskLater(cd,delay);
                }


            }
            if(ActionManager2.loop_Judgment_Map.get(taskID) != null){
                ActionManager2.loop_Judgment_Map.remove(taskID);
            }

            if(ActionManager2.loop_Judgment_Map.get(taskID) != null){
                ActionManager2.loop_Judgment_Map.remove(taskID);
            }

        }

    }

    public void gogo(CustomLineConfig customLineConfig){

        if(ActionManager2.loop_Judgment_Map.get(taskID) == null){
            ActionManager2.loop_Judgment_Map.put(taskID,new JudgmentAction2());
            ActionManager2.loop_Judgment_Map.get(taskID).execute(self,target,customLineConfig,taskID);
        }else {
            ActionManager2.loop_Judgment_Map.get(taskID).execute(self,target,customLineConfig,taskID);
        }

    }

    public boolean condition(CustomLineConfig customLineConfig){

        boolean b = false;

        if(ActionManager2.loop_Condition_Map.get(taskID) == null){
            ActionManager2.loop_Condition_Map.put(taskID,new Condition2());
        }
        if(ActionManager2.loop_Condition_Map.get(taskID) != null){
            ActionManager2.loop_Condition_Map.get(taskID).setCondition(self,target,customLineConfig,taskID);
            b = ActionManager2.loop_Condition_Map.get(taskID).getResult2();
        }
//        if(b){
//            ActionManager2.loop_Condition_Map.remove(taskID);
//        }
        return b;
    }

    public void run(){
        ticksRun = ticksRun + period;
        onTime();
        if(!unlimited){
            if(ticksRun > duration){
                onEnd();
                cancel();
            }
        }
    }



}
