package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.entity.RadiusTarget;
import com.daxton.customdisplay.api.location.DirectionLocation;
import com.daxton.customdisplay.task.JudgmentLocAction2;
import com.daxton.customdisplay.task.action2.meta.Break;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class OrbitalAction3 extends BukkitRunnable{

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Function<Location,Location> fLocation;
    private Location targetLocation;
    private Location startLocation;
    private Vector vec;
    private int speed;
    private int j;

    private int period = 1;
    private int duration = 100;

    private List<Map<String, String>> onTimeHitList = new ArrayList<>();
    private List<Map<String, String>> onTimeList = new ArrayList<>();
    private List<Map<String, String>> onEndHitList = new ArrayList<>();
    private List<Map<String, String>> onEndList = new ArrayList<>();

    private double hitRange = 0.8;
    private boolean selfDead = true;
    private boolean targetDead = true;
    private int setHitCount;
    private int hitCount;

    private LivingEntity self;

    private LivingEntity target;

    private String taskID = "";

    public OrbitalAction3(){

    }

    public void setParabolicAttack(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        this.self = self;
        this.target = target;
        this.taskID = taskID;


        startParabolicAttack(action_Map);

    }

    public void startParabolicAttack(Map<String, String> action_Map){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, this.self, this.target);

        //初始動作
        List<Map<String, String>> onStartList = actionMapHandle.getActionMapList(new String[]{"onstart","os"},null);

        //過程中動作
        onTimeList = actionMapHandle.getActionMapList(new String[]{"ontime","ot"},null);

        //過程中命中動作
        onTimeHitList = actionMapHandle.getActionMapList(new String[]{"ontimehit","oth"},null);

        //最後動作
        onEndList = actionMapHandle.getActionMapList(new String[]{"onend","oe"},null);

        //最後命中動作
        onEndHitList = actionMapHandle.getActionMapList(new String[]{"onendhit","oeh"},null);

        //執行間隔時間
        period = actionMapHandle.getInt(new String[]{"period","p"},0);

        //此動作要持續多久
        duration = actionMapHandle.getInt(new String[]{"duration","d"},20);

        //軌道偏移
        String[] orbitalDeviation = actionMapHandle.getStringList(new String[]{"orbitaldeviation","od"},new String[]{"true","true","false","0","0"},"\\|",5);

        boolean odpt = true;
        boolean odyw = true;
        boolean odsign = false;
        double odX = 0;
        double odY = 0;

        if(orbitalDeviation.length == 5){
            odpt = Boolean.parseBoolean(orbitalDeviation[0]);
            odyw = Boolean.parseBoolean(orbitalDeviation[1]);
            odsign = Boolean.parseBoolean(orbitalDeviation[2]);
            try {
                odX = Double.parseDouble(orbitalDeviation[3]);
                odY = Double.parseDouble(orbitalDeviation[4]);

            }catch (NumberFormatException exception){
                odX = 0;
                odY = 0;

            }
        }

        //技能運行速度
        speed = actionMapHandle.getInt(new String[]{"speed","s"},20);

        //命中判定範圍
        hitRange = actionMapHandle.getDouble(new String[]{"hitrange","hr"},0.8);

        //命中次數
        setHitCount = actionMapHandle.getInt(new String[]{"hitcount","hc"},0);

        //起始座標偏移
        String[] startlocadds = actionMapHandle.getStringList(new String[]{"startlocadd","sla"},new String[]{"true","true","0","0","0","0"},"\\|",6);
        boolean startTargetPitch = Boolean.parseBoolean(startlocadds[0]);
        boolean startTargetYaw = Boolean.parseBoolean(startlocadds[1]);
        double startPitch = 0;
        double startYaw = 0;
        double startDistance = 0;
        double startHight = 0;
        try {
            startPitch = Double.parseDouble(startlocadds[2]);
            startYaw = Double.parseDouble(startlocadds[3]);
            startDistance = Double.parseDouble(startlocadds[4]);
            startHight = Double.parseDouble(startlocadds[5]);
        }catch (NumberFormatException exception){
            startPitch = 0;
            startYaw = 0;
            startDistance = 0;
            startHight = 0;
        }




        startLocation = DirectionLocation.getDirectionLoction(self.getLocation(), self.getLocation(), startTargetPitch, startTargetYaw, startPitch, startYaw, startDistance).add(0, startHight, 0);
//        startX = startX*self.getEyeLocation().getDirection().getX();
//        startZ = startZ*self.getEyeLocation().getDirection().getZ();
//
//        startLocation = self.getEyeLocation().add(startX, startY, startZ);

        //如果自身死亡任務是否取消
        selfDead = actionMapHandle.getBoolean(new String[]{"selfdead","sd"},true);

        //如果目標死亡任務是否取消
        targetDead = actionMapHandle.getBoolean(new String[]{"targetdead","td"},true);

        //LivingEntity livingEntity = actionMapHandle.getOneLivingEntity();
        targetLocation = actionMapHandle.getLocation(null);
        if(targetLocation != null){

            //targetLocation = DirectionLocation.getDirectionLoction(livingEntity.getLocation(), livingEntity.getLocation(), endTargetPitch, endTargetYaw, endPitch, endYaw, endDistance).add(0, endHight, 0);


            fLocation = (floc) ->{return floc;};

            vec = DirectionLocation.getDirection2(self.getLocation(), odpt, odyw, odsign, odX, odY, 1);
            //randomVector(self);

            actionRun(onStartList,startLocation,target);

            runTaskTimer(cd, 0L, period);


        }


    }

    public void run() {

        //自身死亡
        if(selfDead && self.isDead()){
            if(onEndList.size() > 0){
                actionRun(onEndList,targetLocation,target);
            }
            cancel();
            return;
        }

        //目標死亡
        if(target != null && targetDead && target.isDead()){
            if(onEndList.size() > 0){
                actionRun(onEndList,targetLocation,target);
            }

            cancel();
            return;
        }


        j += period;
        for(int k = 0; k < 1; ++k) {
            double c = Math.min(1.0D, (double) j / speed);
            Location location = fLocation.apply(startLocation.add(targetLocation.clone().subtract(startLocation).toVector().normalize().multiply(c).add(vec.multiply(1.0D - c))));
            if(onTimeList.size() > 0){
                actionRun(onTimeList,location,target);
            }

        }

        List<LivingEntity> livingEntityList = RadiusTarget.getRadiusLivingEntities3(self, startLocation,hitRange);
        if(livingEntityList.size() > 0){
            for(LivingEntity livingEntity : livingEntityList){
                if(onTimeHitList.size() > 0){
                    if(setHitCount != 0){
                        if(hitCount < setHitCount){
                            hitCount++;
                            actionRun(onTimeHitList,targetLocation,livingEntity);
                        }else {
                            cancel();
                        }
                    }else {
                        actionRun(onTimeHitList,targetLocation,livingEntity);
                    }
                }

            }
        }

        if(target != null){
            if(j > duration || startLocation.distanceSquared(targetLocation) < hitRange){
                if(onEndList.size() > 0){
                    actionRun(onEndList,targetLocation,target);
                }
                cancel();

            }else if(startLocation.distanceSquared(target.getEyeLocation()) < hitRange){
                if(onEndHitList.size() > 0){
                    actionRun(onEndHitList,targetLocation,target);
                }
                cancel();

            }
        }else {
            //cd.getLogger().info(j+" : "+duration);
            if(j > duration || startLocation.distanceSquared(targetLocation) < hitRange){
                if(onEndList.size() > 0){
                    //cd.getLogger().info("時間到結束");
                    actionRun(onEndList,targetLocation,target);
                }
                cancel();

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

                if(judgMent.toLowerCase().contains("break")){
                    if(!new Break(this.self, livingTarget, stringStringMap, this.taskID).isResult()){
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
                            new JudgmentLocAction2().execute(self, livingTarget, stringStringMap, location, taskID);

                        }
                    };
                    bukkitRunnable.runTaskLater(cd,delay);
                }

            }


        }
    }

}
