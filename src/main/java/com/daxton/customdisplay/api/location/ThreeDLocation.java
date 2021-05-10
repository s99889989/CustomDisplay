package com.daxton.customdisplay.api.location;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

public class ThreeDLocation {

    public ThreeDLocation(){

    }

    public static Location getPng(Location inputLocation, double addX, double addY, double addZ){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        Quaternion quaternion = new Quaternion(0 , new Double(addX).floatValue(), new Double(addY).floatValue(), new Double(addZ).floatValue());
        EulerAngles eulerAngles = quaternion.ToEulerAngles();
        cd.getLogger().info(eulerAngles.pitch+" : "+eulerAngles.yaw+" : "+eulerAngles.roll);

        return inputLocation;
    }

    public Location getPng2(Location inputLocation){
        double x = inputLocation.getX();
        double y = inputLocation.getY();
        double z = inputLocation.getZ();

        double w = Math.cos(0);
        double outX = (1-2*( Math.pow(y, 2) + Math.pow(z, 2)) ) + (2 * ( x*y - w*z )) + (2 * (w*y + x*z));
        double outY = (2*(x*y + w*z)) + (1-2*(Math.pow(x, 2) + Math.pow(z, 2))) + ( 2 * (y*z - w*x) );
        double outZ = (2 * (x*z - w*y)) + (2 * (y*z + w*x)) + (1 - 2 * (Math.pow(x, 2) + Math.pow(y, 2)));

        return inputLocation.set(outX, outY, outZ);
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

    public static double aDoubleAngel(double angle) {
        return (angle %= 360) >= 0 ? angle : (angle + 360);
    }

    public static Location getPngLocation(Location inputLocation, LivingEntity livingEntity, boolean pt, boolean yw, double addX, double addY, double addZ, double addWidth, double addHeight){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        Location useLocation = inputLocation.clone();


        double rotX = 0;
        double rotY = 0;
        double rotZ = 0;


        if(livingEntity != null){
            if(pt){
                rotX = livingEntity.getEyeLocation().getPitch()  + addX;
                //cd.getLogger().info("仰角: "+rotX);
            }else {
                rotX = addX;
            }
            if(yw){
                rotY = (livingEntity.getEyeLocation().getYaw() * -1) + addY;

            }else {
                rotY = addY;
            }
            rotZ = addZ;
        }else {
            rotX = addX;
            rotY = addY;
            rotZ = addZ;
        }

        rotX = aDoubleAngel(rotX);
        rotY = aDoubleAngel(rotY);
        rotZ = aDoubleAngel(rotZ);

        //cd.getLogger().info("三角: "+ rotX+ " : "+rotY+" : "+rotZ);

        //繞Y軸旋轉
        double cosY = Math.cos(Math.toRadians(rotY));
        double sinY = Math.sin(Math.toRadians(rotY));
        //繞X軸旋轉
        double cosX = Math.cos(Math.toRadians(rotX));
        double sinX = Math.sin(Math.toRadians(rotX));
        //繞Z軸旋轉
        double cosZ = Math.cos(Math.toRadians(rotZ));
        double sinZ = Math.sin(Math.toRadians(rotZ));

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

        return useLocation;
    }


    public static double[] getCosSin(LivingEntity livingEntity, boolean pt, boolean yw, double addX, double addY, double addZ){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        double[] outputDouble = new double[6];

        double rotX = 0;
        double rotY = 0;
        double rotZ = 0;


        if(livingEntity != null){
            if(pt){
                rotX = livingEntity.getEyeLocation().getPitch()  + addX;
                //cd.getLogger().info("仰角: "+rotX);
            }else {
                rotX = addX;
            }
            if(yw){
                rotY = (livingEntity.getEyeLocation().getYaw() * -1) + addY;

            }else {
                rotY = addY;
            }
            rotZ = addZ;
        }else {
            rotX = addX;
            rotY = addY;
            rotZ = addZ;
        }

        rotX = aDoubleAngel(rotX);
        rotY = aDoubleAngel(rotY);
        rotZ = aDoubleAngel(rotZ);

        //cd.getLogger().info("三角: "+ rotX+ " : "+rotY+" : "+rotZ);

        //繞X軸旋轉
        double cosX = Math.cos(Math.toRadians(rotX));
        double sinX = Math.sin(Math.toRadians(rotX));
        //繞Y軸旋轉
        double cosY = Math.cos(Math.toRadians(rotY));
        double sinY = Math.sin(Math.toRadians(rotY));
        //繞Z軸旋轉
        double cosZ = Math.cos(Math.toRadians(rotZ));
        double sinZ = Math.sin(Math.toRadians(rotZ));


        outputDouble[0] = cosX;
        outputDouble[1] = sinX;
        outputDouble[2] = cosY;
        outputDouble[3] = sinY;
        outputDouble[4] = cosZ;
        outputDouble[5] = sinZ;

        //cd.getLogger().info(outputDouble[0]+ " : "+outputDouble[1]+" : "+outputDouble[2]+" : "+outputDouble[3]+" : "+outputDouble[4]+" : "+outputDouble[5]);

        return outputDouble;
    }



    public static Location getPngLocationX(Location inputLocation, Location originLocation, double[] inputDoubel){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        Location useLocation = originLocation.clone();

        //繞X軸旋轉
        double cosX = inputDoubel[0];
        double sinX = inputDoubel[1];
        //繞Y軸旋轉
        double cosY = 1;
        double sinY = 0;
        //繞Z軸旋轉
        double cosZ = 1;
        double sinZ = 0;

        double nX = inputLocation.getX()- originLocation.getX();
        double nY = inputLocation.getY()- originLocation.getY();
        double nZ = inputLocation.getZ()- originLocation.getZ();

        double r11 = cosY*cosZ - sinX*sinY*sinZ;
        double r13 = sinY*cosZ + sinX*cosY*sinZ;
        double r21 = cosY*sinZ + sinX*sinY*cosZ;
        double r23 = sinY*sinZ - sinX*cosY*cosZ;

        double rX = r11*nX - cosX*sinZ*nY + r13*nZ;
        double rY = r21*nX + cosX*cosZ*nY + r23*nZ;
        double rZ = -cosX*sinY*nX + sinX*nY + cosX*cosY*nZ;

        useLocation.add(rX,rY,rZ);

        return useLocation;
    }

    public static Location getPngLocationY(Location inputLocation, Location originLocation, double[] inputDoubel){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        Location useLocation = originLocation.clone();

        //繞X軸旋轉
        double cosX = 1;
        double sinX = 0;
        //繞Y軸旋轉
        double cosY = inputDoubel[2];
        double sinY = inputDoubel[3];
        //繞Z軸旋轉
        double cosZ = 1;
        double sinZ = 0;

        double nX = inputLocation.getX()- originLocation.getX();
        double nY = inputLocation.getY()- originLocation.getY();
        double nZ = inputLocation.getZ()- originLocation.getZ();

        double r11 = cosY*cosZ - sinX*sinY*sinZ;
        double r13 = sinY*cosZ + sinX*cosY*sinZ;
        double r21 = cosY*sinZ + sinX*sinY*cosZ;
        double r23 = sinY*sinZ - sinX*cosY*cosZ;

        double rX = r11*nX - cosX*sinZ*nY + r13*nZ;
        double rY = r21*nX + cosX*cosZ*nY + r23*nZ;
        double rZ = -cosX*sinY*nX + sinX*nY + cosX*cosY*nZ;

        useLocation.add(rX,rY,rZ);

        return useLocation;
    }

    public static Location getPngLocationZ(Location inputLocation, Location originLocation, double[] inputDoubel){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        Location useLocation = originLocation.clone();

        //繞X軸旋轉
        double cosX = 1;
        double sinX = 0;
        //繞Y軸旋轉
        double cosY = 1;
        double sinY = 0;
        //繞Z軸旋轉
        double cosZ = inputDoubel[4];
        double sinZ = inputDoubel[5];

        double nX = inputLocation.getX()- originLocation.getX();
        double nY = inputLocation.getY()- originLocation.getY();
        double nZ = inputLocation.getZ()- originLocation.getZ();

        double r11 = cosY*cosZ - sinX*sinY*sinZ;
        double r13 = sinY*cosZ + sinX*cosY*sinZ;
        double r21 = cosY*sinZ + sinX*sinY*cosZ;
        double r23 = sinY*sinZ - sinX*cosY*cosZ;

        double rX = r11*nX - cosX*sinZ*nY + r13*nZ;
        double rY = r21*nX + cosX*cosZ*nY + r23*nZ;
        double rZ = -cosX*sinY*nX + sinX*nY + cosX*cosY*nZ;

        useLocation.add(rX,rY,rZ);

        return useLocation;
    }



}
