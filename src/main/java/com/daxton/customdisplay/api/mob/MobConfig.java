package com.daxton.customdisplay.api.mob;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MobConfig {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Map<String,String> stats_Map = new HashMap<>();

    public MobConfig(){

    }

    public void setMod(ActiveMob activeMob){
        String mobID = activeMob.getMobType();
        String uuidString = activeMob.getUniqueId().toString();
        String mobLevel = String.valueOf(activeMob.getLevel());

        /**用UUID字串儲存MMID**/
        MobManager.mythicMobs_mobID_Map.put(uuidString,mobID);
        /**用UUID字串儲存MMID**/
        MobManager.mythicMobs_Level_Map.put(uuidString,mobLevel);

        File mobFilePatch = new File(cd.getDataFolder(),"Mobs/"+mobID+".yml");
        FileConfiguration mobConfig;


        if(!mobFilePatch.exists()){
            createNewConfig(mobID);
            mobConfig = YamlConfiguration.loadConfiguration(mobFilePatch);
        }else {
            mobConfig = YamlConfiguration.loadConfiguration(mobFilePatch);
        }

        mobConfig.getConfigurationSection(mobID+".Attributes_Stats").getKeys(false).forEach(s -> {
            String value = mobConfig.getString(mobID+".Attributes_Stats."+s);
            MobManager.mythicMobs_Attr_Map.put(mobID+"."+s,value);
        });






    }

    public void createNewConfig(String mobID){
        File mobFilePatch = new File(cd.getDataFolder(),"Mobs/"+mobID+".yml");
        try {
            if(!mobFilePatch.exists()){
                mobFilePatch.createNewFile();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        FileConfiguration mobConfig = YamlConfiguration.loadConfiguration(mobFilePatch);

        File mobDefaultPathc = new File(cd.getDataFolder(),"Class/Main/Default_Mob.yml");
        FileConfiguration mobDefaultConfig = YamlConfiguration.loadConfiguration(mobDefaultPathc);
        List<String> statsList = new ArrayList<>();
        List<String> statsFinalList = new ArrayList<>();
        mobDefaultConfig.getStringList("Default_Mob.Attributes_Stats").forEach((key)->{
            File statsFile = new File(cd.getDataFolder(),"Class/Attributes/EntityStats/"+key+".yml");
            FileConfiguration statsConfig = YamlConfiguration.loadConfiguration(statsFile);
            ConfigurationSection statsSec = statsConfig.getConfigurationSection(key);
            statsSec.getKeys(false).forEach((stats)->{stats_Map.put(stats,statsConfig.getString(key+"."+stats+".base"));});
            statsList.addAll(statsSec.getKeys(false));
        });
        statsFinalList = statsList.stream().distinct().collect(Collectors.toList());
        if(mobConfig.getString(mobID+".Mob_Race") == null){
            statsFinalList.forEach((stats)->{mobConfig.set(mobID+".Attributes_Stats."+stats,stats_Map.get(stats));});
        }
        try {
            mobConfig.save(mobFilePatch);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }



}