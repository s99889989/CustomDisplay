package com.daxton.customdisplay.api.entity;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.bukkit.Color.fromRGB;

public class EntityFind {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public EntityFind(){

    }



    public static Entity getNearestEntityInSight(Player player, int range) {
        ArrayList<Entity> entities = (ArrayList<Entity>) player.getNearbyEntities(range, range, range);
        ArrayList<Block> sightBlock = (ArrayList<Block>) player.getLineOfSight( (Set<Material>) null, range);
        ArrayList<Location> sight = new ArrayList<Location>();
        for (int i = 0;i<sightBlock.size();i++)
            sight.add(sightBlock.get(i).getLocation());
        for (int i = 0;i<sight.size();i++) {
            for (int k = 0;k<entities.size();k++) {
                if (Math.abs(entities.get(k).getLocation().getX()-sight.get(i).getX())<1.3) {
                    if (Math.abs(entities.get(k).getLocation().getY()-sight.get(i).getY())<1.5) {
                        if (Math.abs(entities.get(k).getLocation().getZ()-sight.get(i).getZ())<1.3) {
                            return entities.get(k);
                        }
                    }
                }
            }
        }
        return null; //Return null/nothing if no entity was found
    }




    /**目標不包括自己**/
    public List<Entity> getNearbyEntities(Entity self,Location l, int radius){
        int chunkRadius = radius < 16 ? 1 : (radius - (radius % 16))/16;
        HashSet<Entity> radiusEntities = new HashSet<Entity>();
        List<Entity> livingEntityList = new ArrayList<>();
        for (int chX = 0 -chunkRadius; chX <= chunkRadius; chX ++){
            for (int chZ = 0 -chunkRadius; chZ <= chunkRadius; chZ++){
                int x=(int) l.getX(),y=(int) l.getY(),z=(int) l.getZ();
                for (Entity e : new Location(l.getWorld(),x+(chX*16),y,z+(chZ*16)).getChunk().getEntities()){
                    if (e.getLocation().distance(l) <= radius && e.getLocation().getBlock() != l.getBlock()) radiusEntities.add(e);
                }
            }
        }
        Entity[] entities = radiusEntities.toArray(new Entity[radiusEntities.size()]);
        for(Entity entity : entities){
            if(entity instanceof LivingEntity & entity != self & !(entity instanceof ArmorStand) ){
                livingEntityList.add(entity);
            }
        }

        return livingEntityList;
    }

    /**判斷玩家方向**/
    public static String getCardinalDirection(LivingEntity livingEntity) {
        double rotation = (livingEntity.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return "北";
        } else if (22.5 <= rotation && rotation < 67.5) {
            return "北東";
        } else if (67.5 <= rotation && rotation < 112.5) {
            return "東";
        } else if (112.5 <= rotation && rotation < 157.5) {
            return "南東";
        } else if (157.5 <= rotation && rotation < 202.5) {
            return "南";
        } else if (202.5 <= rotation && rotation < 247.5) {
            return "南西";
        } else if (247.5 <= rotation && rotation < 292.5) {
            return "西";
        } else if (292.5 <= rotation && rotation < 337.5) {
            return "北西";
        } else if (337.5 <= rotation && rotation < 360.0) {
            return "北";
        } else {
            return null;
        }
    }


    public static String rpGetPlayerDirection(LivingEntity livingEntity){
        String dir = "";
        float y = livingEntity.getLocation().getYaw();
        if( y < 0 ){y += 360;}
        y %= 360;
        int i = (int)((y+8) / 22.5);
        if(i == 0){dir = "西";}
        else if(i == 1){dir = "西 北西";}
        else if(i == 2){dir = "北西";}
        else if(i == 3){dir = "北 北西";}
        else if(i == 4){dir = "北";}
        else if(i == 5){dir = "北 北東";}
        else if(i == 6){dir = "北東";}
        else if(i == 7){dir = "東 北東";}
        else if(i == 8){dir = "東";}
        else if(i == 9){dir = "東 南東";}
        else if(i == 10){dir = "南東";}
        else if(i == 11){dir = "南 南東";}
        else if(i == 12){dir = "南";}
        else if(i == 13){dir = "南 南西";}
        else if(i == 14){dir = "南西";}
        else if(i == 15){dir = "西 南西";}
        else {dir = "西";}
        return dir;
    }


}
