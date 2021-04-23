package com.daxton.customdisplay.task.action2.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.item.CustomItem;
import com.daxton.customdisplay.api.location.DirectionLocation;
import com.daxton.customdisplay.manager.ActionManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Random;

public class Holographic3 {

    private static final Random random = new Random();

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private Map<String, String> action_Map;
    private LivingEntity self = null;
    private LivingEntity target = null;

    private Hologram hologram;
    private Location location;


    public Holographic3(){

    }

    public void setHD(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID) {

        this.self = self;
        this.target = target;
        this.taskID = taskID;
        this.action_Map = action_Map;
        setOther();
    }

    public void setOther(){

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        String message = actionMapHandle.getString(new String[]{"message","m"},null);

        int removeMessage = actionMapHandle.getInt(new String[]{"removemessage","rm"},0);

        boolean teleport = actionMapHandle.getBoolean(new String[]{"teleport","tp"},false);

        String itemID = actionMapHandle.getString(new String[]{"itemid","iid"},null);

        boolean delete = actionMapHandle.getBoolean(new String[]{"delete"},false);

        double x = 0;
        double y = 0;
        double z = 0;
        String[] locAdds = actionMapHandle.getStringList(new String[]{"locadd","la"},new String[]{"0","0","0"},"\\|",3);
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

        /**向量增加座標**/
        String[] directionAdd = actionMapHandle.getStringList(new String[]{"directionadd","da"},new String[]{"self","0","0","0"},"\\|",4);
        String directionT = "self";
        double daX = 0;
        double daY = 0;
        double daZ = 0;
        if(directionAdd.length == 4){
            directionT = directionAdd[0];
            try {
                daX = Double.parseDouble(directionAdd[1]);
                daY = Double.parseDouble(directionAdd[2]);
                daZ = Double.parseDouble(directionAdd[3]);
            }catch (NumberFormatException exception){
                daX = 0;
                daY = 0;
                daZ = 0;
            }
        }

        location = actionMapHandle.getLocation(self, target, location);

        if(daX != 0 || daY != 0 || daZ != 0){
            if(directionT.toLowerCase().contains("target")){
                location = DirectionLocation.getSetDirection(location, location, daX , daY, daZ);
            }else {
                location = DirectionLocation.getSetDirection(location, self.getLocation(), daX , daY, daZ);
            }
        }

        if(x != 0 || y != 0 || z != 0){
            location.add(x,y,z);
        }

        if(this.hologram == null){
            if(location != null){
                createHD(location);
            }
        }
        if(this.hologram != null){
            if(itemID != null){
                addItemHD(self,target, hologram, itemID);
            }
            if(removeMessage > 0){
                removeLineHD(hologram, removeMessage-1);
            }
            if(message != null){
                addLineHD(hologram, message);
            }
            if(teleport){
                if(location != null){
                    teleportHD(this.hologram, location);
                }
            }
            if(delete){
                deleteHD(this.hologram);
            }
        }



    }

    /**建立新的全息**/
    public Hologram createHD(Location location){

        hologram = HologramsAPI.createHologram(cd, location);

        return hologram;
    }

    /**增加訊息**/
    public void addLineHD(Hologram hologram, String message){
        hologram.appendTextLine(message);
    }

    /**增加物品訊息**/
    public void addItemHD(LivingEntity self,LivingEntity target, Hologram hologram, String itemID){
        //ItemStack itemStack = giveItem(self,target, itemID);
        ItemStack itemStack = CustomItem.valueOf(self, target, itemID,1);
        hologram.appendItemLine(itemStack);
    }

    /**移除訊息**/
    public void removeLineHD(Hologram hologram, int removeMessage){
        if (hologram.size() > 0){
            hologram.removeLine(removeMessage);
        }

    }

    /**移動全息**/
    public void teleportHD(Hologram hologram, Location location){
        hologram.teleport(location);
    }

    /**刪除全息**/
    public void deleteHD(Hologram hologram){

        hologram.delete();
        ActionManager.judgment_Holographic_Map2.remove(taskID);
    }

    public Hologram getHologram() {
        return hologram;
    }




}
