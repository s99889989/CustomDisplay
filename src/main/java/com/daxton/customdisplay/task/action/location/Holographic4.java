package com.daxton.customdisplay.task.action.location;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.item.CustomItem2;
import com.daxton.customdisplay.manager.ActionManager;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Holographic4 {

    public Holographic4(){

    }

    public static void setHD(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID, Location inputLocation) {

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);
        //標記名稱
        String mark = actionMapHandle.getString(new String[]{"mark","mk"},"0");
        //顯示訊息
        String message = actionMapHandle.getString(new String[]{"message","m"}, null);
        //移除顯示訊息
        int removeMessage = actionMapHandle.getInt(new String[]{"removemessage","rm"}, 0);
        //是否要移動
        boolean teleport = actionMapHandle.getBoolean(new String[]{"teleport","tp"}, false);
        //顯示物品的ID
        String itemID = actionMapHandle.getString(new String[]{"itemid","iid"}, null);
        //是否刪除
        boolean delete = actionMapHandle.getBoolean(new String[]{"delete","deleteall","d"}, false);
        //改變指定行數內容
        int changeMessage = actionMapHandle.getInt(new String[]{"cm","changeMessage"}, 0);

        if(ActionManager.hologram_Map.get(taskID+mark) == null){
            Location location;
            if(inputLocation != null){
                location = actionMapHandle.getLocation(inputLocation);
            }else {
                location = actionMapHandle.getLocation(null);
            }
            if(location != null){
                Hologram hologram = createHD(location);
                ActionManager.hologram_Map.put(taskID+mark, hologram);
            }
        }else {
            Hologram hologram = ActionManager.hologram_Map.get(taskID+mark);
            Location location;

            if(inputLocation != null){
                location = actionMapHandle.getLocation(inputLocation);
            }else {
                Location oldlocation = hologram.getLocation();
                location = actionMapHandle.getLocation(oldlocation);
            }

            if(location != null){
                if(teleport){
                    teleportHD(hologram , location);
                }
            }

        }

        if(ActionManager.hologram_Map.get(taskID+mark) != null){
            Hologram hologram = ActionManager.hologram_Map.get(taskID+mark);

            if(removeMessage > 0){
                removeLineHD(hologram, removeMessage);
            }

            if(changeMessage > 0 && hologram.size() >= changeMessage && message != null){

                String ll =  hologram.getLine(changeMessage-1).toString();
                String[] aa = ll.split("=");
                ll = aa[1].replaceAll("]","");
                if(!ll.equals(message)){
                    hologram.removeLine(changeMessage-1);
                    hologram.insertTextLine(changeMessage-1, message);
                }

            }else {
                if(message != null){
                    //hologram.insertTextLine(0, message);
                    hologram.appendTextLine(message);
                }
            }

            if(itemID != null){
                addItemHD(hologram, itemID);
            }

            if(delete){
                deleteHD(hologram, taskID+mark);
                return;
            }

            ActionManager.hologram_Map.put(taskID+mark, hologram);
        }

    }


    //建立新的全息
    public static Hologram createHD(Location location){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        return HologramsAPI.createHologram(cd, location);
    }

    //增加物品訊息
    public static void addItemHD(Hologram hologram, String itemID){
        ItemStack itemStack = CustomItem2.valueOf(null, null,itemID, 1);
        if(itemStack != null){
            hologram.appendItemLine(itemStack);
        }
    }

    //移除訊息
    public static void removeLineHD(Hologram hologram, int removeMessage){
        if (hologram.size() >= removeMessage){
            hologram.removeLine(removeMessage-1);
        }
    }

    //移動全息
    public static void teleportHD(Hologram hologram, Location location){
        hologram.teleport(location);
    }

    //刪除全息
    public static void deleteHD(Hologram hologram, String taskID){
        hologram.delete();
        ActionManager.hologram_Map.remove(taskID);
    }

}
