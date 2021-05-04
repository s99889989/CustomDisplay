package com.daxton.customdisplay.api.entity;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class LookTarget {

    /**獲得目視生命目標不包括自己**/
    public static LivingEntity getLivingTarget(LivingEntity self, double radius) {
        List<Entity> targetEntityList = self.getNearbyEntities(radius, radius, radius);
        LivingEntity target = null;
        if(targetEntityList.size() > 0){
            double min = radius+1;
            for(Entity targetEntity : targetEntityList){
                if(targetEntity instanceof LivingEntity){

                    Vector targetVector = ((LivingEntity) targetEntity).getEyeLocation().subtract(self.getEyeLocation()).toVector();
                    double rad = targetVector.angle(self.getEyeLocation().getDirection());
                    if(rad < 0.2){
                        Location st = targetEntity.getLocation().subtract(self.getLocation());
                        double dd = Math.sqrt(Math.pow((st.getX()),2) + Math.pow((st.getY()),2) +Math.pow((st.getZ()),2));
                        if(dd <= radius && dd < min){
                            min = dd;
                            target = (LivingEntity) targetEntity;
                        }
                    }
                }
            }
        }
        return target;
    }


    /**獲得目視目標不包括自己(網路上找的方法)**/
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

}
