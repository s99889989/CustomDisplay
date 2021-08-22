package com.daxton.customdisplay.task.action.location;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.location.ThreeDLocation;
import com.daxton.customdisplay.manager.PlaceholderManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.bukkit.Color.fromRGB;
import static org.bukkit.Particle.*;

public class SendParticles3 {

    public SendParticles3(){


    }

    public static void setParticles(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID, Location inputLocation){
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        //獲得功能
        String function = actionMapHandle.getString(new String[]{"function","fc"},"");

        //粒子名稱
        Particle putParticle = actionMapHandle.getParticle(new String[]{"particle","p"},"REDSTONE");

        //粒子的方塊材質
        BlockData blockData = actionMapHandle.getBlockData(new String[]{"particledata","pdata"},"REDSTONE_BLOCK");

        //粒子的物品材質
        ItemStack itemData = actionMapHandle.getItemStack(new String[]{"particledata","pdata"},"COOKIE");

        //粒子的顏色
        Particle.DustOptions color = actionMapHandle.getParticleColor(new String[]{"particledata","pdata"},"FF0000");

        //粒子的速度
        double extra = actionMapHandle.getDouble(new String[]{"extra","e"},0);

        //粒子的數量
        int count = actionMapHandle.getInt(new String[]{"count","c"},10);


        //粒子擴散
        String[] locOffs = actionMapHandle.getStringList(new String[]{"locationoffset","locoff"},new String[]{"0","0","0"},"\\|",3);
        double xOffset = 0;
        double yOffset = 0;
        double zOffset = 0;
        try {
            xOffset = Double.parseDouble(locOffs[0]);
            yOffset = Double.parseDouble(locOffs[1]);
            zOffset = Double.parseDouble(locOffs[2]);
        }catch (NumberFormatException exception){
            xOffset = 0;
            yOffset = 0;
            zOffset = 0;
        }

        Location location = actionMapHandle.getLocation(inputLocation);

        if(function.toLowerCase().contains("remove")){
            if(self instanceof Player){
                Player player = (Player) self;
                String uuidString = player.getUniqueId().toString();
                if(putParticle != REDSTONE){
                    String particle = putParticle.toString().toLowerCase();
                    PlaceholderManager.particles_function.put(uuidString+"particle",particle);
                }
                PlaceholderManager.particles_function.put(uuidString+"function",function);
            }
            if(target instanceof Player){
                Player player = (Player) target;
                String uuidString = player.getUniqueId().toString();
                if(putParticle != REDSTONE){
                    String particle = putParticle.toString().toLowerCase();
                    PlaceholderManager.particles_function.put(uuidString+"particle",particle);
                }
                PlaceholderManager.particles_function.put(uuidString+"function",function);
            }
        }else if(function.toLowerCase().contains("add")){
            if(location != null){
                sendParticle(self, putParticle, location, count, xOffset, yOffset, zOffset, extra, color, blockData, itemData);
            }

        }else if(function.toLowerCase().contains("img")){
            //JavaImageIO(self, target, action_Map, location);
            Map<Location, Integer> runLocation_Map = setLocationMap(self, target, location, action_Map);
            runLocation_Map.forEach((location1, integer) -> {
                location1.getWorld().spawnParticle(REDSTONE, location1, 1, 0, 0, 0, 0, new DustOptions(fromRGB(integer), 1));
            });


        }else {
            if(location != null){
                sendParticle(self, putParticle, location, count, xOffset, yOffset, zOffset, extra, color, blockData, itemData);
            }
        }


    }

    /**單點**/
    public static void sendParticle(LivingEntity self, Particle putParticle, Location location, int count, double xOffset, double yOffset, double zOffset, double extra, Particle.DustOptions color, BlockData blockData, ItemStack itemData){
        try{
            if(putParticle == REDSTONE){
                self.getWorld().spawnParticle(putParticle, location, count, xOffset, yOffset, zOffset, extra,color);
            }else if(putParticle == BLOCK_CRACK || putParticle == BLOCK_DUST){
                self.getWorld().spawnParticle(putParticle, location, count, xOffset, yOffset, zOffset, extra,blockData);
            }else if(putParticle == ITEM_CRACK ){
                self.getWorld().spawnParticle(ITEM_CRACK, location, count, xOffset, yOffset, zOffset, extra,itemData);
            } else {
                self.getWorld().spawnParticle(putParticle, location, count, xOffset, yOffset, zOffset, extra);
            }
        }catch (Exception exception){

        }


    }

//    /**圖片設定**/
//    public static void JavaImageIO(LivingEntity self, LivingEntity target, Map<String, String> action_Map, Location location){
//        CustomDisplay cd = CustomDisplay.getCustomDisplay();
//        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);
//
//        /**要使用的圖片名稱**/
//        String img = actionMapHandle.getString(new String[]{"img"},"");
//
//        /**要使用的圖片大小**/
//        double imgSize = actionMapHandle.getDouble(new String[]{"imgsize"},1);
//
//        //座標向量偏移
//        String[] vecAdds = actionMapHandle.getStringList(new String[]{"VectorAdd","va"},new String[]{"self","true","true","0","0","0"},"\\|",6);
//        String directionT = vecAdds[0];
//        boolean targetPitch = Boolean.parseBoolean(vecAdds[1]);
//        boolean targetYaw = Boolean.parseBoolean(vecAdds[2]);
//        double addX;
//        double addY;
//        double addZ;
//        try {
//            addX = Double.parseDouble(vecAdds[3]);
//            addY = Double.parseDouble(vecAdds[4]);
//            addZ = Double.parseDouble(vecAdds[5]);
//        }catch (NumberFormatException exception){
//            addX = 0;
//            addY = 0;
//            addZ = 0;
//        }
//
//        try{
//            BufferedImage bi = ImageIO.read(new File(cd.getDataFolder(),"Png/"+ img+".png"));
//            if(target != null && directionT.toLowerCase().contains("target")){
//
//                sendPic2(target, location, imgSize, bi, targetPitch, targetYaw, addX, addY, addZ);
//
//                //sendPic(target, location, addX, addY, addZ, imgSize, targetPitch, bi);
//            }else {
//                sendPic2(self, location, imgSize, bi, targetPitch, targetYaw, addX, addY, addZ);
//
//                //sendPic(self, location, addX, addY, addZ, imgSize, targetPitch, bi);
//
//            }
//
//        }catch (IOException exception){
//
//        }
//    }
//
//    //發送圖片粒子
//    public static void sendPic2(LivingEntity livingEntity, Location location, double imgSize, BufferedImage bi, boolean targetPitch, boolean targetYaw, double addX, double addY, double addZ){
//        CustomDisplay cd = CustomDisplay.getCustomDisplay();
//
//        double[] inputDouble = ThreeDLocation.getCosSin(livingEntity, targetPitch, targetYaw, addX, addY, addZ);
//
//        int width = bi.getWidth();
//
//        double widthHalf = (double)width/2;
//
//        int height = bi.getHeight();
//
//        double heightHalf = (double)height/2;
//
//        Map<Location,Integer> particles = new HashMap<>();
//        for(int i=0 ; i < height ; i++) {
//
//            for (int j = 0; j < width ; j++) {
//
//                int color = bi.getRGB(j, i);
//                int blue = color & 0xff;
//                int green = (color & 0xff00) >> 8;
//                int red = (color & 0xff0000) >> 16;
//                int alpha = (color & 0xff000000) >>> 24;
//                int rgb = ( (red*65536) + (green*256) +blue );
//
//                if(alpha != 0){
//                    //把高度置中
//                    double addHeight = 0;
//                    if(i == (heightHalf-0.5)){
//                        addHeight = -0.5;
//                    }else if(i >= heightHalf){
//                        addHeight = (i-heightHalf)*-1;
//                    }else {
//                        addHeight = (heightHalf-(i));
//                    }
//                    addHeight = addHeight * imgSize;
//                    //把寬度置中
//                    double addWidth = 0;
//                    if(j == (widthHalf-0.5)){
//                        addWidth = -0.5;
//                    }else if(j >= widthHalf){
//                        addWidth = (j-widthHalf);
//                    }else {
//                        addWidth = (widthHalf-(j))*-1;
//                    }
//                    addWidth = addWidth * imgSize;
//
//                    Location useLocation = location.clone().add(addWidth, addHeight, 0);
//
//                    useLocation = ThreeDLocation.getPngLocationX(useLocation.clone(), location.clone(), inputDouble);
//                    useLocation = ThreeDLocation.getPngLocationY(useLocation.clone(), location.clone(), inputDouble);
//                    useLocation = ThreeDLocation.getPngLocationZ(useLocation.clone(), location.clone(), inputDouble);
//
//                    particles.put(useLocation, rgb);
//
//                }
//
//            }
//        }
//        particles.forEach((location1, rgb) -> {
//            location1.getWorld().spawnParticle(REDSTONE, location1, 1, 0, 0, 0, 0, new DustOptions(fromRGB(rgb), 1));
//        });
//
//    }

    //圖片位置設定
    public static Map<Location, Integer> setLocationMap(LivingEntity self, LivingEntity livingEntity, Location location, Map<String, String> action_Map){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        Map<Location, Integer> setLocationMap  = new ConcurrentHashMap<>();
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, livingEntity);

        //要使用的圖片名稱
        String img = actionMapHandle.getString(new String[]{"img"},"");

        //圖片的縮放
        double imgSize = actionMapHandle.getDouble(new String[]{"imgsize"},1);

        //要使用的圖片角度
        String[] pngRotAngles = actionMapHandle.getStringList(new String[]{"ira","imgrotangle"},new String[]{"Self","true","true","0","0","0"},"\\|",6);
        String imgTarget = pngRotAngles[0].toLowerCase();
        boolean imgTargetPitch = Boolean.parseBoolean(pngRotAngles[1]);
        boolean imgTargetYaw = Boolean.parseBoolean(pngRotAngles[2]);
        double imgAddX;
        double imgAddY;
        double imgAddZ;
        try {
            imgAddX = Double.parseDouble(pngRotAngles[3]);
            imgAddY = Double.parseDouble(pngRotAngles[4]);
            imgAddZ = Double.parseDouble(pngRotAngles[5]);
        }catch (NumberFormatException exception){
            imgAddX = 0;
            imgAddY = 0;
            imgAddZ = 0;
        }


        try{
            BufferedImage bufferedImage = ImageIO.read(new File(cd.getDataFolder(),"Png/"+ img+".png"));


            double[] inputDouble;
            if(imgTarget.equals("self")){
                inputDouble = ThreeDLocation.getCosSin(self, imgTargetPitch, imgTargetYaw, imgAddX, imgAddY, imgAddZ);
            }else {
                inputDouble = ThreeDLocation.getCosSin(livingEntity, imgTargetPitch, imgTargetYaw, imgAddX, imgAddY, imgAddZ);
            }

            int width = bufferedImage.getWidth();

            double widthHalf = (double)width/2;

            int height = bufferedImage.getHeight();

            double heightHalf = (double)height/2;

            for(int i=0 ; i < height ; i++) {

                for (int j = 0; j < width ; j++) {

                    int color = bufferedImage.getRGB(j, i);
                    int blue = color & 0xff;
                    int green = (color & 0xff00) >> 8;
                    int red = (color & 0xff0000) >> 16;
                    int alpha = (color & 0xff000000) >>> 24;
                    int rgb = ( (red*65536) + (green*256) +blue );

                    if(alpha != 0){
                        //把高度置中
                        double addHeight;
                        if(i == (heightHalf-0.5)){
                            addHeight = -0.5;
                        }else if(i >= heightHalf){
                            addHeight = (i-heightHalf)*-1;
                        }else {
                            addHeight = (heightHalf-(i));
                        }
                        addHeight = addHeight * imgSize;
                        //把寬度置中
                        double addWidth;
                        if(j == (widthHalf-0.5)){
                            addWidth = -0.5;
                        }else if(j >= widthHalf){
                            addWidth = (j-widthHalf);
                        }else {
                            addWidth = (widthHalf-(j))*-1;
                        }
                        addWidth = addWidth * imgSize;

                        Location useLocation = location.clone().add(addWidth, addHeight, 0);

                        useLocation = ThreeDLocation.getPngLocationX(useLocation.clone(), location.clone(), inputDouble);
                        useLocation = ThreeDLocation.getPngLocationY(useLocation.clone(), location.clone(), inputDouble);
                        useLocation = ThreeDLocation.getPngLocationZ(useLocation.clone(), location.clone(), inputDouble);

                        setLocationMap.put(useLocation, rgb);


                    }else {
                        //String kkk = Integer.toHexString(rgb);
                        //cd.getLogger().info("顏色: "+rgb+" : "+kkk);
                    }

                }
            }


        }catch (IOException exception){
            //
        }

        return setLocationMap;
    }

}
