package com.daxton.customdisplay.api.location;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Random;

public class DirectionLocation {

    public DirectionLocation(){

    }

    public Location getLook(LivingEntity self, int distance){

        Location location = self.getLocation();
        Vector vector = location.getDirection().multiply(distance);
        location.add(vector);

        return location;
    }

    /**給向量移動**/
    public static Location getSetDirection(Location inputLocation, Location dirLocation, double hight, double angle, double distance){

        Location location = inputLocation;

        double pitch = ((dirLocation.getPitch() + 90 + (hight*-1)) * Math.PI) / 180;
        double yaw  = ((dirLocation.getYaw() + 90 + angle)  * Math.PI) / 180;


        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.cos(pitch);
        double z = Math.sin(pitch) * Math.sin(yaw);

        Vector vector = new Vector(x, y, z).multiply(distance);
        location.add(vector);


        return location;
    }



    public static Location getDirectionLoction(Location inputLocation, Location dirLocation, boolean pt, boolean yw, double hight, double angle, double distance){

        Location location = new Location(inputLocation.getWorld(),inputLocation.getX(),inputLocation.getY(),inputLocation.getZ());

        double pitch;
        if(pt){
            pitch = ((dirLocation.getPitch() + 90 + (hight*-1)) * Math.PI) / 180;
        }else {
            pitch = ((90 + (hight*-1)) * Math.PI) / 180;
        }
        double yaw;
        if(yw){
            yaw  = ((dirLocation.getYaw() + 90 + angle)  * Math.PI) / 180;
        }else {
            yaw  = (90 + (angle)  * Math.PI) / 180;
        }


        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.cos(pitch);
        double z = Math.sin(pitch) * Math.sin(yaw);

        Vector vector = new Vector(x, y, z).multiply(distance);
        location.add(vector);
        location.setDirection(vector);

        return location;
    }

    public static Vector getDirection(Location dirLocation, boolean pt, boolean yw, double hight, double angle, double distance){

        double pitch;
        if(pt){
            pitch = ((dirLocation.getPitch() + 90 + (hight*-1)) * Math.PI) / 180;
        }else {
            pitch = ((90 + (hight*-1)) * Math.PI) / 180;
        }
        double yaw;
        if(yw){
            yaw  = ((dirLocation.getYaw() + 90 + angle)  * Math.PI) / 180;
        }else {
            yaw  = (90 + (angle)  * Math.PI) / 180;
        }


        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.cos(pitch);
        double z = Math.sin(pitch) * Math.sin(yaw);

        Vector vector = new Vector(x, y, z).multiply(distance);


        return vector;
    }

    //偏轉
    public static Vector getDirection2(Location dirLocation, boolean pt, boolean yw, boolean sign, double hight, double angle, double distance){
        Random random = new Random();
        if(sign){
            angle *= (double)(random.nextBoolean() ? 1 : -1);
        }
        double pitch;
        if(pt){
            pitch = ((dirLocation.getPitch() + 90 + (hight*-1)) * Math.PI) / 180;
        }else {
            pitch = ((90 + (hight*-1)) * Math.PI) / 180;
        }
        double yaw;
        if(yw){
            yaw  = ((dirLocation.getYaw() + 90 + angle)  * Math.PI) / 180;
        }else {
            yaw  = (90 + (angle)  * Math.PI) / 180;
        }

        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.cos(pitch);
        double z = Math.sin(pitch) * Math.sin(yaw);

        Vector vector = new Vector(x, y, z).multiply(distance);


        return vector;
    }

}
