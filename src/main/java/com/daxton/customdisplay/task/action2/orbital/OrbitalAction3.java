package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.entity.RadiusTarget;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.JudgmentLocAction;
import com.daxton.customdisplay.task.JudgmentLocAction2;
import com.daxton.customdisplay.task.condition.Condition2;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

public class OrbitalAction3 extends BukkitRunnable{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Function<Location,Location> fLocation;
    private Location targetLocation;
    private Location startLocation;
    private Vector vec;
    private int speed;
    private int j;

    private int ticksRun = 0;

    private int period = 1;
    private int duration = 100;

    private double angle = 10;
    private double hight = 10;
    private boolean angleSign = false;

    private List<Map<String, String>> onStartList = new ArrayList<>();
    private List<Map<String, String>> onTimeHitList = new ArrayList<>();
    private List<Map<String, String>> onTimeList = new ArrayList<>();
    private List<Map<String, String>> onEndHitList = new ArrayList<>();
    private List<Map<String, String>> onEndList = new ArrayList<>();

    private double startX = 0;
    private double startY = 0;
    private double startZ = 0;
    private String endLocTarget;
    private double endLocDistance;
    private double endX = 0;
    private double endY = 0;
    private double endZ = 0;
    private double hitRange = 0.8;
    private boolean selfDead = true;
    private boolean targetDead = true;
    private int onTimeHistCount;
    private int timeHitCount;

    private final Random random = new Random();

    private BukkitRunnable bukkitRunnable;

    private LivingEntity self;

    private LivingEntity target;

    private String taskID = "";

    public OrbitalAction3(){

    }

    public void setParabolicAttack(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        this.self = self;
        this.target = target;
        this.taskID = taskID;
//        if(target == null){
//            return;
//        }

        startParabolicAttack(action_Map);

    }

    public void startParabolicAttack(Map<String, String> action_Map){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, this.self, this.target);

        /**軌道偏轉高度**/
        hight = actionMapHandle.getDouble(new String[]{"hight"},0);

        /**軌道偏轉角度**/
        angle = actionMapHandle.getDouble(new String[]{"angle"},0);

        /**軌道偏轉角度是否隨機乘上正負**/
        angleSign = actionMapHandle.getBoolean(new String[]{"anglesign"},false);

        /**如果自身死亡任務是否取消**/
        selfDead = actionMapHandle.getBoolean(new String[]{"selfdead"},true);

        /**如果目標死亡任務是否取消**/
        targetDead = actionMapHandle.getBoolean(new String[]{"targetdead"},true);

        /**初始動作**/
        onStartList = actionMapHandle.getActionMapList(new String[]{"onstart"},null);

        /**過程中命中動作**/
        onTimeHitList = actionMapHandle.getActionMapList(new String[]{"ontimehit"},null);

        onTimeHistCount = actionMapHandle.getInt(new String[]{"ontimecount"},0);

        /**過程中動作**/
        onTimeList = actionMapHandle.getActionMapList(new String[]{"ontime"},null);

        /**最後命中動作**/
        onEndHitList = actionMapHandle.getActionMapList(new String[]{"onendhit"},null);

        /**最後動作**/
        onEndList = actionMapHandle.getActionMapList(new String[]{"onend"},null);

        /**執行間隔時間**/
        period = actionMapHandle.getInt(new String[]{"period"},0);

        /**命中判定範圍**/
        hitRange = actionMapHandle.getDouble(new String[]{"hitrange"},0.8);

        /**此動作要持續多久**/
        duration = actionMapHandle.getInt(new String[]{"duration"},20);

        /**技能運行速度**/
        speed = actionMapHandle.getInt(new String[]{"speed"},20);

        /**終點座標要加上自己還是目標**/
        endLocTarget = actionMapHandle.getString(new String[]{"endloctarget","elt"},"none");

        /**目視距離**/
        endLocDistance = actionMapHandle.getDouble(new String[]{"endlocdistance","eld"},10);

        /**終點座標偏移**/
        String[] endlocadds = actionMapHandle.getStringList(new String[]{"endlocadd"},new String[]{"0","0","0"},"\\|",3);
        if(endlocadds.length == 3){
            try {
                endX = Double.valueOf(endlocadds[0]);
                endY = Double.valueOf(endlocadds[1]);
                endZ = Double.valueOf(endlocadds[2]);
            }catch (NumberFormatException exception){
                endX = 0;
                endY = 0;
                endZ = 0;
            }
        }

        /**起始座標偏移**/
        String[] startlocadds = actionMapHandle.getStringList(new String[]{"startlocadd"},new String[]{"0","0","0"},"\\|",3);
        if(startlocadds.length == 3){
            try {
                startX = Double.valueOf(startlocadds[0]);
                startY = Double.valueOf(startlocadds[1]);
                startZ = Double.valueOf(startlocadds[2]);
            }catch (NumberFormatException exception){
                startX = 0;
                startY = 0;
                startZ = 0;
            }
        }

        startX = startX*self.getEyeLocation().getDirection().getX();
        startZ = startZ*self.getEyeLocation().getDirection().getZ();
        //cd.getLogger().info(startX+" : "+startY+" : "+startZ);
        startLocation = self.getEyeLocation().add(startX, startY, startZ);


        if(endLocTarget.toLowerCase().contains("target")){
            if(target != null){
                targetLocation = target.getEyeLocation().clone();
            }else {
                targetLocation = distanceDirection(self,endLocDistance,self.getHeight());
            }
        }else if(endLocTarget.toLowerCase().contains("none")){
            target = null;
            targetLocation = distanceDirection(self,endLocDistance,self.getHeight());
        }else if(endLocTarget.toLowerCase().contains("self")){
            targetLocation = self.getEyeLocation().clone();
        }else {
            target = null;
            targetLocation = distanceDirection(self,endLocDistance,self.getHeight());
        }

        if(targetLocation != null){
            targetLocation = targetLocation.add(endX, endY, endZ);

            fLocation = (floc) ->{return floc;};

            vec = randomVector(self);

            actionRun(onStartList,startLocation,target);

            runTaskTimer(cd, 0L, period);
        }



    }

    public void run() {

        if(selfDead && self.isDead()){
            if(onEndList.size() > 0){
                actionRun(onEndList,targetLocation,target);
            }

            cancel();
            return;
        }
        if(target != null && targetDead && target.isDead()){
            if(onEndList.size() > 0){
                actionRun(onEndList,targetLocation,target);
            }

            cancel();
            return;
        }

        j++;
        for(int k = 0; k < 1; ++k) {
            double c = Math.min(1.0D, (double) j / speed);
            //Location location = fLocation.apply(startLocation.add(targetLocation.clone().subtract(startLocation).toVector().normalize().multiply(c).add(vec.clone().multiply(1.0D - c))));
            Location location = fLocation.apply(startLocation.add(targetLocation.clone().subtract(startLocation).toVector().normalize().multiply(c).add(vec.multiply(1.0D - c))));
            if(onTimeList.size() > 0){
                actionRun(onTimeList,location,target);
            }

        }
        //List<Entity> entityList = new ArrayList<>(startLocation.getWorld().getNearbyEntities(startLocation,1,1,1));
        List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities3(self, startLocation,hitRange);
        if(livingEntityList.size() > 0){
            for(LivingEntity livingEntity : livingEntityList){
                    if(onTimeHitList.size() > 0){
                        if(onTimeHistCount != 0){
                            if(timeHitCount < onTimeHistCount){
                                timeHitCount++;
                                actionRun(onTimeHitList,targetLocation,livingEntity);
                            }
                        }else {
                            actionRun(onTimeHitList,targetLocation,livingEntity);
                        }
                }

            }
        }

        if(target != null){
            if(startLocation.distanceSquared(target.getEyeLocation()) < hitRange){
                if(onEndHitList.size() > 0){
                    actionRun(onEndHitList,targetLocation,target);
                }
                cancel();
                return;
            }else if(j > duration || startLocation.distanceSquared(targetLocation) < hitRange){
                if(onEndList.size() > 0){
                    actionRun(onEndList,targetLocation,target);
                }
                cancel();
                return;
            }
        }else {

            if(j > duration || startLocation.distanceSquared(targetLocation) < hitRange){
                if(onEndList.size() > 0){
                    actionRun(onEndList,targetLocation,target);
                }
                cancel();
                return;
            }
        }

    }


    /**動作**/
    public void actionRun(List<Map<String, String>> action_Map_List,Location location,LivingEntity livingTarget){

        if(action_Map_List.size() > 0){
            int delay = 0;
            for(Map<String, String> stringStringMap : action_Map_List){
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
                            new JudgmentLocAction2().execute(self, livingTarget, stringStringMap, location, taskID);
                        }
                    };
                    bukkitRunnable.runTaskLater(cd,delay);
                }

            }


        }
    }

    /**偏轉**/
    private Vector randomVector(LivingEntity self) {
        if(angleSign){
            angle *= (double)(random.nextBoolean() ? 1 : -1);
        }

        double pitch = ((self.getLocation().getPitch() + 90 + hight) * Math.PI) / 180;
        double yaw  = ((self.getLocation().getYaw() + 90 + angle)  * Math.PI) / 180;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.cos(pitch);
        double z = Math.sin(pitch) * Math.sin(yaw);

        Vector vector = new Vector(x, y, z);
        //Vector vector1 = new Vector(0,hight,0);
        return vector.normalize(); //.add(vector1)
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







    /**根據方向距離和高度，回傳座標**/
    public static Location distanceDirection(LivingEntity livingEntity,double distance,double hight){
        Location location = livingEntity.getLocation();

        /**高度**/
        double ry = livingEntity.getEyeLocation().getDirection().getY();
        double rx = livingEntity.getEyeLocation().getDirection().getX();
        double rz = livingEntity.getEyeLocation().getDirection().getZ();

        location.add(distance*rx,(distance*ry),distance*rz);
        //location.add(distance*rx,hight+(distance*ry),distance*rz);


        return location;
    }




}
