package com.daxton.customdisplay.task.action.meta;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.ClearAction;
import com.daxton.customdisplay.task.JudgmentAction;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;


public class Action3 {


    public Action3(){
    }

    public static void setAction(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String inTaskID){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        //動作列表
        List<Map<String, String>> action_Map_List = actionMapHandle.getActionMapList(new String[]{"a","action"}, null);

        //動作標記
        String taskID = actionMapHandle.getString(new String[]{"m","mark"}, inTaskID);

        //動作次數
        int count = actionMapHandle.getInt(new String[]{"c","count"}, 1);

        //動作間隔
        int countPeriod = actionMapHandle.getInt(new String[]{"cp","countPeriod"},10);

        //需要目標才執行
        boolean needTarget = actionMapHandle.getBoolean(new String[]{"nt","needTarget"},false);

        //暫停持續執行的動作
        boolean stop = actionMapHandle.getBoolean(new String[]{"stop","s"}, false);

        List<LivingEntity> targetList = actionMapHandle.getLivingEntityListTarget();

        if(count > 1){
            int delay = 0;
            for(int i = 0 ; i < count; i++){

                new BukkitRunnable(){

                    @Override
                    public void run() {
                        if(targetList.size() > 0){
                            for(LivingEntity livingEntity : targetList){
                                //String uuidString = livingEntity.getUniqueId().toString();
                                ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, self, livingEntity);
                                //動作標記
                                String taskID = actionMapHandle2.getString(new String[]{"m","mark"}, String.valueOf((int)(Math.random()*100000)));
                                startAction(action_Map_List, taskID, self, livingEntity, stop); //+uuidString+(int)(Math.random()*1000)

                            }
                        }else {
                            if(!needTarget){
                                startAction(action_Map_List, taskID, self, null, stop);
                            }
                        }

                        return;

                    }
                }.runTaskLater(cd,delay);
                delay += countPeriod;
            }
        }else{
            if(targetList.size() > 0){
                for(LivingEntity livingEntity : targetList){
                    //String uuidString = livingEntity.getUniqueId().toString();
                    ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, self, livingEntity);
                    //動作標記
                    String taskID2 = actionMapHandle2.getString(new String[]{"m","mark"}, String.valueOf((int)(Math.random()*100000)));
                    startAction(action_Map_List, taskID2, self, livingEntity, stop);
                }
            }else {
                if(!needTarget){
                    startAction(action_Map_List ,taskID, self, null, stop);
                }
            }


        }
    }



    public static void startAction(List<Map<String, String>> action_Map_List, String taskID, LivingEntity self, LivingEntity livingEntity, boolean stopB){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        //cd.getLogger().info(taskID+" : "+stopB);


        if(stopB){
            ClearAction.taskID(taskID);
            return;
        }
        int delay = 0;
        if(action_Map_List.size() <= 0){
            return;
        }
        for(Map<String, String> stringStringMap : action_Map_List){

            ActionMapHandle actionMapHandle = new ActionMapHandle(stringStringMap, self, livingEntity);
            String judgMent = actionMapHandle.getString(new String[]{"actiontype"}, "");
            //cd.getLogger().info(judgMent+" : "+taskID);
            if(judgMent.toLowerCase().contains("break")){
                if(!Break.valueOf(self, livingEntity, stringStringMap, taskID)){
                    return;
                }
            }

            if(judgMent.toLowerCase().contains("delay")){
                int delayTicks = actionMapHandle.getInt(new String[]{"ticks","t"},0);
                delay = delay + delayTicks;
            }

            if(!judgMent.toLowerCase().contains("break") && !judgMent.toLowerCase().contains("delay")){

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        JudgmentAction.execute(self, livingEntity, stringStringMap, taskID, null);
                    }
                }.runTaskLater(cd, delay);

            }
        }

    }







}
