package com.daxton.customdisplay.util;

import com.daxton.customdisplay.CustomDisplay;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class PlaceholderUtil {

    private FileConfiguration action_bar_config;

    private String[] stringlist;

    private String outputString;

    private String count;

    private int i;

    private int j;

    private String path;

    public PlaceholderUtil(String inputString, ConfigurationSection configurationSection,FileConfiguration action_bar_config, Player p, String path1){
        this.action_bar_config = action_bar_config;
        stringlist = inputString.split(",");
        outputString = "";
        i = 0;
        for(String st : stringlist){
            if(st.contains("%")){
                i++;
                count = PlaceholderAPI.setPlaceholders(p, st);
                if(configurationSection.getKeys(false).size() >= i){
                    j = 0;
                    path = "";
                    for(String key : configurationSection.getKeys(false)){
                        j++;
                        path = path1 + key;
                        List<String> countlist = this.action_bar_config.getStringList(path);
                        if(i == j){
                            for(String slist : countlist){
                                String[] r1 = slist.split(",");
                                count = count.replaceAll(r1[0], r1[1]);
                            }
                            break;
                        }
                    }
                }
            } else {
              count = st;
            }
            outputString = outputString + count;
        }
        //return outputString;
    }

    public String getOutputString() {
        return outputString;
    }
}
