package com.daxton.customdisplay.task.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.SendActionBar;
import com.daxton.customdisplay.api.action.SendTitle;
import com.daxton.customdisplay.api.action.Sound;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerDataMap;
import com.daxton.customdisplay.util.ContentUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OnTimer {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private BukkitRunnable bukkitRunnable;


    public OnTimer(Player player,String string){
        UUID uuid = player.getUniqueId();
        bukkitRunnable = new BukkitRunnable() {
                @Override
                public void run() {

                    int delay = 0;
                    for (String string : getActionList(string,uuid)) {

                        if (string.contains("Condition")) {
                            String string1 = string.replace("Condition[","").replace("condition[","").replace("]","");
                            if(string1.contains("<")){
                                String[] strings1 = string1.split("<");
                                strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
                                if(!(Double.valueOf(strings1[0]) < Double.valueOf(strings1[1]))){
                                    break;
                                }
                            }
                            if(string1.contains(">")){
                                String[] strings1 = string1.split(">");
                                strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
                                if(!(Double.valueOf(strings1[0]) > Double.valueOf(strings1[1]))){
                                    break;
                                }
                            }
                            if(string1.contains("=")){
                                String[] strings1 = string1.split("=");
                                strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
                                if(!(Double.valueOf(strings1[0]).equals(Double.valueOf(strings1[1])))){
                                    break;
                                }
                            }

                        }

                        if (string.toLowerCase().contains("delay ")) {
                            String[] slt = string.split(" ");
                            if (slt.length == 2) {
                                delay = delay + Integer.valueOf(slt[1]);
                            }
                        }
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (string.toLowerCase().contains("sendtitle")) {
                                    new SendTitle(player, string).sendTitle();
                                }
                                if (string.toLowerCase().contains("sound")) {
                                    new Sound(player, string).playSound();
                                }

                                if (string.toLowerCase().contains("sendactionbar")) {
                                    new SendActionBar(player, string);
                                }
                                cancel();
                            }
                        }.runTaskLater(cd, delay);
                    }
                }
        };
        bukkitRunnable.runTaskTimer(cd,1,getTime(string));
    }

    public int getTime(String string){
        String[] strings1 = string.split(":");
        int time = Integer.valueOf(strings1[1]);
        return time;
    }

    public List<String> getActionList(String string,UUID uuid){
        List<String> stringList;
        String[] strings1 = string.split("]");
        String[] strings2 = strings1[0].split("=");
        PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(uuid);
        stringList = playerData.getActionListMap().get(strings2[1]);
        return stringList;
    }

    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable;
    }
}
