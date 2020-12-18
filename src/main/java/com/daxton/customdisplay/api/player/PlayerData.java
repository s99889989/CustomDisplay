package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PermissionManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerData {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private FileConfiguration fileConfiguration;

    /**動作列表**/
    private List<String> playerActionList;

    /**觸發的動作列表**/
    private Map<String,List<String>> action_Trigger_Map = new HashMap<>();

    public PlayerData(Player player){
        this.player = player;
        setPlayerActionList();
        setActionList();
    }
    /**獲取動作列表**/
    public void setPlayerActionList() {
        for(String configName : ConfigMapManager.getFileConfigurationNameMap().values()){

            if(configName.contains("Players_"+player.getName()+".yml")){
                fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Players_"+player.getName()+".yml");
                break;
            }else{
                fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Players_Default.yml");
            }
        }
        playerActionList = fileConfiguration.getStringList("Action");

        for(String stringConfig : PermissionManager.getPermission_String_Map().values()){

            if(player.hasPermission(stringConfig)){
                for(String list : PermissionManager.getPermission_FileConfiguration_Map().get(stringConfig).getStringList("Action")){
                    playerActionList.add(list);
                }
            }

        }

    }

    public void setActionList(){
        if(playerActionList.size() > 0){
            for(String actionString : playerActionList){
                if(actionString.toLowerCase().contains("~onattack")){
                    if(action_Trigger_Map.get("~onattack") == null){
                        action_Trigger_Map.put("~onattack",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onattack") != null){
                        action_Trigger_Map.get("~onattack").add(actionString);
                    }
                }
                if(actionString.toLowerCase().contains("~oncrit")){
                    if(action_Trigger_Map.get("~oncrit") == null){
                        action_Trigger_Map.put("~oncrit",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~oncrit") != null){
                        action_Trigger_Map.get("~oncrit").add(actionString);
                    }
                }
                if(actionString.toLowerCase().contains("~onmagic")){
                    if(action_Trigger_Map.get("~onmagic") == null){
                        action_Trigger_Map.put("~onmagic",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onmagic") != null){
                        action_Trigger_Map.get("~onmagic").add(actionString);
                    }
                }

                if(actionString.toLowerCase().contains("~onmcrit")){
                    if(action_Trigger_Map.get("~onmcrit") == null){
                        action_Trigger_Map.put("~onmcrit",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onmcrit") != null){
                        action_Trigger_Map.get("~onmcrit").add(actionString);
                    }
                }
                if(actionString.toLowerCase().contains("~ondamaged")){
                    if(action_Trigger_Map.get("~ondamaged") == null){
                        action_Trigger_Map.put("~ondamaged",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~ondamaged") != null){
                        action_Trigger_Map.get("~ondamaged").add(actionString);
                    }
                }
                if(actionString.toLowerCase().contains("~onregainhealth")){
                    if(action_Trigger_Map.get("~onregainhealth") == null){
                        action_Trigger_Map.put("~onregainhealth",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onregainhealth") != null){
                        action_Trigger_Map.get("~onregainhealth").add(actionString);
                    }
                }
                if(actionString.toLowerCase().contains("~onjoin")){
                    if(action_Trigger_Map.get("~onjoin") == null){
                        action_Trigger_Map.put("~onjoin",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onjoin") != null){
                        action_Trigger_Map.get("~onjoin").add(actionString);
                    }
                }
                if(actionString.toLowerCase().contains("~onquit")){
                    if(action_Trigger_Map.get("~onquit") == null){
                        action_Trigger_Map.put("~onquit",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onquit") != null){
                        action_Trigger_Map.get("~onquit").add(actionString);
                    }
                }
                if(actionString.toLowerCase().contains("~onmove")){
                    if(action_Trigger_Map.get("~onmove") == null){
                        action_Trigger_Map.put("~onmove",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onmove") != null){
                        action_Trigger_Map.get("~onmove").add(actionString);
                    }
                }
                if(actionString.toLowerCase().contains("~onsneak")){
                    if(action_Trigger_Map.get("~onsneak") == null){
                        action_Trigger_Map.put("~onsneak",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onsneak") != null){
                        action_Trigger_Map.get("~onsneak").add(actionString);
                    }
                }
                if(actionString.toLowerCase().contains("~onstandup")){
                    if(action_Trigger_Map.get("~onstandup") == null){
                        action_Trigger_Map.put("~onstandup",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onstandup") != null){
                        action_Trigger_Map.get("~onstandup").add(actionString);
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
                if(actionString.toLowerCase().contains("~onskillcaststart")){
                    if(action_Trigger_Map.get("~onskillcaststart") == null){
                        action_Trigger_Map.put("~onskillcaststart",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onskillcaststart") != null){
                        action_Trigger_Map.get("~onskillcaststart").add(actionString);
                    }
                }
                if(actionString.toLowerCase().contains("~onskillcaststop")){
                    if(action_Trigger_Map.get("~onskillcaststop") == null){
                        action_Trigger_Map.put("~onskillcaststop",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onskillcaststop") != null){
                        action_Trigger_Map.get("~onskillcaststop").add(actionString);
                    }
                }



            }
        }
    }

    public Player getPlayer() {
        return player;
    }
    /**動作列表**/
    public List<String> getPlayerActionList() {
        return playerActionList;
    }
    /**觸發的動作列表**/
    public Map<String, List<String>> getAction_Trigger_Map() {
        return action_Trigger_Map;
    }
}
