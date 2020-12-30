package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.inventory.EquipmentSlot.HAND;

public class PlayerEquipment {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerEquipment(Player player){


        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(itemStack.getType() != Material.AIR){
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(itemMeta.getDisplayName() != null && itemMeta.getLore() != null){

                List<String> stringList = itemStack.getLore();
                //player.sendMessage(itemMeta.getDisplayName());
                for(String s : stringList){


                }
            }

        }

    }

    public PlayerEquipment(Player player,int slot){
        player.sendMessage("切換");
        ItemStack itemStack = player.getInventory().getItem(slot);
        clearEqmStats(player);
        if(itemStack != null && itemStack.getType() != Material.AIR){
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(itemMeta.getDisplayName() != null && itemMeta.getLore() != null){
                List<String> stringList = itemStack.getLore();
                for(String s : stringList){
                    if(s.contains(":")){
                        String[] strings = s.split(":");
                        if(strings.length == 2){
                            String attrName = strings[0];
                            String attrNumber = strings[1].replace(" ","").replace("%","");
                            int attrnum;
                            try {
                                attrnum = Integer.valueOf(attrNumber);
                            }catch (NumberFormatException exception){
                                attrnum = 0;
                            }
                            if(attrName != null && attrnum > 0){
                                setEqmStats(player,attrName,attrnum);
                            }
                        }
                    }

                }
            }

        }

    }

    public void clearEqmStats(Player player){
        String uuidString = player.getUniqueId().toString();
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
        FileConfiguration playerConfig = ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml");
        String className = playerConfig.getString(uuidString+".Class");
        FileConfiguration classConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Main_"+className+".yml");
        List<String> eqNameList = classConfig.getStringList(className+".EquipmentStats");
        for(String eqName : eqNameList){
            FileConfiguration eqStatsConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_Equipment_"+eqName+".yml");
            ConfigurationSection eqStatsList = eqStatsConfig.getConfigurationSection(eqName);
            if(eqStatsList.getKeys(false).size() > 0){
                for(String eqSatats : eqStatsList.getKeys(false)){
                    //player.sendMessage(eqSatats);
                    playerConfig.set(uuidString+".EquipmentStats."+eqSatats,0);
                    try {
                        playerConfig.save(playerFilePatch);
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }

                }
            }


        }
    }


    public void setEqmStats(Player player,String key,int keyNumber){

        String uuidString = player.getUniqueId().toString();
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+".yml");
        FileConfiguration playerConfig = ConfigMapManager.getFileConfigurationMap().get("Players_"+uuidString+".yml");
        String className = playerConfig.getString(uuidString+".Class");
        FileConfiguration classConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Main_"+className+".yml");
        List<String> eqNameList = classConfig.getStringList(className+".EquipmentStats");
        for(String eqName : eqNameList){
            FileConfiguration eqStatsConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_Equipment_"+eqName+".yml");
            ConfigurationSection eqStatsList = eqStatsConfig.getConfigurationSection(eqName);
            if(eqStatsList.getKeys(false).size() > 0){
                for(String eqSatats : eqStatsList.getKeys(false)){
                    String statsName = eqStatsConfig.getString(eqName+"."+eqSatats+".name");
                    if(statsName.contains(key)){
                        player.sendMessage(statsName+":" +eqSatats);
                        playerConfig.set(uuidString+".EquipmentStats."+eqSatats,keyNumber);
                        try {
                            playerConfig.save(playerFilePatch);
                        }catch (Exception exception){
                            exception.printStackTrace();
                        }
                    }

                }
            }


        }


    }


}
