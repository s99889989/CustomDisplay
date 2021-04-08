package com.daxton.customdisplay.task.action.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.entity.RadiusTarget;
import com.daxton.customdisplay.api.location.LookLocation;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.JudgmentLocAction;
import com.daxton.customdisplay.task.condition.Condition2;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class FixedPoint extends BukkitRunnable {

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

    private double startX;
    private double startY;
    private double startZ;

    private List<CustomLineConfig> onStartList = new ArrayList<>();
    private List<CustomLineConfig> onHitList = new ArrayList<>();
    private List<CustomLineConfig> onTimeList = new ArrayList<>();
    private List<CustomLineConfig> onEndList = new ArrayList<>();

    private BukkitRunnable bukkitRunnable;
    private int j;
    private int count;

    public FixedPoint(){

    }

    public void set(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        this.taskID = taskID;
        this.self = self;
        this.target = target;
        setting(customLineConfig);
    }

    public void setting(CustomLineConfig customLineConfig){

        /**初始動作**/
        onStartList = customLineConfig.getActionKeyList(new String[]{"onstart"},null, self, target);

        /**過程中命中動作**/
        onHitList = customLineConfig.getActionKeyList(new String[]{"onhit"},null, self, target);

        /**命中判定範圍**/
        hitRange = customLineConfig.getDouble(new String[]{"hitrange"},3,self,target);

        /**命中判定範圍**/
        hitCount = customLineConfig.getInt(new String[]{"hitcount"},0,self,target);

        /**過程中動作**/
        onTimeList = customLineConfig.getActionKeyList(new String[]{"ontime"},null, self, target);

        /**最後動作**/
        onEndList = customLineConfig.getActionKeyList(new String[]{"onend"},null, self, target);

        /**此動作要持續多久**/
        duration = customLineConfig.getInt(new String[]{"duration"},100,self,target);

        /**執行間隔時間**/
        period = customLineConfig.getInt(new String[]{"period"},20,self,target);

        /**起始座標偏移**/
        String[] startlocadds = customLineConfig.getStringList(new String[]{"locadd"},new String[]{"0","0","0"},"\\|",3, self, target);
        if(startlocadds.length == 3){
            try {
                startX = Double.parseDouble(startlocadds[0]);
                startY = Double.parseDouble(startlocadds[1]);
                startZ = Double.parseDouble(startlocadds[2]);
            }catch (NumberFormatException exception){
                startX = 0;
                startY = 0;
                startZ = 0;
            }
        }

        /**向量增加座標**/
        String[] directionAdd = customLineConfig.getStringList(new String[]{"directionadd","da"},new String[]{"0","0","0"},"\\|",3, self, target);
        double daX = 0;
        double daY = 0;
        double daZ = 0;
        if(directionAdd.length == 3){
            try {
                daX = Double.parseDouble(directionAdd[0]);
                daY = Double.parseDouble(directionAdd[1]);
                daZ = Double.parseDouble(directionAdd[2]);
            }catch (NumberFormatException exception){
                daX = 0;
                daY = 0;
                daZ = 0;
            }
        }

        /**如果自身死亡任務是否取消**/
        selfDead = customLineConfig.getBoolean(new String[]{"selfdead"},true,self,target);

        if(startLocation == null){
            //new LookLocation().get2(self,daX,daY,daZ);
            startLocation = new LookLocation().get2(self,daX,daY,daZ).add(startX, startY, startZ);
        }

        if(startLocation != null){
            if(onStartList.size() > 0){
                actionRun(onStartList,startLocation,target);
            }
            runTaskTimer(cd, 0L, period);
        }

    }

    public void run() {
        if(selfDead && self.isDead()){
            if(onEndList.size() > 0){
                actionRun(onEndList,startLocation,target);
            }

            cancel();
            return;
        }

        if(j > duration){
            if(onEndList.size() > 0){
                actionRun(onEndList,startLocation,target);
            }


            cancel();
            return;
        }

        if(onTimeList.size() > 0){
            actionRun(onTimeList,startLocation,target);
        }

        List<LivingEntity> entityList = RadiusTarget.getRadiusLivingEntities3(self, startLocation, hitRange);
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
    public void actionRun(List<CustomLineConfig> customLineConfigList, Location location, LivingEntity livingTarget){

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
                            new JudgmentLocAction().execute(self,livingTarget,customLineConfig,location.setDirection(setVector()),taskID);
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
