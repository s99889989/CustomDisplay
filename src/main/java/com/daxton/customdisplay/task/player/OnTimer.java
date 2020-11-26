package com.daxton.customdisplay.task.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.task.action.list.SendActionBar;
import com.daxton.customdisplay.task.action.list.SendTitle;
import com.daxton.customdisplay.task.action.list.Sound;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.util.ContentUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class OnTimer {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private String firstString;

    private int time = 1;

    private List<String> actionList = new ArrayList<>();

    private BukkitRunnable bukkitRunnable1;

    private BukkitRunnable bukkitRunnable2;

    public OnTimer(Player player,String firstString){
        this.player = player;
        this.firstString = firstString;
        setAciotnList();
        startAction();

    }


    public void startAction(){

        bukkitRunnable1 = new BukkitRunnable() {
            @Override
            public void run() {
                int delay = 0;
                next:
                for(String string : actionList){
                    /**判斷條件**/
                    if (string.toLowerCase().contains("condition")) {
                        if(condition(string)){
                            break next;
                        }
                    }

                    if (string.toLowerCase().contains("delay ")) {
                        String[] slt = string.split(" ");
                        if (slt.length == 2) {
                            delay = delay + Integer.valueOf(slt[1]);
                        }
                    }
                    bukkitRunnable2 = new BukkitRunnable() {
                        @Override
                        public void run() {

                            task(string);

                        }
                    };
                    bukkitRunnable2.runTaskLater(cd, delay);

                }

            }
        };
        bukkitRunnable1.runTaskTimer(cd,1,time);

    }

    public void task(String string){
        if (string.toLowerCase().contains("sendtitle")) {
            new SendTitle(player, string).sendTitle();
            bukkitRunnable2.cancel();
        }
        if (string.toLowerCase().contains("sound")) {
            new Sound(player, string).playSound();
            bukkitRunnable2.cancel();
        }

        if (string.toLowerCase().contains("sendactionbar")) {
            new SendActionBar(player, string);
            bukkitRunnable2.cancel();
        }


    }

    /**條件判斷**/
    public boolean condition(String string){
        boolean b = false;
        String string1 = string.replace("Condition[","").replace("condition[","").replace("]","");

        if(string1.contains("<")){
            String[] strings1 = string1.split("<");

            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]) < Double.valueOf(strings1[1]))){
                b = true;
            }
        }
        if(string1.contains(">")){
            String[] strings1 = string1.split(">");
            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]) > Double.valueOf(strings1[1]))){
                b = true;
            }
        }
        if(string1.contains("=")){
            String[] strings1 = string1.split("=");
            strings1[0] = new ContentUtil(strings1[0],player,"Character").getOutputString();
            if(!(Double.valueOf(strings1[0]).equals(Double.valueOf(strings1[1])))){
                b = true;
            }
        }

        return b;
    }

    /**設定動作列表**/
    public void setAciotnList(){
        List<String> list = new ArrayList<>();
        String key = "";
        StringTokenizer stringTokenizer = new StringTokenizer(firstString,"[,] ");
        while (stringTokenizer.hasMoreElements()){
            list.add(stringTokenizer.nextToken());
        }

        for(String string : list){
            if(string.toLowerCase().contains("a=")){
                String[] strings1 = string.split("=");
                key = strings1[1];
            }

            if(string.toLowerCase().contains("~ontimer=")){
                String[] strings1 = string.split("=");
                time = Integer.valueOf(strings1[1]);

            }

        }

        for(String string1 : ConfigMapManager.getFileConfigurationNameMap().values()){
            if(string1.contains("Actions_")){
                FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get(string1);
                if(fileConfiguration.getKeys(false).contains(key)){
                    actionList = fileConfiguration.getStringList(key+".Action");
                }
            }
        }
    }

    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable1;
    }
}
