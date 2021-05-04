package com.daxton.customdisplay.task.action2.orbital;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.item.CustomItem;
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

public class LocationHolographic3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Map<String, String> action_Map;
    private LivingEntity self = null;
    private LivingEntity target = null;
    private String taskID;

    private final Map<String, Hologram> hologram_Map = new ConcurrentHashMap<>();


    public LocationHolographic3(){

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



        if(hologram_Map.get(livTaskID) == null && inputLocation != null){
            Location location = inputLocation;
            if(directionT.toLowerCase().contains("target")){
                location = DirectionLocation.getSetDirection(location, this.target.getLocation(), daX , daY, daZ).add(x, y, z);
            }else {
                location = DirectionLocation.getSetDirection(location, this.self.getLocation(), daX , daY, daZ).add(x, y, z);
            }

            hologram_Map.put(livTaskID, createHD(location));
        }
        if(hologram_Map.get(livTaskID) != null){
            Hologram hologram = hologram_Map.get(livTaskID);
            Location location = null;
            if(inputLocation != null){
                location = inputLocation;
                if(directionT.toLowerCase().contains("target")){
                    location = DirectionLocation.getSetDirection(location, this.target.getLocation(), daX , daY, daZ).add(x, y, z);
                }else {
                    location = DirectionLocation.getSetDirection(location, this.self.getLocation(), daX , daY, daZ).add(x, y, z);
                }
            }

            if(itemID != null){
                addItemHD(hologram, itemID);
            }
            if(removeMessage > 0){
                removeLineHD(hologram, removeMessage-1);
            }
            if(message != null){

                addLineHD(hologram, message);
            }
            if(teleport){
                if(location != null){
                    teleportHD(hologram , location);
                }
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
        ItemStack itemStack = MenuItem2.valueOf(itemID);
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
