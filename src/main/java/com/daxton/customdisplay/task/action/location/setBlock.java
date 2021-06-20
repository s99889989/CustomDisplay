package com.daxton.customdisplay.task.action.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.location.ThreeDLocation;
import com.daxton.customdisplay.api.other.StringConversion;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class setBlock {

    public static void go(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID, Location inputLocation){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        //cd.getLogger().info("改變方塊");
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        //粒子的方塊材質
        BlockData blockData = actionMapHandle.getBlockData(new String[]{"blockdata","bdata"},"STONE");

        //要使用的圖片名稱
        String img = actionMapHandle.getString(new String[]{"img"},null);

        Location location = actionMapHandle.getLocation(inputLocation);

        //BlockData oblock = location.getBlock().getBlockData();


        if(img != null){



            Map<Location, Integer> runLocation_Map = setLocationMap(self, target, location, action_Map);



            runLocation_Map.forEach((location1, integer) -> {
                StringBuilder kkk = new StringBuilder(Integer.toHexString(integer));
                while (kkk.length() < 6){
                    kkk.insert(0, "0");
                }
                //cd.getLogger().info(kkk.toString());
                FileConfiguration colorFile = ConfigMapManager.getFileConfigurationMap().get("Other_BlockColor.yml");
                String colorString = colorFile.getString(kkk.toString());
                BlockData colorBlock = StringConversion.getBlockData(self, target, "STONE", colorString);
                location1.getBlock().setBlockData(colorBlock);

            });




        }else {
            location.getBlock().setBlockData(blockData);

        }




    }

    //圖片位置設定
    public static Map<Location, Integer> setLocationMap(LivingEntity self, LivingEntity livingEntity, Location location, Map<String, String> action_Map){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();





        Map<Location, Integer> setLocationMap  = new ConcurrentHashMap<>();
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, livingEntity);

        //要使用的圖片名稱
        String img = actionMapHandle.getString(new String[]{"img"},"");

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

            int height = bufferedImage.getHeight();


            int widthMid = width/2;
            int heightMid = height/2;


            int oX = (int) location.getX();
            int oY = (int) location.getY();
            int oZ = (int) location.getZ();
            Location location2 = location.clone().set(oX - widthMid, oY - heightMid, oZ);
            location.set(oX, oY, oZ);

           // cd.getLogger().info(widthMid+" : "+heightMid);
            int ii = height;

            for(int i = 0 ; i < height ; i++) {
                ii--;
                int jj = width;
                for (int j = 0 ; j < width ; j++) {
                    jj--;
                    int color = bufferedImage.getRGB(jj, ii);
                    int blue = color & 0xff;
                    int green = (color & 0xff00) >> 8;
                    int red = (color & 0xff0000) >> 16;
                    int alpha = (color & 0xff000000) >>> 24;
                    int rgb = ( (red*65536) + (green*256) +blue );

                    if(alpha != 0){

                        Location useLocation = location2.clone().add(j, i, 0);

                        useLocation = ThreeDLocation.getPngLocationX(useLocation.clone(), location.clone(), inputDouble);
                        useLocation = ThreeDLocation.getPngLocationY(useLocation.clone(), location.clone(), inputDouble);
                        useLocation = ThreeDLocation.getPngLocationZ(useLocation.clone(), location.clone(), inputDouble);
                        //String kkk = Integer.toHexString(rgb);
                        //cd.getLogger().info(i+" : "+ii+" : "+j+" : "+jj+" : "+kkk);
                        //cd.getLogger().info(useLocation.getX()+" : "+useLocation.getY()+" : "+useLocation.getZ());
                        //if(String.valueOf(useLocation.getX()).endsWith(".0") && String.valueOf(useLocation.getY()).endsWith(".0") && String.valueOf(useLocation.getZ()).endsWith(".0")){
                            setLocationMap.put(useLocation, rgb);
                        //}



                    }

                }
            }


        }catch (IOException exception){
            //
        }

        return setLocationMap;
    }

}
