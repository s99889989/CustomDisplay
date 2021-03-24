package com.daxton.customdisplay.task.action.list;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Velocity {

    public Velocity(){

    }

    public void setVelocity(LivingEntity self, LivingEntity target, String firstString, String taskID){

        if(self instanceof Player){
            Player player = (Player) self;
            Location locuser = player.getPlayer().getEyeLocation();
            Location loctarget = target.getEyeLocation();
            Vector vec = loctarget.subtract(locuser).toVector().normalize();
            target.setVelocity(vec.multiply(5));
        }


    }

}
