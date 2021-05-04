package com.daxton.customdisplay.task.action2.meta;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.ClearAction;
import com.daxton.customdisplay.task.JudgmentAction2;
import com.daxton.customdisplay.task.condition.Condition2;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;


public class Action3 {

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self;
    private LivingEntity target;
    private Map<String, String> action_Map;
    private String taskID;

    public Action3(){
    }

    public void setAction(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        this.self = self;
        this.target = target;
        this.action_Map = action_Map;
        this.taskID = taskID;

        stringSetting();
    }

    public void stringSetting(){

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        /**動作列表**/
        List<Map<String, String>> action_Map_List = actionMapHandle.getActionMapList(new String[]{"action","a"}, null);

        /**動作次數**/
        int count = actionMapHandle.getInt(new String[]{"count","c"}, 1);

        /**動作間隔**/
        int countPeriod = actionMapHandle.getInt(new String[]{"countperiod","cp"},10);

       // startAction(action_Map_List);

        List<LivingEntity> targetList = actionMapHandle.getLivingEntityList2();

        if(count > 1){
            int delay = 0;
            for(int i = 0 ; i < count; i++){

                new BukkitRunnable(){

                    @Override
                    public void run() {
                        if(targetList.size() > 0){
                            for(LivingEntity livingEntity : targetList){
                                String uuidString = livingEntity.getUniqueId().toString();
                                startAction(action_Map_List, taskID+uuidString+(int)(Math.random()*1000), livingEntity);
                            }
                        }else {
                            if(target == null){
                                startAction(action_Map_List, taskID, null);
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
                    String uuidString = livingEntity.getUniqueId().toString();
                    //cd.getLogger().info(taskID+" : "+livingEntity.getName());
                    startAction(action_Map_List, taskID+uuidString, livingEntity);
                }
            }else {
                if(target == null){
                    startAction(action_Map_List ,taskID, null);
                }
            }


        }


    }

    public void startAction(List<Map<String, String>> action_Map_List, String taskID, LivingEntity livingEntity){
        new ClearAction().taskID2(taskID);
        int delay = 0;

        for(Map<String, String> stringStringMap : action_Map_List){

            ActionMapHandle actionMapHandle = new ActionMapHandle(stringStringMap, this.self, livingEntity);
            String judgMent = actionMapHandle.getString(new String[]{"actiontype"}, "");

            if(judgMent.toLowerCase().contains("break")){
                if(!new Break(this.self, livingEntity, stringStringMap, this.taskID).isResult()){
                    return;
                }
            }

            if(judgMent.toLowerCase().contains("delay")){
                int delayTicks = actionMapHandle.getInt(new String[]{"ticks","t"},0);
                delay = delay + delayTicks;
            }

            if(!judgMent.toLowerCase().contains("break") && !judgMent.toLowerCase().contains("delay")){

                BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        new JudgmentAction2().execute(self, livingEntity, stringStringMap, taskID);
                    }
                };
                bukkitRunnable.runTaskLater(cd,delay);
            }
        }

    }







}
