package com.daxton.customdisplay.api.mobs;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PermissionManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

public class MobData {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private UUID uuid;

    /**動作列表**/
    private List<String> playerActionList;

    /**觸發的動作列表**/
    private Map<String,List<String>> action_Trigger_Map = new HashMap<>();

    public MobData(LivingEntity livingEntity){
        this.uuid = livingEntity.getUniqueId();

        setPlayerActionList();
        setActionList();
    }

    /**獲取動作列表**/
    public void setPlayerActionList() {
        playerActionList = ConfigMapManager.getFileConfigurationMap().get("Mobs_Default.yml").getStringList("Action");

    }

    /**觸發的動作列表**/
    public void setActionList(){
        if(playerActionList.size() > 0){
            for(String actionString : playerActionList){
                if(actionString.toLowerCase().contains("~ondamaged")){
                    if(action_Trigger_Map.get("~ondamaged") == null){
                        action_Trigger_Map.put("~ondamaged",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~ondamaged") != null){
                        action_Trigger_Map.get("~ondamaged").add(actionString);
                    }
                }

                if(actionString.toLowerCase().contains("~ondeath")){
                    if(action_Trigger_Map.get("~ondeath") == null){
                        action_Trigger_Map.put("~ondeath",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~ondeath") != null){
                        action_Trigger_Map.get("~ondeath").add(actionString);
                    }
                }

            }

        }
    }


    public Map<String, List<String>> getAction_Trigger_Map() {
        return action_Trigger_Map;
    }
}
