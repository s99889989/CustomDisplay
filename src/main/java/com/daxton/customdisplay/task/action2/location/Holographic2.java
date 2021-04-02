package com.daxton.customdisplay.task.action2.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.item.CustomItem;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action.ClearAction;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Holographic2 {

    private static final Random random = new Random();

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private CustomLineConfig customLineConfig;
    private LivingEntity self = null;
    private LivingEntity target = null;

    private Hologram hologram;
    private Location location = new Location(Bukkit.getWorld("world"),0,0,0);


    public Holographic2(){

    }

    public void setHD(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID) {

        this.self = self;
        this.target = target;
        this.taskID = taskID;
        this.customLineConfig = customLineConfig;
        setOther();
    }

    public void setOther(){

        String message = customLineConfig.getString(new String[]{"message","m"},"", self, target);

        String function = customLineConfig.getString(new String[]{"function","fc"},"", self, target);

        double x = 0;
        double y = 0;
        double z = 0;
        String[] locAdds = customLineConfig.getStringList(new String[]{"locadd","la"},new String[]{"0","0","0"},"\\|",3, self, target);
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

        String locTarget = customLineConfig.getString(new String[]{"loctarget","lt"},"", self, target);
        if(locTarget.toLowerCase().contains("self")){
            location = self.getLocation().add(x,y,z);
        }else if(locTarget.toLowerCase().contains("target")){
            if(target != null){
                location = target.getLocation().add(x,y,z);
            }
        }else if(locTarget.toLowerCase().contains("world")){
            location = new Location(self.getWorld(),x,y,z);
        }else {
            location = location.add(x,y,z);
        }



        if(function.toLowerCase().contains("create") && hologram == null){
            createHD(location, message);
        }else if(function.toLowerCase().contains("additemline") && hologram != null){
            addItemHD(self,target, hologram, message);
        }else if(function.toLowerCase().contains("addtextline") && hologram != null){
            addLineHD(hologram, message);
        }else if(function.toLowerCase().contains("removetextline") && hologram != null){
            removeLineHD(hologram, message);
        }else if(function.toLowerCase().contains("teleport") && hologram != null){
            teleportHD(hologram, location);
        }else if(function.toLowerCase().contains("delete") && hologram != null){
            deleteHD(hologram);
        }


//        if(function.toLowerCase().contains("create")){
//            if(!(targetList.isEmpty())){
//                for (LivingEntity livingEntity : targetList){
//
//                    String uuidString = livingEntity.getUniqueId().toString();
//                    if(hologramMap.get(taskID+uuidString) == null){
//                        String message2 = new SetValue(self,livingEntity,firstString,"[];","","message=","m=").getString();
//                        locationMap.put(taskID+uuidString, livingEntity.getLocation().add(x,y,z));
//                        livingEntityMap.put(taskID+uuidString, livingEntity);
//                        hologramMap.put(taskID+uuidString, createHD(livingEntity.getLocation().add(x,y,z), message2));
//                    }
//                }
//            }
//        }
//        if(function.toLowerCase().contains("addtextline")){
//            hologramMap.forEach((s, hologram1) -> {
//                LivingEntity livingEntity = livingEntityMap.get(s);
//                String message2 = new SetValue(self,livingEntity,firstString,"[];","","message=","m=").getString();
//                addLineHD(hologram1,message2);
//            });
//        }
//        if(function.toLowerCase().contains("additemline")){
//            hologramMap.forEach((s, hologram1) -> {
//                LivingEntity livingEntity = livingEntityMap.get(s);
//                String message2 = new SetValue(self,livingEntity,firstString,"[];","","message=","m=").getString();
//                addItemHD(self, livingEntity, hologram1, message2);
//            });
//        }
//        if(function.toLowerCase().contains("removetextline")){
//            hologramMap.forEach((s, hologram1) -> {
//                LivingEntity livingEntity = livingEntityMap.get(s);
//                String message2 = new SetValue(self,livingEntity,firstString,"[];","","message=","m=").getString();
//                removeLineHD(hologram1,message2);
//            });
//        }
//        if(function.toLowerCase().contains("teleport")){
//            double xx = x;
//            double yy = y;
//            double zz = z;
//
//            hologramMap.forEach((s, hologram1) -> {
//
//                Location location = hologram1.getLocation().add(xx,yy,zz);
//
//                if(!(targetList.isEmpty())){
//
//                    for(LivingEntity livingEntity : targetList){
//                        String uuidString = livingEntity.getUniqueId().toString();
//                        if(livingEntityMap.get(taskID+uuidString) == livingEntityMap.get(s)){
//                            location = livingEntity.getLocation().add(xx,yy,zz);
//                        }
//                    }
//                }
//
//
//                teleportHD(hologram1,location);
//
//            });
//        }
//        if(function.toLowerCase().contains("delete")){
//            hologramMap.forEach((s, hologram1) -> {
//                deleteHD(hologram1);
//            });
//            new ClearAction(taskID);
//            if(ActionManager.getJudgment2_Holographic2_Map().get(taskID) != null){
//                ActionManager.getJudgment2_Holographic2_Map().remove(taskID);
//            }
//        }


    }


    public Hologram createHD(Location location, String message){

        hologram = HologramsAPI.createHologram(cd, location);
        hologram.appendTextLine(message);
        return hologram;
    }

    public void addLineHD(Hologram hologram, String message){
        hologram.appendTextLine(message);
    }

    public void addItemHD(LivingEntity self,LivingEntity target, Hologram hologram, String itemID){
        //ItemStack itemStack = giveItem(self,target, itemID);
        ItemStack itemStack = CustomItem.valueOf(self, target, itemID,1);
        hologram.appendItemLine(itemStack);
    }

    public void removeLineHD(Hologram hologram, String message){
        try{
            hologram.removeLine(Integer.valueOf(message));
        }catch (NumberFormatException exception){
            hologram.removeLine(0);
        }
    }

    public void teleportHD(Hologram hologram, Location location){
        hologram.teleport(location);
    }

    public void deleteHD(Hologram hologram){
        hologram.delete();
    }

    public Hologram getHologram() {
        return hologram;
    }




}
