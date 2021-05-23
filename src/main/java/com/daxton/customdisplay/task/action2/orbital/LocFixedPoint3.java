package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.entity.RadiusTarget;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.JudgmentLocAction2;
import com.daxton.customdisplay.task.condition.Condition2;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocFixedPoint3 extends BukkitRunnable {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self;
    private LivingEntity target;
    private String taskID;

    private Location startLocation;

    private double hitRange;
    private int hitCount;
    private boolean selfDead;

    private int period;
    private int duration;

    private List<Map<String, String>> onStartList = new ArrayList<>();
    private List<Map<String, String>> onHitList = new ArrayList<>();
    private List<Map<String, String>> onTimeList = new ArrayList<>();
    private List<Map<String, String>> onEndList = new ArrayList<>();

    private BukkitRunnable bukkitRunnable;
    private int j;
    private int count;

    public LocFixedPoint3(){

    }

    public void set(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        this.taskID = taskID;
        this.self = self;
        this.target = target;
        setting(action_Map);
    }

    public void setting(Map<String, String> action_Map){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, this.self, this.target);

        /**初始動作**/
        onStartList = actionMapHandle.getActionMapList(new String[]{"onstart"},null);

        /**過程中命中動作**/
        onHitList = actionMapHandle.getActionMapList(new String[]{"onhit"},null);

        /**命中判定範圍**/
        hitRange = actionMapHandle.getDouble(new String[]{"hitrange"},3);

        /**命中判定範圍**/
        hitCount = actionMapHandle.getInt(new String[]{"hitcount"},0);

        /**過程中動作**/
        onTimeList = actionMapHandle.getActionMapList(new String[]{"ontime"},null);

        /**最後動作**/
        onEndList = actionMapHandle.getActionMapList(new String[]{"onend"},null);

        /**此動作要持續多久**/
        duration = actionMapHandle.getInt(new String[]{"duration"},100);

        /**執行間隔時間**/
        period = actionMapHandle.getInt(new String[]{"period"},20);


        /**如果自身死亡任務是否取消**/
        selfDead = actionMapHandle.getBoolean(new String[]{"selfdead"},true);

        startLocation = actionMapHandle.getLocation(null);


        if(startLocation != null){
            if(onStartList.size() > 0){
                actionRun(onStartList,startLocation.clone(),target);
            }
            runTaskTimer(cd, 0L, period);
        }


    }

    public void run() {
        if(selfDead && self.isDead()){
            if(onEndList.size() > 0){
                actionRun(onEndList,startLocation.clone(),target);
            }

            cancel();
            return;
        }

        if(j > duration){
            if(onEndList.size() > 0){
                actionRun(onEndList,startLocation.clone(),target);
            }


            cancel();
            return;
        }

        if(onTimeList.size() > 0){
            actionRun(onTimeList,startLocation.clone(),target);
        }

        List<LivingEntity> entityList = RadiusTarget.getRadiusLivingEntities3(self, startLocation.clone(), hitRange);
        if(entityList.size() > 0){
            if(hitCount > 0){

                if(count < hitCount){
                    count++;
                    for(LivingEntity livingEntity : entityList){

                        Location hitLoction = livingEntity.getLocation();
                        if(onHitList.size() > 0){
                            actionRun(onHitList, hitLoction, livingEntity);
                        }

                    }
                }
            }else {
                for(LivingEntity livingEntity : entityList){

                    Location hitLoction = livingEntity.getLocation();
                    if(onHitList.size() > 0){
                        actionRun(onHitList, hitLoction, livingEntity);
                    }

                }
            }
        }

        j+= period;

    }

    /**動作**/
    public void actionRun(List<Map<String, String>> on_Action_List, Location location, LivingEntity livingTarget){

        if(on_Action_List.size() > 0){
            int delay = 0;
            for(Map<String, String> stringStringMap : on_Action_List){
                ActionMapHandle actionMapHandle = new ActionMapHandle(stringStringMap, this.self, this.target);
                String judgMent = actionMapHandle.getString(new String[]{"actiontype"}, "");

//                if(judgMent.toLowerCase().contains("condition")){
//
//                    if(!(condition(customLineConfig))){
//                        return;
//                    }
//                }

                if(judgMent.toLowerCase().contains("delay")){
                    int delayTicks = actionMapHandle.getInt(new String[]{"ticks","t"},0);
                    delay = delay + delayTicks;
                }

                if(!judgMent.toLowerCase().contains("condition") && !judgMent.toLowerCase().contains("delay")){
                    bukkitRunnable = new BukkitRunnable() {
                        @Override
                        public void run() {
                            new JudgmentLocAction2().execute(self, livingTarget, stringStringMap, location.setDirection(setVector()), taskID);
                        }
                    };
                    bukkitRunnable.runTaskLater(cd,delay);
                }

            }


        }
    }

    /**條件**/
    public boolean condition(CustomLineConfig customLineConfig){

        boolean b = false;

        if(ActionManager.orbital_Condition_Map.get(taskID) == null){
            ActionManager.orbital_Condition_Map.put(taskID,new Condition2());
        }
        if(ActionManager.orbital_Condition_Map.get(taskID) != null){
            ActionManager.orbital_Condition_Map.get(taskID).setCondition(self,target,customLineConfig,taskID);
            b = ActionManager.orbital_Condition_Map.get(taskID).getResult2();
        }

        return b;
    }

    /**給向量**/
    public Vector setVector(){


        double angle = 0;
        double hight = -90;

        double pitch = ((hight) * Math.PI) / 180;
        double yaw  = ((angle)  * Math.PI) / 180;

        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.cos(pitch);
        double z = Math.sin(pitch) * Math.sin(yaw);

        Vector vector = new Vector(x, y, z);



        return vector;
    }

}
