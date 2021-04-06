package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.item.CustomItem;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.manager.LocationActionManager;
import com.daxton.customdisplay.task.action.ClearAction;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LocationHolographic2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Hologram hologram;

    public LocationHolographic2(){

    }

    public void setHD(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID, Location location) {
        if(location == null){
            return;
        }
        String message = customLineConfig.getString(new String[]{"message","m"},"", self, target);

        String function = customLineConfig.getString(new String[]{"function","fc"},"", self, target);

        if(function != null && location != null){
            setOther(self, target, function, message, location, taskID);
        }
    }

    public void setOther(LivingEntity self, LivingEntity target, String function,String message,Location location,String taskID){

        if(function.toLowerCase().contains("create") && hologram == null){
            createHD(message,location);
        }
        if(function.toLowerCase().contains("addtextline") && hologram != null){
            addLineHD(message);
        }
        if(function.toLowerCase().contains("additemline") && hologram != null){
            addItemHD(self, target, message);
        }
        if(function.toLowerCase().contains("removetextline") && hologram != null){
            removeLineHD(message);
        }
        if(function.toLowerCase().contains("teleport") && hologram != null){
            teleportHD(location);
        }
        if(function.toLowerCase().contains("delete") && hologram != null){
            deleteHD(taskID);
        }

    }


    public void createHD(String message,Location location){
        hologram = HologramsAPI.createHologram(cd, location);
        hologram.appendTextLine(message);
    }

    public void addLineHD(String message){
        hologram.appendTextLine(message);
    }

    public void addItemHD(LivingEntity self,LivingEntity target, String itemID){
        ItemStack itemStack = CustomItem.valueOf(self, target, itemID,1);
        hologram.appendItemLine(itemStack);
    }

    public void removeLineHD(String message){
        try{
            hologram.removeLine(Integer.valueOf(message));
        }catch (NumberFormatException exception){
            hologram.removeLine(0);
        }
    }

    public void teleportHD(Location location){
        hologram.teleport(location);
    }

    public void deleteHD(String taskID){
        hologram.delete();
        if(ActionManager2.judgment_LocHolographic_Map.get(taskID) != null){
            ActionManager2.judgment_LocHolographic_Map.remove(taskID);
        }

    }

    public Hologram getHologram() {
        return hologram;
    }
}
