package com.daxton.customdisplay.util;

import com.daxton.customdisplay.CustomDisplay;
import com.google.common.base.Charsets;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class ConfigUtil {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private FileConfiguration config;

    private File configFile;

    private String fileName;

    public ConfigUtil(String fileName){
        this.fileName = fileName;

            configFile = new File(cd.getDataFolder(), this.fileName);

        if (!configFile.exists()){
            cd.saveResource(this.fileName, false);
        }
    }

    public FileConfiguration get(){
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        return config;

    }
}
