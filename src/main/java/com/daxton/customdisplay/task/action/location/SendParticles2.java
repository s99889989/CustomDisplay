package com.daxton.customdisplay.task.action.location;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
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

public class SendParticles2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private LivingEntity self = null;
    private LivingEntity target = null;

    private CustomLineConfig customLineConfig;

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


    public SendParticles2(){



    }

    public void setParticles(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){
        this.taskID = taskID;
        this.self = self;
        this.target = target;
        this.customLineConfig = customLineConfig;
        stringSetting();


    }

    public void stringSetting(){

        /**獲得功能**/
        function = customLineConfig.getString(new String[]{"function","fc"},"",self,target);

        /**粒子名稱**/
        putParticle = customLineConfig.getParticle(new String[]{"particle"},"REDSTONE",self,target);

        /**粒子的方塊材質**/
        blockData = customLineConfig.getBlockData(new String[]{"particledata","pdata"},"REDSTONE_BLOCK",self,target);

        /**粒子的物品材質**/
        itemData = customLineConfig.getItemStack(new String[]{"particledata","pdata"},"COOKIE",self,target);

        /**粒子的顏色**/
        color = customLineConfig.getParticleColor(new String[]{"particledata","pdata"},"FF0000",self,target);

        /**粒子的速度**/
        extra = customLineConfig.getDouble(new String[]{"extra"},0,self,target);

        /**粒子的數量**/
        count = customLineConfig.getInt(new String[]{"count"},10,self,target);

        /**粒子中心點位置**/
        String[] locAdds = customLineConfig.getStringList(new String[]{"locationadd","locadd"},new String[]{"0","0","0"},"\\|",3,self,target);

        if(locAdds.length == 3){
            try {
                x = Double.valueOf(locAdds[0]);
                y = Double.valueOf(locAdds[1]);
                z = Double.valueOf(locAdds[2]);
            }catch (NumberFormatException exception){
                x = 0;
                y = 0;
                z = 0;
            }
        }
        /**粒子擴散**/
        String[] locOffs = customLineConfig.getStringList(new String[]{"locationoffset","locoff"},new String[]{"0","0","0"},"\\|",3,self,target);
        if(locOffs.length == 3){
            try {
                xOffset = Double.valueOf(locAdds[0]);
                yOffset = Double.valueOf(locAdds[1]);
                zOffset = Double.valueOf(locAdds[2]);
            }catch (NumberFormatException exception){
                xOffset = 0;
                yOffset = 0;
                zOffset = 0;
            }
        }
        String locTarget = customLineConfig.getString(new String[]{"loctarget","lt"},"target", self, target);
//        if(locTarget.toLowerCase().contains("self")){
//            location = self.getLocation().add(x,y,z);
//        }else if(locTarget.toLowerCase().contains("target")){
//            if(target != null){
//                location = target.getLocation().add(x,y,z);
//            }
//        }else {
//            location = location.add(x,y,z);
//        }

        List<LivingEntity> targetList = customLineConfig.getLivingEntityList(self,target);

        for(LivingEntity livingEntity : targetList){
            Location location = livingEntity.getLocation();
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

            }else if(function.toLowerCase().contains("add")){

                sendParticle(putParticle, self, location);

            }else if(function.toLowerCase().contains("circular")){

            }else if(function.toLowerCase().contains("img")){
                if(locTarget.toLowerCase().contains("self")){
                    JavaImageIO(self, location);
                }else if(locTarget.toLowerCase().contains("target")){
                    if(target != null){
                        JavaImageIO(target, location);
                    }
                }else {
                    JavaImageIO(null, location);
                }

            } else {
                sendParticle(putParticle, self, location);
            }
        }




    }

    /**單點**/
    public void sendParticle(Particle putParticle,LivingEntity self,Location location){
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
        }catch (Exception e){

        }


    }

    /**圖片設定**/
    public void JavaImageIO(LivingEntity livingEntity, Location location){

        /**要使用的圖片名稱**/
        String img = customLineConfig.getString(new String[]{"img"},"",self,target);

        /**要使用的圖片大小**/
        double imgSize = customLineConfig.getDouble(new String[]{"imgsize"},1,self,target);

        /**是否加上目標角度角度**/
        boolean imgTargetAngle = customLineConfig.getBoolean(new String[]{"imgtargetangel"}, false,self,target);

        /**要使用的圖片角度**/
        String[] pngRotAngles = customLineConfig.getStringList(new String[]{"imgrotangle"},new String[]{"0","0","0"},"\\|",3,self,target);

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
        particles.forEach((location1, rgb) -> {
            location1.getWorld().spawnParticle(REDSTONE, location1, count, xOffset, yOffset, zOffset, extra, new DustOptions(fromRGB(rgb), 1));
        });
        //self.getWorld().spawnParticle(putParticle, location, count, xOffset, yOffset, zOffset, extra,color);
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