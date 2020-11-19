package com.daxton.customdisplay.task.titledisply;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class TitleOnTimer {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    BukkitRunnable bukkitRunnable;

    public TitleOnTimer(Player player,String string){
        int time = 0;
        player.sendMessage(string);
        String[] strings = string.split(":");
            if(strings.length == 2){
                player.sendMessage(""+strings[1]);
                time = Integer.valueOf(strings[1]);
            }


        

        bukkitRunnable =  new BukkitRunnable(){
            @Override
            public void run() {
                player.sendTitle("你","好",1,1,1);
            }
        };
        bukkitRunnable.runTaskTimer(cd,1,time);
    }

    public List<String> stringList(Player player){
        List<String> st = null;
        for(String configName : ConfigMapManager.getFileConfigurationNameMap().values()){
            if(configName.contains("Actions_")){
                FileConfiguration useConfig = ConfigMapManager.getFileConfigurationMap().get(configName);
                st = useConfig.getKeys(false).contains(player.getName()) ? useConfig.getStringList(player.getName()+".Action") : useConfig.getStringList("Default.Action");

            }
        }
        return st;
    }


}
