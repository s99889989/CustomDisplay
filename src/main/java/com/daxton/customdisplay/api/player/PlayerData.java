package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerDataMap;
import com.daxton.customdisplay.manager.player.TriggerManager;
import com.daxton.customdisplay.task.player.OnTimer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerData {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;
    /**玩家使用的設定檔**/
    private FileConfiguration PlayerData;
    /**玩家設定檔的項目列表**/
    private List<String> PlayerActionList = new ArrayList<>();
    /**項目列表內的動作關鍵字，用來選擇動作**/
    private List<String> PlayerActionKeyList = new ArrayList<>();
    /**跟據動作關機字，取得有關動作項目的設定檔**/
    private List<FileConfiguration> ActionData = new ArrayList<>();
    /**所擁有動作列表內的項目列表**/
    private Map<String,List<String>> ActionListMap = new HashMap<>();

    public PlayerData(Player player){
        this.player = player;
        setPlayerData();
        setPlayerActionList();
        setPlayerActionKeyList();
        setAcionConfig();
        setActionListMap();
    }

    public void setPlayerData() {
        for(String stl : ConfigMapManager.getFileConfigurationNameMap().values()){
            if(stl.equals("Players_"+player.getName()+".yml")){
                PlayerData = ConfigMapManager.getFileConfigurationMap().get(stl);
                break;
            }else {
                PlayerData = ConfigMapManager.getFileConfigurationMap().get("Players_Default.yml");
            }
        }
    }

    public void setPlayerActionList() {
        if(getPlayerData().getStringList("Action").size() < 1){


            OnTimer onTimer = TriggerManager.getOnTimerMap().get(player.getUniqueId());
            if(onTimer != null){
                onTimer.getBukkitRunnable().cancel();
                TriggerManager.getOnTimerMap().remove(player.getUniqueId());
            }

            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(player.getUniqueId());
            if(playerData != null){
                PlayerDataMap.getPlayerDataMap().remove(player.getUniqueId());
            }

        }else {
            for(String string : getPlayerData().getStringList("Action")){
                PlayerActionList.add(string);
            }
        }

    }


    public void setPlayerActionKeyList() {
        for(String string : getPlayerActionList()){
            String[] strings1 = string.split("]");
            String[] strings2 = strings1[0].split("=");
            if(strings2.length == 2){
                PlayerActionKeyList.add(strings2[1]);
            }
        }
    }

    /**獲取動作資料夾內的所有設定檔**/
    public void setAcionConfig(){
        List<FileConfiguration> stringList = new ArrayList<>();
        for(String string : ConfigMapManager.getFileConfigurationNameMap().values()){
            if(string.contains("Actions_")){
                FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get(string);
                for(String string2 : getPlayerActionKeyList()){
                    if(fileConfiguration.getKeys(false).contains(string2)){
                        stringList.add(fileConfiguration);
                    }
                }
            }
        }
        ActionData = stringList;
    }

    public void setActionListMap() {
        List<String> st3;
        for(FileConfiguration fileConfiguration : getActionData()){
            for(String string : getPlayerActionKeyList()){
                if(fileConfiguration.getKeys(false).contains(string)){
                    st3 = (fileConfiguration.getStringList(string+".Action"));
                    ActionListMap.put(string,st3);
                }
            }
        }
    }


    public Player getPlayer() {
        return player;
    }

    public FileConfiguration getPlayerData() {
        return PlayerData;
    }

    public List<String> getPlayerActionList() {
        return PlayerActionList;
    }

    public List<String> getPlayerActionKeyList() {
        return PlayerActionKeyList;
    }

    public List<FileConfiguration> getActionData() {
        return ActionData;
    }

    public Map<String, List<String>> getActionListMap() {
        return ActionListMap;
    }
}
