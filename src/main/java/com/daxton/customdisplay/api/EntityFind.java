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
        List<Entity> targetList = getNearbyEntities(self,self.getLocation(), radius);
        //List<Entity> targetList = self.getNearbyEntities(10, 10, 10);
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


}
