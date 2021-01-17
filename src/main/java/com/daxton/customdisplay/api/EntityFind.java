package com.daxton.customdisplay.api;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EntityFind {

    public EntityFind(){

    }

    public static Player convertPlayer(Entity entity){
        Player player = null;
        if(entity instanceof Player){
            player = ((Player) entity).getPlayer();
            return player;
        }
        if(entity instanceof Arrow){
            if(((Arrow) entity).getShooter() instanceof Player){
                player = (Player) ((Arrow) entity).getShooter();
                return player;
            }
        }
        if(entity instanceof ThrownPotion){
            if(((ThrownPotion) entity).getShooter() instanceof Player){
                player = (Player) ((ThrownPotion) entity).getShooter();
                return player;
            }
        }
        if(entity instanceof TNTPrimed){
            if(((TNTPrimed) entity).getSource() instanceof Player){
                player = (Player) ((TNTPrimed) entity).getSource();
                return player;
            }
        }
        if(entity instanceof Projectile){
            if(((Projectile) entity).getShooter() instanceof Player){
                player = (Player) ((Projectile) entity).getShooter();
                return player;
            }
        }

        return player;
    }

    public static Player crackShotPlayer(Entity entity){
        Player player = null;
        if(entity instanceof Player){
            player = ((Player) entity).getPlayer();
            return player;
        }
//        if(entity instanceof Arrow){
//            if(((Arrow) entity).getShooter() instanceof Player){
//                player = (Player) ((Arrow) entity).getShooter();
//                return player;
//            }
//        }
//        if(entity instanceof ThrownPotion){
//            if(((ThrownPotion) entity).getShooter() instanceof Player){
//                player = (Player) ((ThrownPotion) entity).getShooter();
//                return player;
//            }
//        }
//        if(entity instanceof Projectile){
//            if(((Projectile) entity).getShooter() instanceof Player){
//                player = (Player) ((Projectile) entity).getShooter();
//                return player;
//            }
//        }

        return player;
    }

    /**目標不包括自己**/
    public Entity getTarget(LivingEntity self,int radius) {
        //List<Entity> targetList = getNearbyEntities(self,self.getLocation(), radius);
        List<Entity> targetList = self.getNearbyEntities(10, 10, 10);
        ArrayList<Entity> nearPlayers = new ArrayList<>();
        if(!(targetList.isEmpty())){
            for (Entity livingEntity : targetList) {
                if(livingEntity instanceof LivingEntity){
                    nearPlayers.add(livingEntity);
                }
            }
        }

        Entity target = null;
        BlockIterator bItr = new BlockIterator(self, radius);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
        while (bItr.hasNext()) {
            block = bItr.next();
            bx = block.getX();
            by = block.getY();
            bz = block.getZ();
            if(!(nearPlayers.isEmpty())){
                for (Entity e : nearPlayers) {
                    loc = e.getLocation();
                    ex = loc.getX();
                    ey = loc.getY();
                    ez = loc.getZ();
                    if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75) && (by - 1 <= ey && ey <= by + 2.5)) {
                        target = e;
                        break;
                    }
                }
            }
        }
        return target;
    }

    /**目標不包括自己**/
    public List<Entity>  getNearbyEntities(Entity self,Location l, int radius){
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
