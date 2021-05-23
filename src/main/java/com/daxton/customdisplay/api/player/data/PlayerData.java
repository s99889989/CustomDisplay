package com.daxton.customdisplay.api.player.data;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.config.LoadConfig;
import com.daxton.customdisplay.api.player.config.PlayerConfig2;
import com.daxton.customdisplay.api.player.data.set.*;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
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

    public double maxmana = 0;

    private double manaRegeneration = 0;

    private FileConfiguration playerConfig;

    private BukkitRunnable bukkitRunnable;

    private String uuidString = "";

    /**標記動作列表**/
    public Map<String, String> taskIDList = new HashMap<>();
    /**等級**/
    public Map<String,String> level_Map = new HashMap<>();
    /**點數**/
    public Map<String,String> point_Map = new HashMap<>();
    /**屬性點數**/
    public Map<String,String> attributes_Point_Map = new HashMap<>();
    public Map<String,String> attributes_Point_Map2 = new HashMap<>();
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
    public static Map<String, List<Map<String, String>>> skill_Custom_Map = new HashMap<>();
    public static Map<String, String> skill_Name_Map = new HashMap<>();
    public static Map<String, List<String>> skill_Key_Map = new HashMap<>();

    /**動作列表**/
    private static List<Map<String, String>> player_Action_List_Map = new ArrayList<>();
    public static List<Map<String, String>> player_Item_Action_List_Map = new ArrayList<>();

    /**觸發的動作列表**/
    private Map<String,List<CustomLineConfig>> action_Trigger_Map2 = new HashMap<>();
    private Map<String,List<CustomLineConfig>> action_Item_Trigger_Map = new HashMap<>();

    public PlayerData(Player player){
        this.player = player;
        uuidString = player.getUniqueId().toString();

        FileConfiguration config = ConfigMapManager.getFileConfigurationMap().get("config.yml");

        new PlayerConfig2(player);
        playerConfig = LoadConfig.getPlayerConfig(player);
        /**是否使用屬性**/
        String attackCore = config.getString("AttackCore");
        if(attackCore.toLowerCase().contains("customcore")){
            setPlayerData(player);
        }

        boolean skill = config.getBoolean("Class.Skill");
        if(skill){

            /**清除所有屬性**/
            new PlayerBukkitAttribute().removeAllAttribute(player);

            /**先設預設值**/
            new PlayerBinds().setMap(player,binds_Map,playerConfig);
            new PlayerLevel().setMap(player,level_Map,playerConfig);
            new PlayerPoint().setMap(player,point_Map,playerConfig);
            new PlayerAttributesPoint().setMap(player,attributes_Point_Map,playerConfig);
            new PlayerAttributesStats().setMap(player,attributes_Stats_Map,playerConfig);
            new PlayerEquipmentStats().setMap(player,equipment_Stats_Map,playerConfig,name_Equipment_Map);
            new PlayerSkills().setMap(player,skills_Map,playerConfig);

            /**2次計算**/
            new PlayerAttributesStats().setFormula(player,attributes_Stats_Map,playerConfig);

            /**核心屬性設置**/
            new PlayerAttributeCore().setBukkitAttribute(player);
            /**設置回血**/
            health_Regeneration(player);
        }

        /**設定動作列表**/
        setPlayerActionList();
        /**依照觸發條件分配**/
        //setActionList();


    }

    public void setPlayerData(Player player){

        /**其他屬性設置**/
        //new PlayerAttributeCore().setCoreAttribute(player);

    }



    /**設置回血回魔**/
    public void health_Regeneration(Player player){
        String uuidString = player.getUniqueId().toString();
        /**魔量**/
        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");
        String maxManaString = fileConfiguration.getString("CoreAttribute.Max_Mana.formula");
        String maxManaString2 = ConversionMain.valueOf(player,null,maxManaString);
        double mana = 0;
        try {
            mana = Double.valueOf(maxManaString2);
            maxmana = Double.valueOf(maxManaString2);
        }catch (NumberFormatException exception){
            mana = 20;
            maxmana = 20;
        }
        //cd.getLogger().info(mana+" : "+maxmana);
        if(PlayerManager.player_nowMana.get(uuidString) == null){
            PlayerManager.player_nowMana.put(uuidString, mana);
        }
        Map<String, Double> player_nowMana = PlayerManager.player_nowMana;

        /**回魔量**/
        String manaRegString = fileConfiguration.getString("CoreAttribute.Mana_Regeneration.formula");
        manaRegString = ConversionMain.valueOf(player,null,manaRegString);
        try {
            manaRegeneration = Double.valueOf(manaRegString);
        }catch (NumberFormatException exception){
            manaRegeneration = 1;
        }

        /**回血量**/
        String healthRegenerationString = fileConfiguration.getString("CoreAttribute.Health_Regeneration.formula");
        String healthRegenerationString2 = ConversionMain.valueOf(player,null,healthRegenerationString);

        bukkitRunnable = new BukkitRunnable(){

            @Override
            public void run() {
                /**回魔**/

                double mana = player_nowMana.get(uuidString);
                if(mana <= maxmana){
                    mana += manaRegeneration;
                    if(mana > maxmana){
                        mana = maxmana;
                        player_nowMana.put(uuidString,mana);
                    }else {
                        player_nowMana.put(uuidString,mana);
                    }
                }

                /**回血**/
                double amount = 0;
                try {
                    amount = Double.valueOf(healthRegenerationString2);
                }catch (NumberFormatException exception){
                    amount = 0;
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
        ///////////////////////////////////////////////////////////////////
        FileConfiguration playerConfig = ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml");
        List<String> actionStringList = playerConfig.getStringList(uuidString+".Action");
        player_Action_List_Map.clear();
        ActionManager.class_Action_Map.forEach((s, maps) -> {

            actionStringList.forEach(s1 -> {
                if(s1.equals(s)){
                    maps.forEach(stringStringMap -> {
                        player_Action_List_Map.add(stringStringMap);
                    });

                }
            });

        });

    }


    public Player getPlayer() {
        return player;
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

    public static List<Map<String, String>> getPlayer_Action_List_Map() {
        return player_Action_List_Map;
    }
}
