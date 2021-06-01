package com.daxton.customdisplay.task.action.entity;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.location.DirectionLocation;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;

public class Move3 {

    public Move3(){

    }

    public static void setVelocity(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        double awayHight = actionMapHandle.getDouble(new String[]{"awayhight","ah"},1);


        //向量增加座標
        String[] directionAdd = actionMapHandle.getStringList(new String[]{"directionadd","da"},new String[]{"self","true","true","0","0","0"},"\\|",6);
        String directionT = "self";
        boolean pt = true;
        boolean yw = true;
        double daX = 0;
        double daY = 0;
        double daZ = 0;
        if(directionAdd.length == 6){
            directionT = directionAdd[0];
            pt = Boolean.parseBoolean(directionAdd[1]);
            yw = Boolean.parseBoolean(directionAdd[2]);
            try {
                daX = Double.parseDouble(directionAdd[3]);
                daY = Double.parseDouble(directionAdd[4]);
                daZ = Double.parseDouble(directionAdd[5]);
            }catch (NumberFormatException exception){
                daX = 0;
                daY = 0;
                daZ = 0;
            }
        }


        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        if(!(livingEntityList.isEmpty())){
            for(LivingEntity livingEntity : livingEntityList){

                Vector vector = null;

                switch (directionT.toLowerCase()){
                    case "target":
                        vector = DirectionLocation.getDirection(livingEntity.getLocation(), pt, yw, daX , daY, daZ);
                        break;
                    case "self":
                        vector = DirectionLocation.getDirection(self.getLocation(), pt, yw, daX , daY, daZ);
                        break;
                    case "selfaway":
                        Location locuser = self.getEyeLocation();
                        Location loctarget = livingEntity.getEyeLocation();
                        Vector vec = loctarget.subtract(locuser).toVector().normalize().multiply(daZ);
                        Vector vector1 = new Vector(0,awayHight,0);
                        vector = vec.add(vector1);

                        break;
                    case "targetaway":
                        Location locuser1 = livingEntity.getEyeLocation();
                        Location loctarget1 = self.getEyeLocation();
                        Vector vec1 = loctarget1.subtract(locuser1).toVector().normalize().multiply(daZ);
                        Vector vector2 = new Vector(0,awayHight,0);
                        vector = vec1.add(vector2);
                        break;
                }

                if(vector != null){
                    livingEntity.setVelocity(vector);
                }




            }

        }


    }



}
