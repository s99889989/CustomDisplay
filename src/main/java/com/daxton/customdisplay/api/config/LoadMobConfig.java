package com.daxton.customdisplay.api.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.MobData;
import com.daxton.customdisplay.config.ConfigManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.MobManager;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoadMobConfig {

    public static void setMob(){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        File file1 = new File("plugins/MythicMobs/Mobs/TestMobs.yml");

        File mmFile = new File("plugins/MythicMobs/Mobs");
        String[] mmFileArray = mmFile.list();
        if(mmFileArray != null){
            for(String mmFileKey : mmFileArray){
                if(mmFileKey.contains(".yml")){
                    File loadFile = new File("plugins/MythicMobs/Mobs/"+mmFileKey);
                    File createFile = new File(cd.getDataFolder(),"Mobs/"+mmFileKey);
                    if(!createFile.exists()){
                        try {
                            createFile.createNewFile();
                        }catch (IOException exception){
                            //
                        }
                    }
                    if(createFile.exists()){
                        setConfig(loadFile, createFile);
                    }
                }else if(!mmFileKey.contains(".")){
                    File createFile = new File(cd.getDataFolder(),"Mobs/"+mmFileKey);
                    if(!createFile.exists()){
                        createFile.mkdir();
                    }
                    if(createFile.exists()){
                        File mmFile1 = new File("plugins/MythicMobs/Mobs/"+mmFileKey);
                        String[] mmFile1Array = mmFile1.list();
                        if(mmFile1Array != null){
                            for(String mmFile1Key : mmFile1Array){
                                if(mmFile1Key.contains(".yml")){
                                    File loadFile1 = new File("plugins/MythicMobs/Mobs/"+mmFileKey+"/"+mmFile1Key);
                                    File createFile1 = new File(cd.getDataFolder(),"Mobs/"+mmFileKey+"/"+mmFile1Key);
                                    if(!createFile1.exists()){
                                        try {
                                            createFile1.createNewFile();
                                        }catch (IOException exception){
                                            //
                                        }
                                    }
                                    if(createFile1.exists()){
                                        setConfig(loadFile1, createFile1);
                                    }
                                }

                            }
                        }
                    }
                }
                //cd.getLogger().info(mmFileKey);
            }
            //FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file1);
        }


    }

    public static void setConfig(File load, File out){
        FileConfiguration loadConfig = YamlConfiguration.loadConfiguration(load);
        FileConfiguration saveConfig = YamlConfiguration.loadConfiguration(out);
        FileConfiguration mobDefaultConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Main_Default_Mob.yml");
        String patch = out.toString().replace("plugins\\CustomDisplay\\","").replace("\\","_");
        loadConfig.getConfigurationSection("").getKeys(false).forEach(s -> {

            MobManager.mythicMobs_mobFile_Map.put(s, patch);
            //CustomDisplay.getCustomDisplay().getLogger().info(patch);
            if(loadConfig.contains(s+".Type")){
                String type = loadConfig.getString(s+".Type");
                saveConfig.set(s+".Type", type);
            }

            if(loadConfig.contains(s+".Display")){
                String display = loadConfig.getString(s+".Display");
                saveConfig.set(s+".Display", display);
            }

            if(loadConfig.contains(s+".Faction")){
                String faction = loadConfig.getString(s+".Faction");
                saveConfig.set(s+".Faction", faction);
            }
            mobDefaultConfig.getConfigurationSection("Default_Mob").getKeys(false).forEach(ss -> {

                if(!saveConfig.contains(s+".Custom."+ss)){
                    saveConfig.set(s+".Custom."+ss, mobDefaultConfig.get("Default_Mob."+ss));
                }

            });
        });

        try {
            saveConfig.save(out);
        }catch (IOException exception){
            //
        }


    }

    public static void setMobData(){

        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        List<FileConfiguration> mobConfigList = Config.getTypeConfigList("Mobs_");
        mobConfigList.forEach(mobConfig -> {

            mobConfig.getConfigurationSection("").getKeys(false).forEach(mobID -> {
                //cd.getLogger().info("魔物名稱: "+mobID);
                MobManager.mob_Data_Map.put(mobID, new MobData(mobID, mobConfig));
            });

        });


    }

}
