package com.daxton.customdisplay.config;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.AutoConfig;
import com.daxton.customdisplay.api.config.AutoConfig2;
import com.daxton.customdisplay.manager.ConfigMapManager;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.auras.Aura;
import org.bukkit.configuration.file.FileConfiguration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigManager {

    CustomDisplay cd;

    public FileConfiguration config;

    public FileConfiguration language;


    public ConfigManager(CustomDisplay plugin){
        cd = plugin;

//        MythicLineConfig mythicLineConfig = null;
//        mythicLineConfig.getString(new String[]{"auranme"},null, new String[0]);


        config = new AutoConfig("resource/config.yml","config.yml").get();

        language = new AutoConfig("resource/Language/"+config.getString("Language")+".yml","Language/"+config.getString("Language")+".yml").get();

        File players_file = new File(cd.getDataFolder(),"Players");
        if(!players_file.exists()){
            players_file.mkdir();
        }
        File mobs_file = new File(cd.getDataFolder(),"Mobs");
        if(!mobs_file.exists()){
            mobs_file.mkdir();
        }
        File png_file = new File(cd.getDataFolder(),"Png");
        if(!png_file.exists()){
            png_file.mkdir();
        }
        new AutoConfig2();

        //ConfigMapManager.getFileConfigurationNameMap().forEach((s, s2) -> cd.getLogger().info(s));

    }



}
