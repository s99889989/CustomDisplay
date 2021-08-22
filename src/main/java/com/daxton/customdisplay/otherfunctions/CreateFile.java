package com.daxton.customdisplay.otherfunctions;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;

import java.io.File;
import java.io.IOException;

public class CreateFile {

    public static void create(){
        File file = new File(CustomDisplay.getCustomDisplay().getDataFolder(),"/Json/Itme.yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException exception){
                exception.printStackTrace();
            }
        }
        if(file.exists()){
            FileConfiguration itmeConfig = YamlConfiguration.loadConfiguration(file);

//            for(Material material : Material.values()){
//                itmeConfig.set("Material."+material.getKey().getKey().toUpperCase(), material.getKey().getKey().replace("_"," "));
//            }

//            for(Sound sound : Sound.values()){
//                itmeConfig.set("Sound."+sound.name(), sound.name().replace("_"," ").toLowerCase());
//            }
            for(EntityType entity : EntityType.values()) {

                itmeConfig.set("Entity."+entity.name(), entity.name().replace("_"," ").toLowerCase());
            }

            try {
                itmeConfig.save(file);
            }catch (IOException exception){
                exception.printStackTrace();
            }




        }



    }

}
