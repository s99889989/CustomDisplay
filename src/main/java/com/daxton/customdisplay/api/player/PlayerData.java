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
                /**當攻擊時**/
                if(actionString.toLowerCase().contains("~onattack")){
                    if(action_Trigger_Map.get("~onattack") == null){
                        action_Trigger_Map.put("~onattack",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onattack") != null){
                        action_Trigger_Map.get("~onattack").add(actionString);
                    }
                }
                /**當爆擊時**/
                if(actionString.toLowerCase().contains("~oncrit")){
                    if(action_Trigger_Map.get("~oncrit") == null){
                        action_Trigger_Map.put("~oncrit",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~oncrit") != null){
                        action_Trigger_Map.get("~oncrit").add(actionString);
                    }
                }
                /**當魔法攻擊時**/
                if(actionString.toLowerCase().contains("~onmagic")){
                    if(action_Trigger_Map.get("~onmagic") == null){
                        action_Trigger_Map.put("~onmagic",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onmagic") != null){
                        action_Trigger_Map.get("~onmagic").add(actionString);
                    }
                }
                /**當魔法攻擊爆擊時**/
                if(actionString.toLowerCase().contains("~onmcrit")){
                    if(action_Trigger_Map.get("~onmcrit") == null){
                        action_Trigger_Map.put("~onmcrit",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onmcrit") != null){
                        action_Trigger_Map.get("~onmcrit").add(actionString);
                    }
                }
                /**被攻擊時**/
                if(actionString.toLowerCase().contains("~ondamaged")){
                    if(action_Trigger_Map.get("~ondamaged") == null){
                        action_Trigger_Map.put("~ondamaged",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~ondamaged") != null){
                        action_Trigger_Map.get("~ondamaged").add(actionString);
                    }
                }
                /**當回血時**/
                if(actionString.toLowerCase().contains("~onregainhealth")){
                    if(action_Trigger_Map.get("~onregainhealth") == null){
                        action_Trigger_Map.put("~onregainhealth",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onregainhealth") != null){
                        action_Trigger_Map.get("~onregainhealth").add(actionString);
                    }
                }
                /**當登入時**/
                if(actionString.toLowerCase().contains("~onjoin")){
                    if(action_Trigger_Map.get("~onjoin") == null){
                        action_Trigger_Map.put("~onjoin",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onjoin") != null){
                        action_Trigger_Map.get("~onjoin").add(actionString);
                    }
                }
                /**當登出時**/
                if(actionString.toLowerCase().contains("~onquit")){
                    if(action_Trigger_Map.get("~onquit") == null){
                        action_Trigger_Map.put("~onquit",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onquit") != null){
                        action_Trigger_Map.get("~onquit").add(actionString);
                    }
                }
                /**移動時**/
                if(actionString.toLowerCase().contains("~onmove")){
                    if(action_Trigger_Map.get("~onmove") == null){
                        action_Trigger_Map.put("~onmove",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onmove") != null){
                        action_Trigger_Map.get("~onmove").add(actionString);
                    }
                }
                /**蹲下時**/
                if(actionString.toLowerCase().contains("~onsneak")){
                    if(action_Trigger_Map.get("~onsneak") == null){
                        action_Trigger_Map.put("~onsneak",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onsneak") != null){
                        action_Trigger_Map.get("~onsneak").add(actionString);
                    }
                }
                /**站起來時**/
                if(actionString.toLowerCase().contains("~onstandup")){
                    if(action_Trigger_Map.get("~onstandup") == null){
                        action_Trigger_Map.put("~onstandup",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onstandup") != null){
                        action_Trigger_Map.get("~onstandup").add(actionString);
                    }
                }
                /**當死亡時**/
                if(actionString.toLowerCase().contains("~ondeath")){
                    if(action_Trigger_Map.get("~ondeath") == null){
                        action_Trigger_Map.put("~ondeath",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~ondeath") != null){
                        action_Trigger_Map.get("~ondeath").add(actionString);
                    }
                }
                /**當按下案件F時，一開始會觸發為ON，登出重新計算**/
                if(actionString.toLowerCase().contains("~onkeyfon")){
                    if(action_Trigger_Map.get("~onkeyfon") == null){
                        action_Trigger_Map.put("~onkeyfon",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkeyfon") != null){
                        action_Trigger_Map.get("~onkeyfon").add(actionString);
                    }
                }
                /**再按下案件F時，觸發為OFF，登出重新計算**/
                if(actionString.toLowerCase().contains("~onkeyfoff")){
                    if(action_Trigger_Map.get("~onkeyfoff") == null){
                        action_Trigger_Map.put("~onkeyfoff",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkeyfoff") != null){
                        action_Trigger_Map.get("~onkeyfoff").add(actionString);
                    }
                }
                /**當切換到物品欄1時**/
                if(actionString.toLowerCase().contains("~onkey1")){
                    if(action_Trigger_Map.get("~onkey1") == null){
                        action_Trigger_Map.put("~onkey1",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey1") != null){
                        action_Trigger_Map.get("~onkey1").add(actionString);
                    }
                }
                /**當切換到物品欄2時**/
                if(actionString.toLowerCase().contains("~onkey2")){
                    if(action_Trigger_Map.get("~onkey2") == null){
                        action_Trigger_Map.put("~onkey2",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey2") != null){
                        action_Trigger_Map.get("~onkey2").add(actionString);
                    }
                }
                /**當切換到物品欄3時**/
                if(actionString.toLowerCase().contains("~onkey3")){
                    if(action_Trigger_Map.get("~onkey3") == null){
                        action_Trigger_Map.put("~onkey3",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey3") != null){
                        action_Trigger_Map.get("~onkey3").add(actionString);
                    }
                }
                /**當切換到物品欄4時**/
                if(actionString.toLowerCase().contains("~onkey4")){
                    if(action_Trigger_Map.get("~onkey4") == null){
                        action_Trigger_Map.put("~onkey4",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey4") != null){
                        action_Trigger_Map.get("~onkey4").add(actionString);
                    }
                }
                /**當切換到物品欄5時**/
                if(actionString.toLowerCase().contains("~onkey5")){
                    if(action_Trigger_Map.get("~onkey5") == null){
                        action_Trigger_Map.put("~onkey5",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey5") != null){
                        action_Trigger_Map.get("~onkey5").add(actionString);
                    }
                }
                /**當切換到物品欄6時**/
                if(actionString.toLowerCase().contains("~onkey6")){
                    if(action_Trigger_Map.get("~onkey6") == null){
                        action_Trigger_Map.put("~onkey6",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey6") != null){
                        action_Trigger_Map.get("~onkey6").add(actionString);
                    }
                }
                /**當切換到物品欄7時**/
                if(actionString.toLowerCase().contains("~onkey7")){
                    if(action_Trigger_Map.get("~onkey7") == null){
                        action_Trigger_Map.put("~onkey7",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey7") != null){
                        action_Trigger_Map.get("~onkey7").add(actionString);
                    }
                }
                /**當切換到物品欄8時**/
                if(actionString.toLowerCase().contains("~onkey8")){
                    if(action_Trigger_Map.get("~onkey8") == null){
                        action_Trigger_Map.put("~onkey8",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey8") != null){
                        action_Trigger_Map.get("~onkey8").add(actionString);
                    }
                }
                /**當切換到物品欄9時**/
                if(actionString.toLowerCase().contains("~onkey9")){
                    if(action_Trigger_Map.get("~onkey9") == null){
                        action_Trigger_Map.put("~onkey9",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey9") != null){
                        action_Trigger_Map.get("~onkey9").add(actionString);
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
