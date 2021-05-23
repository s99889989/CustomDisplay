package com.daxton.customdisplay.task.action2.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.item.CustomItem2;
import com.daxton.customdisplay.api.item.MenuItem2;
import com.daxton.customdisplay.manager.ActionManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Holographic3 {

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Map<String, String> action_Map;
    private LivingEntity self = null;
    private LivingEntity target = null;
    private String taskID;

    private final Map<String, Hologram> hologram_Map = new ConcurrentHashMap<>();
    private final Map<String, Location> location_Map = new ConcurrentHashMap<>();

    public Holographic3(){

    }

    public void setHD(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID) {
        this.taskID = taskID;
        this.self = self;
        this.target = target;
        this.action_Map = action_Map;

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String mark = actionMapHandle.getString(new String[]{"mark","mk"},"0");

        setOther(taskID+mark);


    }

    public void setOther(String livTaskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        String message = actionMapHandle.getString(new String[]{"message","m"}, null);

        int removeMessage = actionMapHandle.getInt(new String[]{"removemessage","rm"}, 0);

        boolean teleport = actionMapHandle.getBoolean(new String[]{"teleport","tp"}, false);

        String itemID = actionMapHandle.getString(new String[]{"itemid","iid"}, null);

        boolean delete = actionMapHandle.getBoolean(new String[]{"delete","d"}, false);

        boolean deleteAll = actionMapHandle.getBoolean(new String[]{"deleteall","dall"}, false);


        Location oldlocation = null;
        if(location_Map.get(livTaskID) != null){
            oldlocation = location_Map.get(livTaskID);
        }
        Location location = actionMapHandle.getLocation(oldlocation);

        if(hologram_Map.get(livTaskID) == null){
            if(location != null){
                location_Map.put(livTaskID, location);
                hologram_Map.put(livTaskID, createHD(location));
            }
        }else {
            Hologram hologram = hologram_Map.get(livTaskID);
            if(location != null){
                if(teleport){
                    teleportHD(hologram , location);
                    location_Map.put(livTaskID, location);
                }
            }
        }

        if(hologram_Map.get(livTaskID) != null){
            Hologram hologram = hologram_Map.get(livTaskID);

            if(itemID != null){
                hologram = addItemHD(hologram, itemID);

            }
            if(removeMessage > 0){
                hologram = removeLineHD(hologram, removeMessage-1);
            }
            if(message != null){
                hologram = addLineHD(hologram, message);
            }

            hologram_Map.put(livTaskID, hologram);

            if(delete){
                deleteHD(livTaskID);
            }
            if(deleteAll){
                deleteAllHD();
            }
        }

    }

    /**建立新的全息**/
    public Hologram createHD(Location location){

        //Hologram hologram = HologramsAPI.createHologram(cd, location);

        return HologramsAPI.createHologram(cd, location);
    }

    /**增加訊息**/
    public Hologram addLineHD(Hologram hologram, String message){
        hologram.appendTextLine(message);
        return hologram;
    }

    /**增加物品訊息**/
    public Hologram addItemHD(Hologram hologram, String itemID){
        ItemStack itemStack = CustomItem2.valueOf(null, null,itemID, 1);
        if(itemStack != null){
            hologram.appendItemLine(itemStack);
        }
        return hologram;
    }

    /**移除訊息**/
    public Hologram removeLineHD(Hologram hologram, int removeMessage){
        if (hologram.size() > 0){
            hologram.removeLine(removeMessage);
        }
        return hologram;
    }

    /**移動全息**/
    public void teleportHD(Hologram hologram, Location location){
        hologram.teleport(location);
    }

    /**刪除全息**/
    public void deleteHD(String livTaskID){
        this.hologram_Map.get(livTaskID).delete();
        this.hologram_Map.remove(livTaskID);
        this.location_Map.remove(livTaskID);

    }

    public void deleteAllHD(){
        if(!this.hologram_Map.isEmpty()){
            this.hologram_Map.forEach((s, hologram1) -> hologram1.delete());

        }
        if(ActionManager.judgment_Holographic_Map2.get(this.taskID) != null){
            ActionManager.judgment_Holographic_Map2.remove(this.taskID);
        }
    }

    public Map<String, Hologram> getHologram_Map() {
        return hologram_Map;
    }


}
