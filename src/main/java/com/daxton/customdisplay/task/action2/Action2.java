package com.daxton.customdisplay.task.action2;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.other.ConfigFind;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.task.JudgmentAction2;
import com.daxton.customdisplay.task.action.JudgmentAction;
import com.daxton.customdisplay.task.condition.Condition;
import com.daxton.customdisplay.task.condition.Condition2;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;


public class Action2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self;
    private LivingEntity target;
    private String firstString;
    private CustomLineConfig customLineConfig;
    private String taskID;

    private List<CustomLineConfig> customLineConfigList = new ArrayList<>();
    private BukkitRunnable bukkitRunnable;




    public Action2(){
    }

    public void setAction(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;
        this.taskID = taskID;

        stringSetting();
    }

    public void stringSetting(){

        /**動作列表**/
        customLineConfigList = customLineConfig.getActionKeyList(new String[]{"action","a"},null, self, target);
        /**動作次數**/
        int count = customLineConfig.getInt(new String[]{"count","c"},1, self, target);

        /**動作間隔**/
        int countPeriod = customLineConfig.getInt(new String[]{"countperiod","cp"},10, self, target);
        if(count > 1){
            int delay = 0;
            for(int i = 0 ; i < count; i++){

                new BukkitRunnable(){

                    @Override
                    public void run() {

                        startAction(String.valueOf((int)(Math.random()*100000)));

                        return;

                    }
                }.runTaskLater(cd,delay);
                delay += countPeriod;
            }
        }else {
            startAction(taskID);
        }



    }

    public void startAction(String taskID){

        if(customLineConfigList.size() > 0){

                int delay = 0;
                for(CustomLineConfig customLineConfig : customLineConfigList){
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
                        bukkitRunnable = new BukkitRunnable() {
                            @Override
                            public void run() {

                                new JudgmentAction2().execute(self,target,customLineConfig,taskID);
                            }
                        };
                        bukkitRunnable.runTaskLater(cd,delay);
                    }

                }


            if(ActionManager2.action_Condition_Map.get(taskID) != null){
                ActionManager2.action_Condition_Map.remove(taskID);
            }

        }

    }

    public boolean condition(CustomLineConfig customLineConfig){

        boolean b = false;

        if(ActionManager2.action_Condition_Map.get(taskID) == null){
            ActionManager2.action_Condition_Map.put(taskID,new Condition2());
        }
        if(ActionManager2.action_Condition_Map.get(taskID) != null){
            ActionManager2.action_Condition_Map.get(taskID).setCondition(self,target,customLineConfig,taskID);
            b = ActionManager2.action_Condition_Map.get(taskID).getResult2();
        }

        return b;
    }



}
