package com.daxton.customdisplay.api.entity;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Line {

    public Line(){

    }

    /**獲得目視生命目標不包括自己**/
    public static List<LivingEntity> getMulti(LivingEntity self, double line, double range) {
        List<LivingEntity> livingEntityList = new ArrayList<>();
        List<Entity> targetEntityList = self.getNearbyEntities(line, line, line);

        if(targetEntityList.size() > 0){

            for(Entity targetEntity : targetEntityList){
                if(targetEntity instanceof LivingEntity){

                    Vector targetVector = ((LivingEntity) targetEntity).getEyeLocation().subtract(self.getEyeLocation()).toVector();
                    double rad = targetVector.angle(self.getEyeLocation().getDirection());

                    if(rad < range){
                        Location st = targetEntity.getLocation().subtract(self.getLocation());
                        double dd = Math.sqrt(Math.pow((st.getX()),2) + Math.pow((st.getY()),2) +Math.pow((st.getZ()),2));
                        if(dd <= line){
                            livingEntityList.add((LivingEntity) targetEntity);
                        }
                    }
                }
            }
        }
        return  livingEntityList;
    }


}
