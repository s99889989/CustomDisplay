package com.daxton.customdisplay.task.locationAction;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.EntityFind;

import com.daxton.customdisplay.api.other.ConfigFind;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ConditionManager;
import com.daxton.customdisplay.task.condition.Condition;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import static org.bukkit.Color.fromRGB;

public class OrbitalAction extends BukkitRunnable{

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
    private double distance = 10;
    private String onStart = "";
    private String onTimeHit = "";
    private String onTime = "";
    private String onEndHit = "";
    private String onEnd = "";
    private double startX = 0;
    private double startY = 0;
    private double startZ = 0;
    private double endX = 0;
    private double endY = 0;
    private double endZ = 0;
    private boolean selfDead = true;
    private boolean targetDead = true;

    protected static final Random random = new Random();

    private BukkitRunnable bukkitRunnable;

    private LivingEntity self;

    private LivingEntity target;

    private String taskID = "";

    private Hologram hologram;

    public OrbitalAction(){

    }

    public void setParabolicAttack(LivingEntity self, LivingEntity target, String firstString, String taskID){
        this.self = self;
        this.target = target;
        this.taskID = taskID;

        startParabolicAttack(firstString);
    }

    public void startParabolicAttack(String firstString){
        List<String> stringList = new StringFind().getStringList(firstString);
        for(String string1 : stringList){
            if(string1.toLowerCase().contains("onstart=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    onStart = strings[1];
                }
            }

            if(string1.toLowerCase().contains("ontimehit=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    onTimeHit = strings[1];
                }
            }

            if(string1.toLowerCase().contains("ontime=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    onTime = strings[1];
                }
            }

            if(string1.toLowerCase().contains("onendhit=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    onEndHit = strings[1];
                }
            }

            if(string1.toLowerCase().contains("onend=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    onEnd = strings[1];
                }
            }

            if(string1.toLowerCase().contains("period=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    period = Integer.valueOf(strings[1]);
                }
            }

            if(string1.toLowerCase().contains("distance=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    distance = Double.valueOf(strings[1]);
                }
            }

            if(string1.toLowerCase().contains("duration=")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    duration = Integer.valueOf(strings[1]);
                }
            }

            if(string1.toLowerCase().contains("startlocadd")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    String[] strings1 = strings[1].split("|");
                    if(strings1.length == 3){
                        try {
                            startX = Double.valueOf(strings1[0]);
                            startY = Double.valueOf(strings1[1]);
                            startX = Double.valueOf(strings1[2]);
                        }catch (NumberFormatException exception){
                            startX = 0;
                            startY = 0;
                            startX = 0;
                        }
                    }
                }
            }
            if(string1.toLowerCase().contains("endlocadd")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    String[] strings1 = strings[1].split("|");
                    if(strings1.length == 3){
                        try {
                            endX = Double.valueOf(strings1[0]);
                            endY = Double.valueOf(strings1[1]);
                            endX = Double.valueOf(strings1[2]);
                        }catch (NumberFormatException exception){
                            endX = 0;
                            endY = 0;
                            endX = 0;
                        }
                    }
                }
            }
            if(string1.toLowerCase().contains("selfdead")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    selfDead = Boolean.valueOf(strings[1]);
                }
            }
            if(string1.toLowerCase().contains("targetdead")){
                String[] strings = string1.split("=");
                if(strings.length == 2){
                    targetDead = Boolean.valueOf(strings[1]);
                }
            }
        }
        if(target == null){
            this.target = (LivingEntity) new EntityFind().getTarget(self,10);
        }


        startLocation = self.getLocation().add(0.0, self.getHeight(), 0.0);
        if(target == null){

            targetLocation = distanceDirection(self,distance,self.getHeight());
        }else {

            //targetLocation = distanceDirection(self,20,self.getHeight());
            targetLocation = target.getLocation().add(0.0, target.getHeight(), 0.0);
        }

        fLocation = (floc) ->{return floc;};
        vec = randomVector(self);
        speed = 10;

        actionRun(onStart,startLocation,target);

        runTaskTimer(cd, 0L, period);
    }

    public void run() {
        j++;
        for(int k = 0; k < 1; ++k) {
            double c = Math.min(1.0D, (double) j / duration);
            //Location location = fLocation.apply(startLocation.add(targetLocation.clone().subtract(startLocation).toVector().normalize().multiply(c).add(vec.clone().multiply(1.0D - c))));
            Location location = fLocation.apply(startLocation.add(targetLocation.clone().subtract(startLocation).toVector().normalize().multiply(c).add(vec.multiply(1.0D - c))));
            actionRun(onTime,location,target);
        }
        List<Entity> entityList = new ArrayList<>(startLocation.getWorld().getNearbyEntities(startLocation,1,1,1));
        if(entityList.size() > 0){
            for(Entity entity : entityList){
                if(entity != self && entity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) entity;
                    actionRun(onTimeHit,targetLocation,livingEntity);
                }
            }
        }

        if(target != null && startLocation.distanceSquared(target.getLocation().add(0.0, target.getHeight() / 2.0, 0.0)) < 0.8D){

            actionRun(onEndHit,targetLocation,target);

            cancel();
        }else if(j > duration || startLocation.distanceSquared(targetLocation) < 0.8D){

            actionRun(onEnd,targetLocation,target);

            cancel();
        }

    }

    private Vector randomVector(LivingEntity livingEntity) {
        double ax = Math.toRadians(livingEntity.getEyeLocation().getYaw() + 90.0f);
        double az = Math.toRadians(livingEntity.getEyeLocation().getYaw() + 90.0f);
        //Vector vector = new Vector(Math.cos(a += (double)(random.nextBoolean() ? 1 : -1) * (random.nextDouble() * 2.0 + 1.0) * 3.141592653589793 / 6.0), 0.8, Math.sin(a)).normalize().multiply(0.4);

        //double x = Math.cos(a += (random.nextDouble() * 2.0 + 1.0) * 3.141592653589793 / 6.0);
        double x = Math.cos(ax);
        double z = Math.sin(az);
        //cd.getLogger().info("亂數: "+(random.nextDouble() * 2.0 + 1.0));
        Vector vector = new Vector(x, 0.0, z).normalize().multiply(0.4);
        return vector;
    }

    public void actionRun(String action,Location location,LivingEntity livingTarget){
        List<String> stringList = new ConfigFind().getActionKeyList(action);
        if(stringList.size() > 0){
            int delay = 0;
            for(String actionString : stringList){
                if(actionString.toLowerCase().contains("condition")){
                    if(!(condition(actionString))){
                        return;
                    }
                }
                if(actionString.toLowerCase().contains("delay")){
                    String[] strings = actionString.replace(" ","").toLowerCase().split("=");
                    if(strings.length == 2){
                        try{
                            delay = delay + Integer.valueOf(strings[1]);
                        }catch (NumberFormatException exception){

                        }
                    }
                }
                bukkitRunnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        new LocationAction().execute(self,livingTarget,actionString,location,taskID);
                    }
                };
                bukkitRunnable.runTaskLater(cd,delay);
            }


        }
    }

    public boolean condition(String actionString){

        boolean b = false;
        if(ConditionManager.getAction_Condition_Map().get(taskID) == null){
            ConditionManager.getAction_Condition_Map().put(taskID,new Condition());
        }
        if(ConditionManager.getAction_Condition_Map().get(taskID) != null){
            ConditionManager.getAction_Condition_Map().get(taskID).setCondition(self,target,actionString,taskID);
            b = ConditionManager.getAction_Condition_Map().get(taskID).getResult2();
        }
        return b;
    }





    public double vectorX(LivingEntity livingEntity){
        double xVector = livingEntity.getLocation().getDirection().getX();
        double rxVector = 0;
        if(xVector > 0){
            rxVector = 0.1;
        }else{
            rxVector = -0.1;
        }
        return rxVector;
    }
    public double vectorY(LivingEntity livingEntity){
        double yVector = livingEntity.getLocation().getDirection().getY();
        double ryVector = 0;
        if(yVector > 0){
            ryVector = 1;
        }else{
            ryVector = -1;
        }
        return ryVector;
    }
    public double vectorZ(LivingEntity livingEntity){
        double zVector = livingEntity.getLocation().getDirection().getZ();
        double rzVector = 0;
        if(zVector > 0){
            rzVector = 0.1;
        }else{
            rzVector = -0.1;
        }
        return rzVector;
    }



    /**根據方向距離和高度，回傳座標**/
    public static Location distanceDirection(LivingEntity livingEntity,double distance,double hight){
        Location location = livingEntity.getLocation();

        /**高度**/
        double ry = livingEntity.getEyeLocation().getDirection().getY();
        double rx = livingEntity.getEyeLocation().getDirection().getX();
        double rz = livingEntity.getEyeLocation().getDirection().getZ();

        location.add(distance*rx,hight+(distance*ry),distance*rz);



        return location;
    }




}
