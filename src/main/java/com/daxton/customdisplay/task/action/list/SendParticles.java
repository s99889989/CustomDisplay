package com.daxton.customdisplay.task.action.list;


import com.daxton.customdisplay.CustomDisplay;

import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.PlaceholderManager;

import com.sun.imageio.plugins.gif.GIFImageReader;
import com.sun.imageio.plugins.gif.GIFImageReaderSpi;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;

import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


import javax.imageio.ImageIO;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.bukkit.Color.*;
import static org.bukkit.Particle.*;

public class SendParticles {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private LivingEntity self = null;
    private LivingEntity target = null;

    private String function = "";
    private Particle putParticle;
    private Particle inParticle;
    private BlockData blockData;
    private ItemStack itemData;
    private String img;
    private double imgSize;
    private String imgStyle;
    private String imgRotMeth;
    private String imgRotAngle;
    private Boolean imgTargetAngle;
    private double imgX;
    private double imgY;
    private double imgZ;
    private BufferedImage[] bufferedImages = null;
    private int gifCount = 0;

    private Particle.DustOptions color = new Particle.DustOptions(fromRGB(0xFF0000), 1);
    private Location location = new Location(Bukkit.getWorld("world"),0,0,0);
    private String aims = "";
    private double extra = 0;
    private int count = 10;
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double xOffset = 0;
    private double yOffset = 0;
    private double zOffset = 0;


    public SendParticles(){



    }

    public void setParticles(LivingEntity self,LivingEntity target, String firstString, String taskID){
        this.taskID = taskID;
        this.self = self;
        this.target = target;
        stringSetting(firstString);


    }

    public void stringSetting(String firstString){

        /**獲得功能**/
        function = new StringFind().getKeyValue(self,target,firstString,"[];","fc=","function=");
        /**目標**/
        aims = new StringFind().getKeyValue(self,target,firstString,"[];","@=");
        /**粒子名稱**/
        try{
            putParticle = Enum.valueOf(Particle.class,new StringFind().getKeyValue(self,target,firstString,"[];","particle=").toUpperCase());
        }catch (Exception exception){
            putParticle = REDSTONE;
        }
        /**要使用的圖片名稱**/
        img = new StringFind().getKeyValue(self,target,firstString,"[];","img=");
        /**要使用的圖片大小**/
        try {
            imgSize = Double.valueOf(new StringFind().getKeyValue(self,target,firstString,"[];","imgsize="));
        }catch (NumberFormatException exception){
            imgSize = 1;
        }
        /**圖片的樣式**/
        imgStyle = new StringFind().getKeyValue(self,target,firstString,"[];","imgstyle=");
        /**圖片的樣式**/
        imgRotMeth = new StringFind().getKeyValue(self,target,firstString,"[];","imgrotmeth=");

        /**是否加上目標角度角度**/
        imgTargetAngle = Boolean.valueOf(new StringFind().getKeyValue(self,target,firstString,"[];","imgtargetangel="));

        /**要使用的圖片角度**/
        imgRotAngle = new StringFind().getKeyValue(self,target,firstString,"[];","imgrotangle=");
        String[] pngRotAngles = imgRotAngle.split("\\|");
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

        /**粒子的方塊材質**/
        try {
            blockData = Enum.valueOf(Material.class,new StringFind().getKeyValue(self,target,firstString,"[];","particledata=","pdata=").toUpperCase()).createBlockData();
        }catch (Exception exception){
            blockData = Material.REDSTONE_BLOCK.createBlockData();
        }
        /**粒子的物品材質**/
        try {
            itemData = new ItemStack(Enum.valueOf(Material.class,new StringFind().getKeyValue(self,target,firstString,"[];","particledata=","pdata=").toUpperCase()));
        }catch (Exception exception){
            itemData = new ItemStack(Material.COOKIE);
        }
        /**粒子的顏色**/
        try{
            BigInteger bigint = new BigInteger(new StringFind().getKeyValue(self,target,firstString,"[];","particledata=","pdata="), 16);
            int numb = bigint.intValue();
            color = new Particle.DustOptions(fromRGB(numb), 1);
        }catch (NumberFormatException exception){
            BigInteger bigint = new BigInteger("FF0000", 16);
            int numb = bigint.intValue();
            color = new Particle.DustOptions(fromRGB(numb), 1);
        }
        /**粒子的速度**/
        try {
            extra = Double.valueOf(new StringFind().getKeyValue(self,target,firstString,"[];","extra="));
        }catch (NumberFormatException exception){
            extra = 0;
        }
        /**粒子的數量**/
        try {
            count = Integer.valueOf(new StringFind().getKeyValue(self,target,firstString,"[];","count="));
        }catch (NumberFormatException exception){
            count = 1;
        }


        /**粒子中心點位置**/
        String locAdd = new StringFind().getKeyValue(self,target,firstString,"[];","locationadd=","locadd=");
        String[] locAdds = locAdd.split("\\|");
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
        String locOff = new StringFind().getKeyValue(self,target,firstString,"[];","locationoffset=","locoff=");
        String[] locOffs = locOff.split("\\|");
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



        if(aims.toLowerCase().contains("target")){
            if(putParticle != null){
                String particle = putParticle.toString().toLowerCase();
                PlaceholderManager.getParticles_function().put(target.getUniqueId().toString()+"particle",particle);
            }
            PlaceholderManager.getParticles_function().put(target.getUniqueId().toString()+"function",function);
            location = target.getLocation().add(x,y,z);
        }else if(aims.toLowerCase().contains("self")){
            if(putParticle != null){
                String particle = putParticle.toString().toLowerCase();
                PlaceholderManager.getParticles_function().put(self.getUniqueId().toString()+"particle",particle);
            }
            PlaceholderManager.getParticles_function().put(self.getUniqueId().toString()+"function",function);
            location = self.getLocation().add(x,y,z);
        }else if(aims.toLowerCase().contains("world")){
            location = new Location(self.getWorld(),x,y,z);
        }else {
            location = location.add(x,y,z);
        }

        if(function.toLowerCase().contains("remove")){

        }else if(function.toLowerCase().contains("add")){
            sendParticle();
        }else if(function.toLowerCase().contains("circular")){
            sendParticleCircular();
        }else if(function.toLowerCase().contains("png")){
            //Test();
            JavaImageIOTest();
            //TestParticle(target,cd);
        } else {
            sendParticle();
        }

    }
    /**單點**/
    public void sendParticle(){
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

    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++) {
            if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName)== 0) {
                return((IIOMetadataNode) rootNode.item(i));
            }
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return(node);
    }

    public void JavaImageIOTest(){
        try{
            BufferedImage bi = ImageIO.read(new File(cd.getDataFolder(),"Png/"+ this.img+".png"));
            sendPic(bi);
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




        }catch (IOException exception){

        }
    }

    public void sendPic(BufferedImage bi){

        int width = bi.getWidth();

        double widthHalf = (double)width/2;

        int height = bi.getHeight();

        double heightHalf = (double)height/2;

        /**pngX rotY pngZ 為設定給的角度**/
        /**target為攻擊目標**/
        double rotX = 0;
        double rotY = 0;
        double rotZ = 0;
//        try {
//            imgX = Double.valueOf(imgRotAngle);
//        }catch (NumberFormatException exception){
//            imgX = 0;
//        }
        if(imgTargetAngle){
            if(target != null && aims.toLowerCase().contains("target")){
                rotX = target.getLocation().getYaw()+ imgX;
                rotY = target.getLocation().getPitch()+ imgX; //+pngY
                rotZ = target.getLocation().getYaw()+ imgX; // pngZ
            }else {
                rotX = self.getLocation().getYaw()+ imgX;
                rotY = self.getLocation().getPitch()+ imgX; // pngY
                rotZ = self.getLocation().getYaw()+ imgX; // pngZ
            }
        }else {
            rotX = imgX;
            rotY = imgY;
            rotZ = imgZ;
        }

        if(rotY > 359){
            rotY %= 360;
        }
        while (rotY < 0){
            rotY +=360;
        }
        if(rotX > 359){
            rotX %= 360;
        }
        while (rotX < 0){
            rotX +=360;
        }
        if(rotZ > 359){
            rotZ %= 360;
        }
        while (rotZ < 0){
            rotZ +=360;
        }

        cd.getLogger().info("X: "+rotX+" Y: "+rotY+" Z: "+rotZ);
//        double cos90 = -Math.cos(Math.toRadians(target.getLocation().getYaw() - 90));
//        double sin90 = -Math.sin(Math.toRadians(target.getLocation().getYaw() - 90));
//        Location firstLoc = null;
//        if(imgStyle.toLowerCase().contains("v")){
//            firstLoc = target.getLocation().add(target.getLocation().getDirection().getX()*-1,2,target.getLocation().getDirection().getZ()*-1);
//        }else {
//            //firstLoc = target.getLocation().add(target.getLocation().getDirection().getX()*-1,2,target.getLocation().getDirection().getZ()*-1);
//            firstLoc = target.getLocation().add(0,2,0);
//        }

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
                    /**把寬度置中**/
                    double addWidth = 0;
                    if(j == (widthHalf-0.5)){
                        addWidth = -0.5;
                    }else if(j >= widthHalf){
                        addWidth = (j-widthHalf);
                    }else {
                        addWidth = (widthHalf-(j))*-1;
                    }

//                    /**水平公式**/
//                    double hyx = ((addWidth*Math.cos(Math.toRadians(rotX)))+(addHeight*Math.sin(Math.toRadians(rotX)))) * imgSize; //-Math.cos(Math.toRadians(0)) *
//                    double hyy = addWidth * imgSize;
//                    double hyz = ((addWidth*Math.sin(Math.toRadians(rotX))*-1)+(addHeight*Math.cos(Math.toRadians(rotX)))) * imgSize;
//
//                    /**pngSytle選擇水平還是垂直**/
//                    if(imgStyle.toLowerCase().contains("v")){
//                        /**垂直**/
//                        /**pngRotMeth選擇已哪軸旋轉**/
//                        if(imgRotMeth.toLowerCase().contains("x")){ /**沿著X旋轉**/            /**pngSize為粒子位置縮放**/              /**count為粒子數量**/
//                            double vyx = addHeight* imgSize;
//                            double vyy = -Math.cos(Math.toRadians(rotY)) * addWidth * imgSize;
//                            double vyz = -Math.sin(Math.toRadians(rotY)) * addWidth * imgSize;
//                            Location lastLocation = firstLoc.clone().add(vyx, vyy, vyz);
//                            lastLocation.add(cos90, 0, sin90);
//                            particles.put(lastLocation, rgb);
//                            //self.getWorld().spawnParticle(REDSTONE, location.clone().add(vy1*pngSize, vx1*pngSize, vz1*pngSize), count, 0, 0, 0, 0,new Particle.DustOptions(fromRGB(rgb), 1));
//                            //particles.put(new Vector(vy1y*pngSize,vx1y*pngSize,vz1y*pngSize), rgb);
//                        }else if(imgRotMeth.toLowerCase().contains("z")){ /**沿著Z旋轉**/
//                            double vyx = -Math.cos(Math.toRadians(rotY)) * addWidth * imgSize;
//                            double vyy = -Math.sin(Math.toRadians(rotY)) * addWidth * imgSize;
//                            double vyz = addHeight* imgSize;
//                            Location lastLocation = firstLoc.clone().add(vyx, vyy, vyz);
//                            lastLocation.add(cos90, 0, sin90);
//                            particles.put(lastLocation, rgb);
//                            //self.getWorld().spawnParticle(REDSTONE, location.clone().add(vx1*pngSize, vz1*pngSize, vy1*pngSize), count, 0, 0, 0, 0,new Particle.DustOptions(fromRGB(rgb), 1));
//                            //particles.put(new Vector(vx1*pngSize,vz1*pngSize,vy1*pngSize), rgb);
//                        }else {  /**沿著Y旋轉**/
//
//                            double vyx = -Math.cos(Math.toRadians(rotX)) * addWidth * imgSize;
//                            double vyy = addHeight* imgSize;
//                            double vyz = -Math.sin(Math.toRadians(rotX)) * addWidth * imgSize;
//                            Location lastLocation = firstLoc.clone().add(vyx, vyy, vyz);
//                            lastLocation.add(cos90, 0, sin90);
//                            particles.put(lastLocation, rgb);
//                            //self.getWorld().spawnParticle(REDSTONE, lastLocation, count, 0, 0, 0, 0,new Particle.DustOptions(fromRGB(rgb), 1));
//                            //particles.put(new Vector(vyx* imgSize,vyy* imgSize,vyz* imgSize), rgb);
//                            //self.getWorld().spawnParticle(REDSTONE, firstLoc, count, 0, 0, 0, 0,new Particle.DustOptions(fromRGB(rgb), 1));
//                        }
//                    }else {
//                        /**水平**/
//                        if(imgRotMeth.toLowerCase().contains("x")){ /**沿著X旋轉**/
//                            double vyx = ((addWidth*Math.cos(Math.toRadians(0)))+(addHeight*Math.sin(Math.toRadians(0)))) * imgSize; //-Math.cos(Math.toRadians(0)) *
//                            double vyy = 0;
//                            double vyz = ((addWidth*Math.sin(Math.toRadians(0))*-1)+(addHeight*Math.cos(Math.toRadians(0)))) * imgSize;
//                            Location lastLocation = firstLoc.clone().add(vyx, vyy, vyz);
//                            particles.put(lastLocation, rgb);
//                            //self.getWorld().spawnParticle(REDSTONE, location.clone().add(hy1*pngSize, hx1*pngSize, hz1*pngSize), count, 0, 0, 0, 0,new Particle.DustOptions(fromRGB(rgb), 1));
//                            //particles.put(new Vector(hy1* imgSize,hx1* imgSize,hz1* imgSize), rgb);
//                        }else if(imgRotMeth.toLowerCase().contains("z")){ /**沿著Z旋轉**/
//
//                            //self.getWorld().spawnParticle(REDSTONE, location.clone().add(hx1*pngSize, hz1*pngSize, hy1*pngSize), count, 0, 0, 0, 0,new Particle.DustOptions(fromRGB(rgb), 1));
//                            //particles.put(new Vector(hx1* imgSize,hz1* imgSize,hy1* imgSize), rgb);
//                        }else { /**沿著Y旋轉**/
//
//                            Location lastLocation = firstLoc.clone().add(hyx, hyy, hyz);
//                            particles.put(lastLocation, rgb);
//                            //self.getWorld().spawnParticle(REDSTONE, location.clone().add(hx1*pngSize, hy1*pngSize, hz1*pngSize), count, 0, 0, 0, 0,new Particle.DustOptions(fromRGB(rgb), 1));
//                            //particles.put(new Vector(hx1* imgSize,hy1* imgSize,hz1* imgSize), rgb);
//                        }
//                    }

                }

            }
        }
//        particles.forEach((location, rgb) -> {
//            location.getWorld().spawnParticle(REDSTONE, location, 1, new Particle.DustOptions(fromRGB(rgb), 1));
//            //location.subtract(vector);
//        });


        //spawnAngularParticle(location, Math.toRadians(rotX), Math.toRadians(rotY), 0, 0, 0, particles);

    }



    public static void spawnAngularParticle(Location location, double alpha, double beta, double offsetX, double offsetY, double offsetZ, Map<Vector, Integer> particles) {
        World world = location.getWorld();
        assert world != null;

//        double x = Math.cos(alpha) * Math.cos(beta);
//        double z = Math.sin(alpha) * Math.cos(beta);
//        double y = Math.sin(beta);

        double x = Math.cos(alpha) + Math.cos(alpha);
        double z = (Math.sin(alpha)*-1) + Math.cos(alpha);
        double y = 0;

        //location.add(new Vector(x, y, z));
        particles.forEach((vector, rgb) -> {


            world.spawnParticle(REDSTONE, location.add(vector), 1, new Particle.DustOptions(fromRGB(rgb), 1));
            location.subtract(vector);
        });
    }

    public void spawnRun(Map<Vector, Integer> particles){
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if(i >= 360){
                    cancel();
                    return;
                }
                double sinB = Math.sin(Math.toRadians(i));
                double cosB = Math.cos(Math.toRadians(i));

                particles.forEach((vector, rgb) -> {
                    int j = i;
                    location.getWorld().spawnParticle(REDSTONE, location.add(vector), 1, new Particle.DustOptions(fromRGB(rgb), 1));
                    location.subtract(vector);
                });

                i+=10;
            }
        };
        bukkitRunnable.runTaskTimer(cd,0,1);

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

}
