package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.inventory.EquipmentSlot.HAND;

public class PlayerEquipment {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerEquipment(){



    }

    public void reloadEquipment(Player player,int key){

        /**清除所有設定**/
        clearEqmStats(player);

        /**讀取目前身上裝備**/
        loadAllEq(player,key);

    }


    public void loadAllEq(Player player,int key){
        if(player.getInventory().getArmorContents().length > 0){
            for(ItemStack itemStack : player.getInventory().getArmorContents()){
                if(itemStack != null){
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if(itemMeta != null && !(itemMeta.getDisplayName().isEmpty())){
                        /**找出裝備上的Lore屬性**/
                        loadItem(itemStack,player);
                    }
                }
            }
        }
        if(key > 8){
            if(player.getInventory().getItemInMainHand() != null){
                ItemStack itemStack = player.getInventory().getItemInMainHand();
                if(itemStack != null){
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if(itemMeta != null && !(itemMeta.getDisplayName().isEmpty())){
                        /**找出主手上的Lore屬性**/
                        loadItem(itemStack,player);
                    }
                }
            }
        }else {
            if(player.getInventory().getItem(key) != null){
                ItemStack itemStack = player.getInventory().getItem(key);
                if(itemStack != null){
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if(itemMeta != null && !(itemMeta.getDisplayName().isEmpty())){
                        loadItem(itemStack,player);
                    }
                }
            }
        }

        if(player.getInventory().getItemInOffHand() != null){
            ItemStack itemStack = player.getInventory().getItemInOffHand();
            if(itemStack != null){
                ItemMeta itemMeta = itemStack.getItemMeta();
                if(itemMeta != null && !(itemMeta.getDisplayName().isEmpty())){
                    /**找出副手上的Lore屬性**/
                    loadItem(itemStack,player);
                }
            }
        }


    }

    /**找出裝備上的Lore屬性**/
    public void loadItem(ItemStack itemStack,Player player){
        if(itemStack != null && itemStack.getType() != Material.AIR){
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(itemMeta.getDisplayName() != null && itemMeta.getLore() != null){
                List<String> stringList = itemStack.getLore();
                for(String s : stringList){
                    if(s.contains(":")){
                        String[] strings = s.split(":");
                        if(strings.length == 2){
                            String attrName = strings[0];
                            String attrNumberString = strings[1].replace(" ","").replace("%","");
                            int attrNumber;
                            try {
                                attrNumber = Integer.valueOf(attrNumberString);
                            }catch (NumberFormatException exception){
                                attrNumber = 0;
                            }
                            if(attrName != null && attrNumber > 0){
                                /**比對裝備內容**/
                                setEqmStats(player,attrName,attrNumber);
                            }
                        }
                    }

                }
            }

        }
    }

    /**比對裝備內容**/
    public void setEqmStats(Player player,String key,int keyNumber){

        String uuidString = player.getUniqueId().toString();

        File playerEqmFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/equipment-stats.yml");
        FileConfiguration playerEqmConfig = YamlConfiguration.loadConfiguration(playerEqmFilePatch);

        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/"+uuidString+".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
        List<String> eqmSetNameList = playerConfig.getStringList(uuidString+".EquipmentStats");
        if(eqmSetNameList.size() > 0){
            for(String eqmSetName : eqmSetNameList){
                File eqmStatsPatch = new File(cd.getDataFolder(),"Class/Attributes/Stats/"+eqmSetName+".yml");
                FileConfiguration eqmConfig = YamlConfiguration.loadConfiguration(eqmStatsPatch);
                ConfigurationSection eqmStatsSec = eqmConfig.getConfigurationSection(eqmSetName);
                if(eqmStatsSec.getKeys(false).size() > 0){
                    for(String eqmStatsName : eqmStatsSec.getKeys(false)){
                        String statsName = eqmConfig.getString(eqmSetName+"."+eqmStatsName+".name");
                        if(statsName.contains(key)){
                            int nowNumber = playerEqmConfig.getInt(uuidString+".EquipmentStats."+eqmStatsName);
                            int newNumber = nowNumber + keyNumber;


                            playerEqmConfig.set(uuidString+".EquipmentStats."+eqmStatsName,newNumber);
                            try {
                                playerEqmConfig.save(playerEqmFilePatch);
                            }catch (Exception exception){
                                exception.printStackTrace();
                            }
                        }
                    }
                }

            }

        }

    }

    /**清除裝備設定內容**/
    public void clearEqmStats(Player player){
        String uuidString = player.getUniqueId().toString();

        File playerEqmFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/equipment-stats.yml");
        FileConfiguration playerEqmConfig = YamlConfiguration.loadConfiguration(playerEqmFilePatch);

        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/"+uuidString+".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
        List<String> eqmSetNameList = playerConfig.getStringList(uuidString+".EquipmentStats");
        if(eqmSetNameList.size() > 0){
            for(String eqmSetName : eqmSetNameList){
                File eqmStatsPatch = new File(cd.getDataFolder(),"Class/Attributes/Stats/"+eqmSetName+".yml");
                FileConfiguration eqmConfig = YamlConfiguration.loadConfiguration(eqmStatsPatch);
                ConfigurationSection eqmStatsSec = eqmConfig.getConfigurationSection(eqmSetName);
                if(eqmStatsSec.getKeys(false).size() > 0){
                    for(String eqmStatsName : eqmStatsSec.getKeys(false)){
                        playerEqmConfig.set(uuidString+".EquipmentStats."+eqmStatsName,0);
                        try {
                            playerEqmConfig.save(playerEqmFilePatch);
                        }catch (Exception exception){
                            exception.printStackTrace();
                        }
                    }
                }

            }

        }
    }


}
