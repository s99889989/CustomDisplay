package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerData {

    private Player player;

    private FileConfiguration fileConfiguration;

    private List<String> playerActionList;

    public PlayerData(Player player){
        this.player = player;
        setPlayerActionList();
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
    }

    public Player getPlayer() {
        return player;
    }

    public List<String> getPlayerActionList() {
        return playerActionList;
    }
}
