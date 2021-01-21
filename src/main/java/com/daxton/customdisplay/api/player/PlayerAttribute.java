package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.api.player.data.set.PlayerBukkitAttribute;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class PlayerAttribute {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerAttribute(){

    }
    public PlayerAttribute(Player player){
        String playerUUIDString = player.getUniqueId().toString();
        File playerFilePatch = new File(cd.getDataFolder(),"Players/"+playerUUIDString+"/"+playerUUIDString+".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
        List<String> attrStatsNameList = playerConfig.getStringList(playerUUIDString+".Attributes_Stats");
        if(attrStatsNameList.size() > 0){
            for(String attrStatsFileName : attrStatsNameList){

                File attrFilePatch = new File(cd.getDataFolder(),"Players/"+playerUUIDString+"/attributes-stats.yml");
                FileConfiguration attrConfig = YamlConfiguration.loadConfiguration(attrFilePatch);

                FileConfiguration attrStatsConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Attributes_EntityStats_"+attrStatsFileName+".yml");

                ConfigurationSection attrStatsSec = attrStatsConfig.getConfigurationSection(attrStatsFileName);
                if(attrStatsSec.getKeys(false).size() > 0){
                    for(String attrStats : attrStatsSec.getKeys(false)){
                        String statsNumberString = attrStatsConfig.getString(attrStatsFileName+"."+attrStats+".formula");

                        if(statsNumberString != null){
                            statsNumberString = new ConversionMain().valueOf(player,null,statsNumberString);
                        }else {
                            statsNumberString = "0";
                        }
                        //cd.getLogger().info(statsNumberString);

                        double statsNumber = 0;
                        String numberDec = "";
                        try {
                            double number = Arithmetic.eval(statsNumberString);
                            numberDec = new NumberUtil(number,"#.###").getDecimalString();
                            statsNumber = Double.valueOf(numberDec);
                        }catch (Exception exception){
                            numberDec = statsNumberString;
                            statsNumber = 0;
                        }

                        attrConfig.set(playerUUIDString+".Attributes_Stats."+attrStats,numberDec);
                        String coreattr = attrStatsConfig.getString(attrStatsFileName+"."+attrStats+".coreattr");
                        String inherit = attrStatsConfig.getString(attrStatsFileName+"."+attrStats+".inherit");
                        String operation = attrStatsConfig.getString(attrStatsFileName+"."+attrStats+".operation");
                        if(inherit != null && operation !=null){

                            new PlayerBukkitAttribute().addAttribute(player,inherit,operation,statsNumber,attrStats);
                        }else if(coreattr != null){
                            PlayerDataMap.getCore_Attribute_Map().get(playerUUIDString).setAttribute(coreattr,operation,statsNumber);
                        }
                    }
                }
                try {
                    attrConfig.save(attrFilePatch);
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        }
    }

}
