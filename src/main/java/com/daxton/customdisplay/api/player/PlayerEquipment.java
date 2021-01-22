package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.data.set.PlayerEquipmentStats;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;

import static org.bukkit.inventory.EquipmentSlot.HAND;

public class PlayerEquipment {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerEquipment(){



    }

    public void reloadEquipment(Player player,int key){

        String attackCore = cd.getConfigManager().config.getString("AttackCore");
        if(attackCore.toLowerCase().contains("customcore")){
            /**清除所有設定**/
            new PlayerEquipmentStats().setMap(player);

            /**讀取目前身上裝備**/
            loadAllEq(player,key);
        }

    }


    public void loadAllEq(Player player,int key){
        if(player.getInventory().getArmorContents().length > 0){
            for(ItemStack itemStack : player.getInventory().getArmorContents()){
                if(itemStack != null){
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if(itemMeta != null && !(itemMeta.getDisplayName().isEmpty())){
                        /**找出裝備上的Lore屬性**/
                        loadItem(itemStack,player,"");
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
                        loadItem(itemStack,player,"MainHand");
                    }
                }
            }
        }else {
            if(player.getInventory().getItem(key) != null){
                ItemStack itemStack = player.getInventory().getItem(key);
                if(itemStack != null){
                    ItemMeta itemMeta = itemStack.getItemMeta();
                    if(itemMeta != null && !(itemMeta.getDisplayName().isEmpty())){
                        loadItem(itemStack,player,"MainHand");
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
                    loadItem(itemStack,player,"");
                }
            }
        }


    }

    /**找出裝備上的Lore屬性**/
    public void loadItem(ItemStack itemStack,Player player,String parts){

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
                            if(attrName != null && attrNumberString != null){
                                /**比對裝備內容**/
                                setEqmStats(player,attrName,attrNumberString);
                            }
                        }
                    }

                }
            }

        }
    }


    /**比對裝備內容**/
    public void setEqmStats(Player player,String key,String keyNumberString){


        UUID playerUUID = player.getUniqueId();
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
        if(playerData != null){
            Map<String,String> attributes_EquipmentStats_Map = playerData.equipment_Stats_Map;
            Map<String,String> name_Equipment_Map = playerData.name_Equipment_Map;
            Iterator keys = name_Equipment_Map.keySet().iterator();
            while(keys.hasNext()){
                String key2 = (String)keys.next();
                if(key2.equals(key)){
                    String attr = name_Equipment_Map.get(key2);
                    attributes_EquipmentStats_Map.put(attr,keyNumberString);
                    //player.sendMessage(attr+" : "+keyNumberString);


                }
            }
        }



//        String uuidString = player.getUniqueId().toString();
//
//        File playerEqmFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/equipment-stats.yml");
//        FileConfiguration playerEqmConfig = YamlConfiguration.loadConfiguration(playerEqmFilePatch);
//
//        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/"+uuidString+".yml");
//        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
//        List<String> eqmSetNameList = playerConfig.getStringList(uuidString+".Equipment_Stats");
//        if(eqmSetNameList.size() > 0){
//            for(String eqmSetName : eqmSetNameList){
//                File eqmStatsPatch = new File(cd.getDataFolder(),"Class/Attributes/EquipmentStats/"+eqmSetName+".yml");
//                FileConfiguration eqmConfig = YamlConfiguration.loadConfiguration(eqmStatsPatch);
//                ConfigurationSection eqmStatsSec = eqmConfig.getConfigurationSection(eqmSetName);
//                if(eqmStatsSec.getKeys(false).size() > 0){
//                    for(String eqmStatsName : eqmStatsSec.getKeys(false)){
//                        String statsName = eqmConfig.getString(eqmSetName+"."+eqmStatsName+".name");
//
//
//                        if(statsName.contains(key)){
//                            String nowNumberString = playerEqmConfig.getString(uuidString+".Equipment_Stats."+eqmStatsName);
//                            String end = "";
//                            try {
//                                double nowNumber = Arithmetic.eval(nowNumberString);
//                                double keyNumber = Arithmetic.eval(keyNumberString);
//                                double newNumber = nowNumber + keyNumber;
//                                end = String.valueOf(newNumber);
//                            }catch (Exception exception){
//                                end = keyNumberString;
//                            }
//
//                            playerEqmConfig.set(uuidString+".Equipment_Stats."+eqmStatsName,end);
//
//                            try {
//                                playerEqmConfig.save(playerEqmFilePatch);
//                            }catch (Exception exception){
//                                exception.printStackTrace();
//                            }
//                        }
//                    }
//                }
//
//            }
//
//        }

    }




}
