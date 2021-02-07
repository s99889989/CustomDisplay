package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.wrappers.BlockPosition;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.PlaceholderManager;

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;
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
        extra = Double.valueOf(new StringFind().getKeyValue(self,target,firstString,"[];","extra="));
        /**粒子的數量**/
        count = Integer.valueOf(new StringFind().getKeyValue(self,target,firstString,"[];","count="));
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
        } else {
            sendParticle();
        }

    }
    /**單點**/
    public void sendParticle(){
        try{
            if(putParticle == REDSTONE){
                self.getWorld().spawnParticle(putParticle, location.add(x, y, z), count, xOffset, yOffset, zOffset, extra,color);
            }else if(putParticle == BLOCK_CRACK || putParticle == BLOCK_DUST){
                self.getWorld().spawnParticle(putParticle, location.add(x, y, z), count, xOffset, yOffset, zOffset, extra,blockData);
            }else if(putParticle == ITEM_CRACK ){
                self.getWorld().spawnParticle(ITEM_CRACK, location.add(x, y, z), count, xOffset, yOffset, zOffset, extra,itemData);
            } else {
                self.getWorld().spawnParticle(putParticle, location.add(x, y, z), count, xOffset, yOffset, zOffset, extra);
            }
        }catch (Exception e){

        }


    }



    /**圓圈**/
    public void sendParticleCircular(){
        cd.getLogger().info("圓圈");
        try{
            if(putParticle == REDSTONE){
                List<Location> circleList = circle(location.add(x, y, z),10,0,true,true,0);
                for(Location location1 : circleList){
                    self.getWorld().spawnParticle(putParticle, location1, count, xOffset, yOffset, zOffset, extra,color);
                }

            }else if(putParticle == BLOCK_CRACK || putParticle == BLOCK_DUST){
                self.getWorld().spawnParticle(putParticle, location.add(x, y, z), count, xOffset, yOffset, zOffset, extra,blockData);
            }else if(putParticle == ITEM_CRACK ){
                self.getWorld().spawnParticle(ITEM_CRACK, location.add(x, y, z), count, xOffset, yOffset, zOffset, extra,itemData);
            } else {
                self.getWorld().spawnParticle(putParticle, location.add(x, y, z), count, xOffset, yOffset, zOffset, extra);
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

}
