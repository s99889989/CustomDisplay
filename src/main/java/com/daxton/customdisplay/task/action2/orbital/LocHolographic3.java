package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.item.CustomItem;
import com.daxton.customdisplay.api.item.CustomItem2;
import com.daxton.customdisplay.api.item.MenuItem2;
import com.daxton.customdisplay.api.location.DirectionLocation;
import com.daxton.customdisplay.manager.ActionManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocHolographic3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Map<String, String> action_Map;
    private LivingEntity self = null;
    private LivingEntity target = null;
    private String taskID;

    private final Map<String, Hologram> hologram_Map = new ConcurrentHashMap<>();
    private final Map<String, Location> location_Map = new ConcurrentHashMap<>();

    public LocHolographic3(){

    }

    public void setHD(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID, Location location) {

        this.taskID = taskID;
        this.self = self;
        this.target = target;
        this.action_Map = action_Map;
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String mark = actionMapHandle.getString(new String[]{"mark","mk"},"0");

        setOther(taskID+mark, location);
    }

    public void setOther(String livTaskID, Location inputLocation){

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        String message = actionMapHandle.getString(new String[]{"message","m"}, null);

        int removeMessage = actionMapHandle.getInt(new String[]{"removemessage","rm"}, 0);

        boolean teleport = actionMapHandle.getBoolean(new String[]{"teleport","tp"}, false);

        String itemID = actionMapHandle.getString(new String[]{"itemid","iid"}, null);

        boolean delete = actionMapHandle.getBoolean(new String[]{"delete"}, false);

        boolean deleteAll = actionMapHandle.getBoolean(new String[]{"deleteall"}, false);



        Location location = actionMapHandle.getLocation2(inputLocation);
        //cd.getLogger().info(inputLocation.getY()+" : "+location.getY());
        if(hologram_Map.get(livTaskID) == null && inputLocation != null){

            hologram_Map.put(livTaskID, createHD(location));
            //location_Map.put(livTaskID, location);
        }else {
            Hologram hologram = hologram_Map.get(livTaskID);
            if(teleport){
                if(location != null){
                    teleportHD(hologram , location);
                }
            }
        }
        if(hologram_Map.get(livTaskID) != null){
            Hologram hologram = hologram_Map.get(livTaskID);
            //location_Map.get(livTaskID);


            if(itemID != null){
                addItemHD(hologram, itemID);
            }
            if(removeMessage > 0){
                removeLineHD(hologram, removeMessage-1);
            }
            if(message != null){

                addLineHD(hologram, message);
            }

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

        Hologram hologram = HologramsAPI.createHologram(cd, location);

        return hologram;
    }

    /**增加訊息**/
    public void addLineHD(Hologram hologram, String message){
        hologram.appendTextLine(message);
    }

    /**增加物品訊息**/
    public void addItemHD(Hologram hologram, String itemID){
        //ItemStack itemStack = giveItem(self,target, itemID);
        ItemStack itemStack = CustomItem2.valueOf(null, null, itemID, 1);
        if(itemStack != null){
            hologram.appendItemLine(itemStack);
        }
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
    public void deleteHD(String livTaskID){
        this.hologram_Map.get(livTaskID).delete();
        this.hologram_Map.remove(livTaskID);

    }

    public void deleteAllHD(){
        if(!this.hologram_Map.isEmpty()){
            this.hologram_Map.forEach((s, hologram1) -> {
                hologram1.delete();
            });

        }
        if(ActionManager.judgment_Holographic_Map2.get(this.taskID) != null){
            ActionManager.judgment_Holographic_Map2.remove(this.taskID);
        }
    }

    public Map<String, Hologram> getHologram_Map() {
        return hologram_Map;
    }
}
