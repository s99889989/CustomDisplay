package com.daxton.customdisplay.api.player.data;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ReplaceTrigger;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.config.SaveConfig;
import com.daxton.customdisplay.api.player.config.PlayerConfig2;
import com.daxton.customdisplay.api.player.data.set.*;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PermissionManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
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

    private double mana = 0;

    private double maxmana = 0;

    private double manaRegeneration = 0;

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
    public Map<String,String> attributes_Stats_Map2 = new HashMap<>();
    /**裝備屬性**/
    public Map<String,String> equipment_Stats_Map = new HashMap<>();
    public Map<String,String> name_Equipment_Map = new HashMap<>();
    public Map<String,String> equipment_Stats_Map2 = new HashMap<>();
    public Map<String, Integer> equipment_Enchants_Map = new HashMap<>();
    /**技能**/
    public Map<String,String> skills_Map = new HashMap<>();
    /**技能綁定**/
    public Map<String,String> binds_Map = new HashMap<>();

    /**動作列表**/
    private List<String> playerActionList = new ArrayList<>();



    /**觸發的動作列表**/
    private Map<String,List<String>> action_Trigger_Map = new HashMap<>();
    private Map<String,List<CustomLineConfig>> action_Trigger_Map2 = new HashMap<>();
    private Map<String,List<CustomLineConfig>> action_Item_Trigger_Map = new HashMap<>();

    public PlayerData(Player player){
        this.player = player;
        uuidString = player.getUniqueId().toString();

        FileConfiguration config = ConfigMapManager.getFileConfigurationMap().get("config.yml");

        new PlayerConfig2(player);
        playerConfig = new LoadConfig().getPlayerConfig(player);
        /**是否使用屬性**/
        String attackCore = config.getString("AttackCore");
        if(attackCore.toLowerCase().contains("customcore")){
            setPlayerData(player);
        }

        boolean skill = config.getBoolean("Class.Skill");
        if(skill){

            /**清除所有屬性**/
            new PlayerBukkitAttribute().removeAllAttribute(player);

            new PlayerBinds().setMap(player,binds_Map,playerConfig);
            new PlayerLevel().setMap(player,level_Map,playerConfig);
            new PlayerPoint().setMap(player,point_Map,playerConfig);
            new PlayerAttributesPoint().setMap(player,attributes_Point_Map,playerConfig);
            new PlayerAttributesStats().setMap(player,attributes_Stats_Map,playerConfig);
            new PlayerEquipmentStats().setMap(player,equipment_Stats_Map,playerConfig,name_Equipment_Map);
            new PlayerSkills().setMap(player,skills_Map,playerConfig);

        }

        /**設定動作列表**/
        setPlayerActionList();
        /**依照觸發條件分配**/
        setActionList();


    }

    public void setPlayerData(Player player){

        /**先設預設值**/





        /**2次計算**/
        new PlayerAttributesStats().setFormula(player,attributes_Stats_Map,playerConfig);


        /**核心屬性設置**/
        new PlayerAttributeCore().setBukkitAttribute(player);
        /**其他屬性設置**/
        new PlayerAttributeCore().setCoreAttribute(player);

        /**設置回血**/
        health_Regeneration(player);

    }



    /**設置回血回魔**/
    public void health_Regeneration(Player player){
        /**回血量**/
        String amountString = PlayerDataMap.core_Formula_Map.get("Health_Regeneration");
        /**魔量**/
        String maxmanaString = PlayerDataMap.core_Formula_Map.get("Max_Mana");
        maxmanaString = new ConversionMain().valueOf(player,null,maxmanaString);
        try {
            mana = Double.valueOf(maxmanaString);
            maxmana = Double.valueOf(maxmanaString);
        }catch (NumberFormatException exception){
            mana = 20;
            maxmana = 20;
        }
        /**回魔量**/
        String manaRegString = PlayerDataMap.core_Formula_Map.get("Mana_Regeneration");
        manaRegString = new ConversionMain().valueOf(player,null,manaRegString);
        try {
            manaRegeneration = Double.valueOf(manaRegString);
        }catch (NumberFormatException exception){
            manaRegeneration = 1;
        }


        bukkitRunnable = new BukkitRunnable(){
            @Override
            public void run() {
                String amountString2 = new ConversionMain().valueOf(player,null,amountString);
                double amount = 0;
                try {
                    amount = Double.valueOf(amountString2);
                }catch (NumberFormatException exception){
                    amount = 0;
                }

                if(mana != maxmana){
                    if((mana += manaRegeneration) > maxmana){
                        mana = maxmana;
                    }
                }


                if(player.getHealth() != player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()){
                    double giveHealth = player.getHealth()+amount;
                    double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                    if(giveHealth > maxHealth){
                        giveHealth = giveHealth - (giveHealth - maxHealth);
                    }
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

        FileConfiguration configuration = cd.getConfigManager().config;
        boolean b = configuration.getBoolean("Permission.fastUse");

        if(!b){
            for(String configName : ConfigMapManager.getFileConfigurationNameMap().values()){
                if(configName.contains("Permission")){
                    String perName = configName.replace("Permission_","").replace(".yml","").toLowerCase();
                    if(player.hasPermission("customdisplay.permission."+perName)){
                        for(String list : ConfigMapManager.getFileConfigurationMap().get(configName).getStringList("Action")){
                            thisList.add(list);

                        }
                    }
                }
            }
        }else {
            for(String configName : ConfigMapManager.getFileConfigurationNameMap().values()){
                if(configName.contains("Permission")){
                    for(String list : ConfigMapManager.getFileConfigurationMap().get(configName).getStringList("Action")){
                        String perName = configName.replace("Permission_","").replace(".yml","").toLowerCase();
                        //PlayerDataMap.playerAction_Permission.put(uuidString+ReplaceTrigger.valueOf(list),"customdisplay.permission."+perName);
                        thisList.add(list+" #"+"customdisplay.permission."+perName);

                    }
                }
            }
        }

        skills_Map.forEach((s, s2) -> {
            if(s.contains("_level")){
                String skillKey = s.replace("_level","");
                FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Skills_"+skillKey+".yml");
                boolean passiveSkill = skillConfig.getBoolean(skillKey+".PassiveSkill");
                int skillLevel = Integer.parseInt(s2);
                if(passiveSkill && skillLevel > 0){
                    //cd.getLogger().info("技能: "+s+" : "+s2+" : "+passiveSkill);
                    List<String> skillList = skillConfig.getStringList(skillKey+".Action");
                    skillList.forEach(s1 -> {
                        thisList.add(s1);
                        //cd.getLogger().info(s1);
                    });
                }

            }

        });

        playerActionList = thisList.stream().distinct().collect(Collectors.toList());

    }
    /**設定個個動作Map**/
    public void setActionList(){
        if(action_Trigger_Map.size() > 0){
            action_Trigger_Map.clear();
        }
        //action_Trigger_Map = new PlayerAction().setPlayerAction(playerActionList);
        action_Trigger_Map2 = new PlayerAction2().setPlayerAction(playerActionList);

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

    public Map<String, List<CustomLineConfig>> getAction_Trigger_Map2() {
        return action_Trigger_Map2;
    }

    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable;
    }

    public Map<String, List<CustomLineConfig>> getAction_Item_Trigger_Map() {
        return action_Item_Trigger_Map;
    }
}
