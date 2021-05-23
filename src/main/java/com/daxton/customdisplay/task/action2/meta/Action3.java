package com.daxton.customdisplay.task.action2.meta;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.task.ClearAction;
import com.daxton.customdisplay.task.JudgmentAction2;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;


public class Action3 {


    public Action3(){
    }

    public static void setAction(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**動作列表**/
        List<Map<String, String>> action_Map_List = actionMapHandle.getActionMapList(new String[]{"action","a"}, null);

        /**動作次數**/
        int count = actionMapHandle.getInt(new String[]{"count","c"}, 1);

        /**動作間隔**/
        int countPeriod = actionMapHandle.getInt(new String[]{"countperiod","cp"},10);

        List<LivingEntity> targetList = actionMapHandle.getLivingEntityListTarget();

        if(count > 1){
            int delay = 0;
            for(int i = 0 ; i < count; i++){

                new BukkitRunnable(){

                    @Override
                    public void run() {
                        if(targetList.size() > 0){
                            for(LivingEntity livingEntity : targetList){
                                String uuidString = livingEntity.getUniqueId().toString();
                                startAction(action_Map_List, taskID+uuidString+(int)(Math.random()*1000), self, livingEntity);
                            }
                        }else {
                            //if(target == null){
                            startAction(action_Map_List, taskID, self, null);
                            //}
                        }

                        return;

                    }
                }.runTaskLater(cd,delay);
                delay += countPeriod;
            }
        }else{
            if(targetList.size() > 0){
                for(LivingEntity livingEntity : targetList){
                    String uuidString = livingEntity.getUniqueId().toString();
                    startAction(action_Map_List, taskID+uuidString, self, livingEntity);
                }
            }else {

                startAction(action_Map_List ,taskID, self, null);

            }


        }
    }



    public static void startAction(List<Map<String, String>> action_Map_List, String taskID, LivingEntity self, LivingEntity livingEntity){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        new ClearAction().taskID2(taskID);
        int delay = 0;

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
                        new JudgmentAction2().execute(self, livingEntity, stringStringMap, taskID);
                    }
                }.runTaskLater(cd,delay);

            }
        }

    }







}
