package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.entity.LookTarget;
import com.daxton.customdisplay.api.entity.RadiusTarget;
import com.daxton.customdisplay.api.other.ConfigFind;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.task.condition.Condition;
import com.daxton.customdisplay.task.condition.Condition2;
import com.daxton.customdisplay.task.locationAction.LocationAction;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class OrbitalAction2 extends BukkitRunnable{

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

    private List<CustomLineConfig> onStartList = new ArrayList<>();
    private List<CustomLineConfig> onTimeHitList = new ArrayList<>();
    private List<CustomLineConfig> onTimeList = new ArrayList<>();
    private List<CustomLineConfig> onEndHitList = new ArrayList<>();
    private List<CustomLineConfig> onEndList = new ArrayList<>();

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

    public OrbitalAction2(){

    }

    public void setParabolicAttack(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        this.self = self;
        this.target = target;
        this.taskID = taskID;
//        if(target == null){
//            return;
//        }

        startParabolicAttack(customLineConfig);

    }

    public void startParabolicAttack(CustomLineConfig customLineConfig){

        /**軌道偏轉高度**/
        hight = customLineConfig.getDouble(new String[]{"hight"},0,self,target);

        /**軌道偏轉角度**/
        angle = customLineConfig.getDouble(new String[]{"angle"},0,self,target);

        /**軌道偏轉角度是否隨機乘上正負**/
        angleSign = customLineConfig.getBoolean(new String[]{"anglesign"},false,self,target);

        /**如果自身死亡任務是否取消**/
        selfDead = customLineConfig.getBoolean(new String[]{"selfdead"},true,self,target);

        /**如果目標死亡任務是否取消**/
        targetDead = customLineConfig.getBoolean(new String[]{"targetdead"},true,self,target);

        /**初始動作**/
        onStartList = customLineConfig.getActionKeyList(new String[]{"onstart"},null, self, target);

        /**過程中命中動作**/
        onTimeHitList = customLineConfig.getActionKeyList(new String[]{"ontimehit"},null, self, target);

        onTimeHistCount = customLineConfig.getInt(new String[]{"ontimecount"},0,self,target);

        /**過程中動作**/
        onTimeList = customLineConfig.getActionKeyList(new String[]{"ontime"},null, self, target);

        /**最後命中動作**/
        onEndHitList = customLineConfig.getActionKeyList(new String[]{"onendhit"},null, self, target);

        /**最後動作**/
        onEndList = customLineConfig.getActionKeyList(new String[]{"onend"},null, self, target);

        /**執行間隔時間**/
        period = customLineConfig.getInt(new String[]{"period"},0,self,target);

        /**命中判定範圍**/
        hitRange = customLineConfig.getDouble(new String[]{"hitrange"},0.8,self,target);

        /**此動作要持續多久**/
        duration = customLineConfig.getInt(new String[]{"duration"},20,self,target);

        /**技能運行速度**/
        speed = customLineConfig.getInt(new String[]{"speed"},20,self,target);

        /**終點座標要加上自己還是目標**/
        endLocTarget = customLineConfig.getString(new String[]{"endloctarget","elt"},"none",self,target);

        /**目視距離**/
        endLocDistance = customLineConfig.getDouble(new String[]{"endlocdistance","eld"},10,self,target);

        /**終點座標偏移**/
        String[] endlocadds = customLineConfig.getStringList(new String[]{"endlocadd"},new String[]{"0","0","0"},"\\|",3, self, target);
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
        String[] startlocadds = customLineConfig.getStringList(new String[]{"startlocadd"},new String[]{"0","0","0"},"\\|",3, self, target);
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
            targetLocation = distanceDirection(self,endLocDistance,self.getHeight());
        }else if(endLocTarget.toLowerCase().contains("self")){
            targetLocation = self.getEyeLocation().clone();
        }else {
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
        //cd.getLogger().info("位置"+startLocation.getX()+" : "+startLocation.getY()+" : "+startLocation.getZ());
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

        if(target != null){ //.add(endX, endY, endZ)
            //cd.getLogger().info("誤差值"+startLocation.distanceSquared(target.getLocation().add(endX, endY, endZ))+" : "+hitRange);
            if(startLocation.distanceSquared(target.getEyeLocation()) < hitRange){
                //cd.getLogger().info("命中"+startLocation.distanceSquared(target.getLocation().add(endX, endY, endZ))+" : "+hitRange);
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
    public void actionRun(List<CustomLineConfig> customLineConfigList,Location location,LivingEntity livingTarget){

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
                            new LocationAction2().execute(self,livingTarget,customLineConfig,location,taskID);
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

        if(ActionManager2.orbital_Condition_Map.get(taskID) == null){
            ActionManager2.orbital_Condition_Map.put(taskID,new Condition2());
        }
        if(ActionManager2.orbital_Condition_Map.get(taskID) != null){
            ActionManager2.orbital_Condition_Map.get(taskID).setCondition(self,target,customLineConfig,taskID);
            b = ActionManager2.orbital_Condition_Map.get(taskID).getResult2();
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
