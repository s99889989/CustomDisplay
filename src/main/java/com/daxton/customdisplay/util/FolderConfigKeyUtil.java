package com.daxton.customdisplay.util;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.player.PlayerConfigMap;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public class FolderConfigKeyUtil {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String trigger;

    private String title;

    private String subTitle;

    private int fadeIn;

    private int stay;

    private int fadeOut;

    public FolderConfigKeyUtil(String folder, Player player){
        File folderPath = new File(cd.getDataFolder(),folder);
        String[] configList = folderPath.list();
        for(String config :configList){
            File fileConfig = new File(cd.getDataFolder(),folder+"\\"+config);
            FileConfiguration useConfig = YamlConfiguration.loadConfiguration(fileConfig);
            PlayerConfigMap.getStringStringMap().put(config,useConfig);
            List<String> st = useConfig.getKeys(false).contains(player.getName()) ? useConfig.getStringList(player.getName()+".Action") : useConfig.getStringList("Default.Action");
            for(String string : st){
               String[] list = string.split(";");
               for(String stringList : list){
                   if(stringList.contains("title=")){
                       String[] stl = stringList.split("=");
                       setTitle(stl[1]);
                   }
                   if(stringList.contains("subtitle=")){
                       String[] stl = stringList.split("=");
                       setSubTitle(stl[1]);
                   }
                   if(stringList.contains("fadeIn=")){
                       String[] stl = stringList.split("=");
                       setFadeIn(Integer.valueOf(stl[1]));
                   }
                   if(stringList.contains("stay=")){
                       String[] stl = stringList.split("=");
                       setStay(Integer.valueOf(stl[1]));
                   }
                   if(stringList.contains("fadeOut=")){
                       String[] stl = stringList.split("=");
                       setFadeOut(Integer.valueOf(stl[1]));
                   }
               }
            }
            //            ConfigurationSection configSection = useConfig.getConfigurationSection("");
//            for (String keySection : configSection.getKeys(false)) {
//                player.sendMessage(keySection);
//            }
        }

    }

    public void titleShow(Player player){
        player.sendTitle(title,subTitle,fadeIn,stay,fadeOut);
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setFadeIn(int fadeIN) {
        this.fadeIn = fadeIn;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }

    public void setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
    }
}
