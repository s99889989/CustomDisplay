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

import static org.bukkit.Color.fromRGB;
import static org.bukkit.Particle.*;

public class SendParticles3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private LivingEntity self = null;
    private LivingEntity target = null;
    private Map<String, String> action_Map;


    private String function = "";
    private Particle putParticle;
    private BlockData blockData;
    private ItemStack itemData;

    private DustOptions color = new DustOptions(fromRGB(0xFF0000), 1);
    private Location location = new Location(Bukkit.getWorld("world"),0,0,0);
    private double extra = 0;
    private int count = 10;
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double xOffset = 0;
    private double yOffset = 0;
    private double zOffset = 0;


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
            JavaImageIO(self, target, action_Map, location);
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

    /**圖片設定**/
    public static void JavaImageIO(LivingEntity self, LivingEntity target, Map<String, String> action_Map, Location location){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**要使用的圖片名稱**/
        String img = actionMapHandle.getString(new String[]{"img"},"");

        /**要使用的圖片大小**/
        double imgSize = actionMapHandle.getDouble(new String[]{"imgsize"},1);

        //座標向量偏移
        String[] vecAdds = actionMapHandle.getStringList(new String[]{"VectorAdd","va"},new String[]{"self","true","true","0","0","0"},"\\|",6);
        String directionT = vecAdds[0];
        boolean targetPitch = Boolean.parseBoolean(vecAdds[1]);
        boolean targetYaw = Boolean.parseBoolean(vecAdds[2]);
        double addX = 0;
        double addY = 0;
        double addZ = 0;
        try {
            addX = Double.parseDouble(vecAdds[3]);
            addY = Double.parseDouble(vecAdds[4]);
            addZ = Double.parseDouble(vecAdds[5]);
        }catch (NumberFormatException exception){
            addX = 0;
            addY = 0;
            addZ = 0;
        }

        try{
            BufferedImage bi = ImageIO.read(new File(cd.getDataFolder(),"Png/"+ img+".png"));
            if(target != null && directionT.toLowerCase().contains("target")){

                sendPic2(target, location, imgSize, bi, targetPitch, targetYaw, addX, addY, addZ);

                //sendPic(target, location, addX, addY, addZ, imgSize, targetPitch, bi);
            }else {
                sendPic2(self, location, imgSize, bi, targetPitch, targetYaw, addX, addY, addZ);

                //sendPic(self, location, addX, addY, addZ, imgSize, targetPitch, bi);

            }

        }catch (IOException exception){

        }
    }

    /**發送圖片粒子**/
    public static void sendPic2(LivingEntity livingEntity, Location location, double imgSize, BufferedImage bi, boolean targetPitch, boolean targetYaw, double addX, double addY, double addZ){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        double[] inputDouble = ThreeDLocation.getCosSin(livingEntity, targetPitch, targetYaw, addX, addY, addZ);

        int width = bi.getWidth();

        double widthHalf = (double)width/2;

        int height = bi.getHeight();

        double heightHalf = (double)height/2;

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
                    //把高度置中
                    double addHeight = 0;
                    if(i == (heightHalf-0.5)){
                        addHeight = -0.5;
                    }else if(i >= heightHalf){
                        addHeight = (i-heightHalf)*-1;
                    }else {
                        addHeight = (heightHalf-(i));
                    }
                    addHeight = addHeight * imgSize;
                    //把寬度置中
                    double addWidth = 0;
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

                    particles.put(useLocation, rgb);

                }

            }
        }
        particles.forEach((location1, rgb) -> {
            location1.getWorld().spawnParticle(REDSTONE, location1, 1, 0, 0, 0, 0, new DustOptions(fromRGB(rgb), 1));
        });

    }

    /**發送圖片粒子**/
    public static void sendPic(LivingEntity livingEntity, Location location, double imgX, double imgY, double imgZ, double imgSize, boolean imgTargetAngle, BufferedImage bi){

        int width = bi.getWidth();

        double widthHalf = (double)width/2;

        int height = bi.getHeight();

        double heightHalf = (double)height/2;

        //pngX rotY pngZ 為設定給的角度
        //target為攻擊目標
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
        //繞Y軸旋轉
        double cosY = Math.cos(Math.toRadians(rotY));
        double sinY = Math.sin(Math.toRadians(rotY));
        //繞X軸旋轉
        double cosX = Math.cos(Math.toRadians(rotX));
        double sinX = Math.sin(Math.toRadians(rotX));
        //繞Z軸旋轉
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
                    //把高度置中
                    double addHeight = 0;
                    if(i == (heightHalf-0.5)){
                        addHeight = -0.5;
                    }else if(i >= heightHalf){
                        addHeight = (i-heightHalf)*-1;
                    }else {
                        addHeight = (heightHalf-(i));
                    }
                    addHeight = addHeight * imgSize;
                    //把寬度置中
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
        particles.forEach((location1, rgb) -> {

            location1.getWorld().spawnParticle(REDSTONE, location1, 1, 0, 0, 0, 0, new DustOptions(fromRGB(rgb), 1));
        });

    }


    /**圓圈**/
    public void sendParticleCircular(){
        cd.getLogger().info("圓圈");
        try{
            if(putParticle == REDSTONE){
                List<Location> circleList = circle(location,20,0,true,true,0);
//                for(Location location1 : circleList){
//                    self.getWorld().spawnParticle(putParticle, location1, count, xOffset, yOffset, zOffset, extra,color);
//                }

            }else if(putParticle == BLOCK_CRACK || putParticle == BLOCK_DUST){
                self.getWorld().spawnParticle(putParticle, location, count, xOffset, yOffset, zOffset, extra,blockData);
            }else if(putParticle == ITEM_CRACK ){
                self.getWorld().spawnParticle(ITEM_CRACK, location, count, xOffset, yOffset, zOffset, extra,itemData);
            } else {
                self.getWorld().spawnParticle(putParticle, location, count, xOffset, yOffset, zOffset, extra);
            }
        }catch (Exception e){

        }



    }

    /**圓圈**/
    public static List<Location> circle (Location loc, Integer r, Integer h, Boolean hollow, Boolean sphere, int plus_y) {
        List<Location> circleblocks = new ArrayList<Location>();
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        for (int x = cx - r; x <= cx +r; x++)
            for (int z = cz - r; z <= cz +r; z++)
                for (int y = (sphere ? cy - r : cy); y < (sphere ? cy + r : cy + h); y++) {
                    double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);
                    if (dist < r*r && !(hollow && dist < (r-1)*(r-1))) {
                        Location l = new Location(loc.getWorld(), x, y + plus_y, z);
                        circleblocks.add(l);
                    }
                }

        return circleblocks;
    }


    //            BufferedImage bi = null;
//            BufferedImage[] img = null;
//            GIFImageReader reader = new GIFImageReader(new GIFImageReaderSpi());
//            if(this.img.contains(".gif")){
//                reader.setInput(new FileImageInputStream(new File(cd.getDataFolder(),"Png/"+ this.img)));
//                int num = reader.getNumImages(true);
//                img = new BufferedImage[num];
//                for(int i = 0; i < num ;i++){
//                    img[i] = reader.read(i);
//                }
//                bufferedImages = img;
//                int delayTime = 0;
//                for(int b = 0 ; b < img.length ;b++){
//                    gifCount = b;
//                    IIOMetadata imageMetaData =  reader.getImageMetadata(b);
//                    String metaFormatName = imageMetaData.getNativeMetadataFormatName();
//                    IIOMetadataNode root = (IIOMetadataNode)imageMetaData.getAsTree(metaFormatName);
//                    IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
//                    delayTime = delayTime + Integer.valueOf(graphicsControlExtensionNode.getAttribute("delayTime"));
//                    BukkitRunnable bukkitRunnable = new BukkitRunnable() {
//                        @Override
//                        public void run() {
//                            sendPic(bufferedImages[gifCount]);
//                        }
//                    };
//                    bukkitRunnable.runTaskLater(cd,delayTime);
//                }
//
//
//
//
//            }else {
//                bi = ImageIO.read(new File(cd.getDataFolder(),"Png/"+ this.img));
//                sendPic(bi);
//            }

}
