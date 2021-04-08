package com.daxton.customdisplay.task.action.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.item.CustomItem;
import com.daxton.customdisplay.manager.ActionManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

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

        if(function != null && location != null){
            setOther(self, target, function, message, location.add(x, y, z), taskID);
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
        if(ActionManager.judgment_LocHolographic_Map.get(taskID) != null){
            ActionManager.judgment_LocHolographic_Map.remove(taskID);
        }

    }

    public Hologram getHologram() {
        return hologram;
    }
}
