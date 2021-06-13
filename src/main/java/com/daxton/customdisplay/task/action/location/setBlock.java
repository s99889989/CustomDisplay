package com.daxton.customdisplay.task.action.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.location.ThreeDLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class setBlock {

    public static void go(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID, Location inputLocation){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        cd.getLogger().info("改變方塊");
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        //粒子的方塊材質
        BlockData blockData = actionMapHandle.getBlockData(new String[]{"blockdata","bdata"},"STONE");

        //要使用的圖片名稱
        String img = actionMapHandle.getString(new String[]{"img"},null);

        Location location = actionMapHandle.getLocation(inputLocation);

        BlockData oblock = location.getBlock().getBlockData();
        if(img != null){
            Map<Integer, Location> runLocation_Map = setLocationMap(self, target, location, action_Map);

            runLocation_Map.forEach((integer, location1) -> {
                location1.getBlock().setBlockData(blockData);
            });

            new BukkitRunnable() {
                @Override
                public void run() {
                    runLocation_Map.forEach((integer, location1) -> {
                        location1.getBlock().setBlockData(oblock);
                    });
                }
            }.runTaskLater(cd, 40);



        }else {
            location.getBlock().setBlockData(blockData);

            new BukkitRunnable() {
                @Override
                public void run() {
                    location.getBlock().setBlockData(oblock);
                }
            }.runTaskLater(cd, 40);
        }




    }

    //圖片位置設定
    public static Map<Integer, Location> setLocationMap(LivingEntity self, LivingEntity livingEntity, Location location, Map<String, String> action_Map){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        Map<Integer, Location> setLocationMap  = new ConcurrentHashMap<>();
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, livingEntity);

        //要使用的圖片名稱
        String img = actionMapHandle.getString(new String[]{"img"},"");

        //圖片的縮放
        double imgSize = actionMapHandle.getDouble(new String[]{"is","imgsize"},1);

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
            int count = 1;
            for(int i=0 ; i < height ; i++) {

                for (int j = 0; j < width ; j++) {

                    int color = bufferedImage.getRGB(j, i);
                    //int blue = color & 0xff;
                    //int green = (color & 0xff00) >> 8;
                    //int red = (color & 0xff0000) >> 16;
                    int alpha = (color & 0xff000000) >>> 24;
                    //int rgb = ( (red*65536) + (green*256) +blue );

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

                        setLocationMap.put(count, useLocation);
                        count++;

                    }

                }
            }


        }catch (IOException exception){
            //
        }

        return setLocationMap;
    }

}
