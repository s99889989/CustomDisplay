package com.daxton.customdisplay.task.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class Attacked{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private BukkitRunnable bukkitRunnable;

    public Attacked(Player player){
        List<String> stringList = stringList(player);
        player.sendMessage("顯示開始");
        int delay = 0;
        for(String st : stringList) {
            if (st.contains("delay ")) {
                String[] slt = st.split(" ");
                if (slt.length == 2) {
                    delay = delay+Integer.valueOf(slt[1]);
                }
            }
            new BukkitRunnable() {
                @Override
                public void run() {
                        player.sendMessage(st);
                        cancel();
                }
            }.runTaskLater(cd,delay);
        }

    }

    public List<String> stringList(Player player){
        List<String> st = null;
        for(String configName : ConfigMapManager.getFileConfigurationNameMap().values()){
            if(configName.contains("Players_")){
                FileConfiguration useConfig = ConfigMapManager.getFileConfigurationMap().get(configName);
                st = useConfig.getKeys(false).contains(player.getName()) ? useConfig.getStringList(player.getName()+".Action") : useConfig.getStringList("Default.Action");

            }
        }
        return st;
    }

}
