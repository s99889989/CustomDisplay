package com.daxton.customdisplay.task.locationAction;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import com.daxton.customdisplay.manager.LocationActionManager;
import com.daxton.customdisplay.task.action.ClearAction;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import java.util.Random;

public class LocationHolographic {

    private static final Random random = new Random();

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Hologram hologram;


    public LocationHolographic(){

    }

    public void setHD(LivingEntity self, LivingEntity target, String firstString,String taskID,Location location) {
        String message = "";
        String function = null;
        for(String string : new StringFind().getStringMessageList(firstString)){
            if(string.toLowerCase().contains("message=") || string.toLowerCase().contains("m=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    message = new ConversionMain().valueOf(self,target,strings[1]);
                }
            }
        }
        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("function=") || string.toLowerCase().contains("fc=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    function = strings[1];
                }
            }
        }
        if(function != null && location != null){
            setOther(function,message,location,taskID);
        }
    }

    public void setOther(String function,String message,Location location,String taskID){

        if(function.toLowerCase().contains("create") && hologram == null){
            createHD(message,location);
        }
        if(function.toLowerCase().contains("addtextline") && hologram != null){
            addLineHD(message);
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

    public void removeLineHD(String message){
        try{
            hologram.removeLine(Integer.valueOf(message));
        }catch (NumberFormatException exception){
            cd.getLogger().info("移除內容錯誤");
        }
    }

    public void teleportHD(Location location){
        hologram.teleport(location);
    }

    public void deleteHD(String taskID){
        new ClearAction(taskID);
        if(LocationActionManager.location_Holographic_Map.get(taskID) != null){
            LocationActionManager.location_Holographic_Map.remove(taskID);
        }
        hologram.delete();
    }

    public Hologram getHologram() {
        return hologram;
    }
}
