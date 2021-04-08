package com.daxton.customdisplay.task.action.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.location.LookLocation;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.JudgmentLocAction;
import com.daxton.customdisplay.task.condition.Condition2;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.Color.fromRGB;

public class LocPng {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private CustomLineConfig customLineConfig;
    private LivingEntity self = null;
    private LivingEntity target = null;
    private String taskID = "";

    private int period;
    private int duration;

    private List<CustomLineConfig> onStartList = new ArrayList<>();
    private List<CustomLineConfig> onTimeList = new ArrayList<>();
    private List<CustomLineConfig> onEndList = new ArrayList<>();

    private BukkitRunnable bukkitRunnable;

    public LocPng(){

    }

    public void set(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;
        this.taskID = taskID;


        JavaImageIO(self);
    }


    /**圖片設定**/
    public void JavaImageIO(LivingEntity livingEntity){

        /**要使用的圖片名稱**/
        String img = customLineConfig.getString(new String[]{"img"},"",self,target);

        /**要使用的圖片大小**/
        double imgSize = customLineConfig.getDouble(new String[]{"imgsize"},1,self,target);

        /**是否加上目標角度角度**/
        boolean imgTargetAngle = customLineConfig.getBoolean(new String[]{"imgtargetangel"}, false,self,target);

        /**要使用的圖片角度**/
        String[] pngRotAngles = customLineConfig.getStringList(new String[]{"imgrotangle"},new String[]{"0","0","0"},"\\|",3,self,target);

        /**初始動作**/
        onStartList = customLineConfig.getActionKeyList(new String[]{"onstart"},null, self, target);

        /**過程中動作**/
        onTimeList = customLineConfig.getActionKeyList(new String[]{"ontime"},null, self, target);

        /**最後動作**/
        onEndList = customLineConfig.getActionKeyList(new String[]{"onend"},null, self, target);

        /**執行間隔時間**/
        period = customLineConfig.getInt(new String[]{"period"},0,self,target);

        /**此動作要持續多久**/
        duration = customLineConfig.getInt(new String[]{"duration"},20,self,target);

        double imgX = 0;
        double imgY = 0;
        double imgZ = 0;
        if(pngRotAngles.length == 3){
            try {
                imgX = Double.valueOf(pngRotAngles[0]);
                imgY = Double.valueOf(pngRotAngles[1]);
                imgZ = Double.valueOf(pngRotAngles[2]);
            }catch (NumberFormatException exception){
                imgX = 0;
                imgY = 0;
                imgZ = 0;
            }
        }

        /**起始座標偏移**/
        String[] startlocadds = customLineConfig.getStringList(new String[]{"locadd"},new String[]{"0","0","0"},"\\|",3, self, target);
        double startX = 0;
        double startY = 0;
        double startZ = 0;
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

        Location location = new LookLocation().get2(self,daX,daY,daZ).add(startX, startY, startZ).setDirection(moveEntity());


        try{
            BufferedImage bi = ImageIO.read(new File(cd.getDataFolder(),"Png/"+ img+".png"));
            sendPic(livingEntity, location, imgX, imgY, imgZ, imgSize, imgTargetAngle, bi);

        }catch (IOException exception){

        }
    }

    /**發送圖片粒子**/
    public void sendPic(LivingEntity livingEntity, Location location, double imgX, double imgY, double imgZ, double imgSize, boolean imgTargetAngle, BufferedImage bi){

        int width = bi.getWidth();

        double widthHalf = (double)width/2;

        int height = bi.getHeight();

        double heightHalf = (double)height/2;

        /**pngX rotY pngZ 為設定給的角度**/
        /**target為攻擊目標**/
        double rotX = 0;
        double rotY = 0;
        double rotZ = 0;

        if(livingEntity != null && imgTargetAngle){
            rotX = livingEntity.getLocation().getYaw()+ imgX;
            rotY = livingEntity.getLocation().getPitch()+ imgY;
            rotZ = livingEntity.getLocation().getYaw()+ imgZ;
        }else {
            rotX = imgX;
            rotY = imgY;
            rotZ = imgZ;
        }

        rotX %= 360;
        rotY %= 360;
        rotZ %= 360;
        /**繞Y軸旋轉**/
        double cosY = Math.cos(Math.toRadians(rotY));
        double sinY = Math.sin(Math.toRadians(rotY));
        /**繞X軸旋轉**/
        double cosX = Math.cos(Math.toRadians(rotX));
        double sinX = Math.sin(Math.toRadians(rotX));
        /**繞Z軸旋轉**/
        double cosZ = Math.cos(Math.toRadians(rotZ));
        double sinZ = Math.sin(Math.toRadians(rotZ));

        Map<Location,Integer> particles = new HashMap<>();
        for(int i=0 ; i < height ; i++) {

            for (int j = 0; j < width ; j++) {

                int color = bi.getRGB(j, i);
                int blue = color & 0xff;
                int green = (color & 0xff00) >> 8;
                int red = (color & 0xff0000) >> 16;
                int alpha = (color & 0xff000000) >>> 24;
                int rgb = ( (red*65536) + (green*256) +blue );

                if(alpha != 0){
                    /**把高度置中**/
                    double addHeight = 0;
                    if(i == (heightHalf-0.5)){
                        addHeight = -0.5;
                    }else if(i >= heightHalf){
                        addHeight = (i-heightHalf)*-1;
                    }else {
                        addHeight = (heightHalf-(i));
                    }
                    addHeight = addHeight * imgSize;
                    /**把寬度置中**/
                    double addWidth = 0;
                    if(j == (widthHalf-0.5)){
                        addWidth = -0.5;
                    }else if(j >= widthHalf){
                        addWidth = (j-widthHalf);
                    }else {
                        addWidth = (widthHalf-(j))*-1;
                    }
                    addWidth = addWidth * imgSize;

                    Location useLocation = location.clone();

                    double nX = addWidth;
                    double nY = addHeight;
                    double nZ = 0;

                    double r11 = cosY*cosZ - sinX*sinY*sinZ;
                    double r13 = sinY*cosZ + sinX*cosY*sinZ;
                    double r21 = cosY*sinZ + sinX*sinY*cosZ;
                    double r23 = sinY*sinZ - sinX*cosY*cosZ;

                    double rX = r11*nX - cosX*sinZ*nY + r13*nZ;
                    double rY = r21*nX + cosX*cosZ*nY + r23*nZ;
                    double rZ = -cosX*sinY*nX + sinX*nY + cosX*cosY*nZ;

                    useLocation.add(rX,rY,rZ);

                    particles.put(useLocation, rgb);

                }

            }
        }



            if(onStartList.size() >0 ){
                particles.forEach((location1, rgb) -> {
                    actionRun(onStartList, location1, target);
                });

            }

            bukkitRunnable = new BukkitRunnable() {
                int tickRun = 0;
                @Override
                public void run() {
                    tickRun+=20;
                    if(tickRun > duration){
                        if(onEndList.size() >0 ){
                            particles.forEach((location1, rgb) -> {
                                actionRun(onEndList, location1, target);
                            });

                        }
                        cancel();
                        return;
                    }
                    if(onTimeList.size() > 0){
                        particles.forEach((location1, rgb) -> {
                            actionRun(onTimeList, location1, target);
                        });

                    }

                }
            };
            bukkitRunnable.runTaskTimer(cd, 0 ,period);

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
                            new JudgmentLocAction().execute(self,livingTarget,customLineConfig,location,taskID+(Math.random()*100000));
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

    /**給向量移動**/
    public Vector moveEntity(){


        double angle = 0;
        double hight = -90;
        //cd.getLogger().info((livingEntity.getLocation().getPitch() + 90 + hight)+"");
        //double pitch = ((livingEntity.getLocation().getPitch() + 90 + hight) * Math.PI) / 180;
        //double yaw  = ((livingEntity.getLocation().getYaw() + 90 + angle)  * Math.PI) / 180;
        double pitch = ((-90) * Math.PI) / 180;
        double yaw  = ((0)  * Math.PI) / 180;

        double x = Math.sin(pitch) * Math.cos(yaw);
        double z = Math.sin(pitch) * Math.sin(yaw);

        Vector vector = new Vector(x, 0, z);
        Vector vector1 = new Vector(0,hight,0);
        Vector vector2 = vector.add(vector1);


        return vector2;
    }

}
