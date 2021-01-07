package com.daxton.customdisplay.api.mob;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.PlaceholderManager;
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

    private Map<String,Integer> stats_Map = new HashMap<>();

    public MobConfig(){

    }

    /**建新檔**/
    public void createNewFile(String mobID){

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
            statsSec.getKeys(false).forEach((stats)->{stats_Map.put(stats,statsConfig.getInt(key+"."+stats+".base"));});
            statsList.addAll(statsSec.getKeys(false));
        });
        statsFinalList = statsList.stream().distinct().collect(Collectors.toList());
        if(mobConfig.getString(mobID+".Mob_Race") == null){
            mobConfig.set(mobID+".Mob_Level",mobDefaultConfig.getInt("Default_Mob.Mob_Level"));
            mobConfig.set(mobID+".Mob_Race",mobDefaultConfig.getString("Default_Mob.Mob_Race"));
            mobConfig.set(mobID+".Mob_Attribute",mobDefaultConfig.getString("Default_Mob.Mob_Attribute"));
            mobConfig.set(mobID+".Mob_Body",mobDefaultConfig.getString("Default_Mob.Mob_Body"));
            mobConfig.set(mobID+".Mob_Type",mobDefaultConfig.getString("Default_Mob.Mob_Type"));
            statsFinalList.forEach((stats)->{mobConfig.set(mobID+".Attributes_Stats."+stats,stats_Map.get(stats));});
            mobConfig.set(mobID+".Melee_physics_formula",mobDefaultConfig.getString("Default_Mob.Melee_physics_formula"));
            mobConfig.set(mobID+".Range_physics_formula",mobDefaultConfig.getString("Default_Mob.Range_physics_formula"));
        }
        try {
            mobConfig.save(mobFilePatch);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }



    public void setMobConfig(String mobID){
        File file = new File(cd.getDataFolder(),"Mobs/Attributes/Stats_Ro_Mob.yml");
        FileConfiguration mobConfig = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection mobSec = mobConfig.getConfigurationSection("Stats_Ro_Mob");
        for(String mob : mobSec.getKeys(false)){
            String value = mobConfig.getString("Stats_Ro_Mob."+mob+".base");
            cd.getLogger().info(mob + " : " +value);
            PlaceholderManager.getMythicMobs_Attr_Map().put(mobID+mob,value);
        }
    }

}
