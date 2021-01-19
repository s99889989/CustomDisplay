package com.daxton.customdisplay.api.player.data;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.config.PlayerConfig2;
import com.daxton.customdisplay.api.player.data.set.*;
import com.daxton.customdisplay.manager.PermissionManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerData {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private FileConfiguration playerConfig;

    private BukkitRunnable bukkitRunnable;

    private String uuidString = "";

    /**等級**/
    public Map<String,String> level_Map = new HashMap<>();
    /**點數**/
    public Map<String,String> point_Map = new HashMap<>();
    /**屬性點數**/
    public Map<String,String> attributes_Point_Map = new HashMap<>();
    /**玩家屬性**/
    public Map<String,String> attributes_Stats_Map = new HashMap<>();
    /**裝備屬性**/
    public Map<String,String> equipment_Stats_Map = new HashMap<>();
    /**技能**/
    public Map<String,String> skills_Map = new HashMap<>();
    /**技能綁定**/
    public Map<String,String> binds_Map = new HashMap<>();

    /**動作列表**/
    private List<String> playerActionList = new ArrayList<>();

    /**觸發的動作列表**/
    private Map<String,List<String>> action_Trigger_Map = new HashMap<>();

    public PlayerData(Player player){
        this.player = player;
        uuidString = player.getUniqueId().toString();



        new PlayerConfig2(player);
        playerConfig = new LoadConfig().getPlayerConfig(player);
        /**是否使用屬性**/
        String attackCore = cd.getConfigManager().config.getString("AttackCore");
        if(attackCore.toLowerCase().contains("customcore")){
            //customCore(player);
            setPlayerData(player);
        }

        setPlayerActionList();
        setActionList();


    }

    public void setPlayerData(Player player){

        /**先設預設值**/
        new PlayerLevel().setMap(player,level_Map,playerConfig);
        new PlayerPoint().setMap(player,point_Map,playerConfig);
        new PlayerAttributesPoint().setMap(player,attributes_Point_Map,playerConfig);
        new PlayerAttributesStats().setMap(player,attributes_Stats_Map,playerConfig);
        new PlayerEquipmentStats().setMap(player,equipment_Stats_Map,playerConfig);
        new PlayerSkills().setMap(player,skills_Map,playerConfig);
        new PlayerBinds().setMap(player,binds_Map,playerConfig);

        /**2次計算**/
        new PlayerAttributesStats().setFormula(player,attributes_Stats_Map,playerConfig);



    }

    /**Custom職業相關**/
    public void customCore(Player player){
        new PlayerRPG().setPlayerRPG(player);
        health_Regeneration(player);
    }


    public void health_Regeneration(Player player){
        bukkitRunnable = new BukkitRunnable(){
            @Override
            public void run() {

                if(player.getHealth() != player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()){
                    double giveHealth = player.getHealth()+PlayerDataMap.getCore_Attribute_Map().get(uuidString).getAttribute("Health_Regeneration");
                    double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                    if(giveHealth > maxHealth){
                        giveHealth = giveHealth - (giveHealth - maxHealth);
                    }
                    //cd.getLogger().info("回血:"+giveHealth);
                    //player.sendMessage("回血:"+PlayerDataMap.getCore_Attribute_Map().get(uuidString).getAttribute("Health_Regeneration"));
                    player.setHealth(giveHealth);
                }


            }
        };
        bukkitRunnable.runTaskTimer(cd,0,200);
    }


    /**獲取動作列表**/
    public void setPlayerActionList() {
        String uuidString = player.getUniqueId().toString();
        playerActionList.clear();
        File inputFile = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
        FileConfiguration inputConfig = YamlConfiguration.loadConfiguration(inputFile);
        List<String> setList = inputConfig.getStringList(uuidString+".Action");
        List<String> thisList = new ArrayList<>();
        for(String set : setList){
            File inputFile2 = new File(cd.getDataFolder(),"Class/Action/"+set+".yml");
            FileConfiguration inputConfig2 = YamlConfiguration.loadConfiguration(inputFile2);
            List<String> actionList = inputConfig2.getStringList("Action");
            for(String string : actionList){
                thisList.add(string);
            }


        }

        for(String stringConfig : PermissionManager.getPermission_String_Map().values()){
            if(player.hasPermission(stringConfig)){
                for(String list : PermissionManager.getPermission_FileConfiguration_Map().get(stringConfig).getStringList("Action")){
                    thisList.add(list);
                }
            }
        }

        playerActionList = thisList.stream().distinct().collect(Collectors.toList());


    }
    /**設定個個動作Map**/
    public void setActionList(){
        if(action_Trigger_Map.size() > 0){
            action_Trigger_Map.clear();
        }
        action_Trigger_Map = new PlayerAction().setPlayerAction(playerActionList);
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

    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable;
    }
}
