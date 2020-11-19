package com.daxton.customdisplay.task.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class ActionDisplay {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String action ="";

    private String trigger ="";

    private String title ="";

    private String subTitle ="";

    private int fadeIn = 1;

    private int stay = 1;

    private int fadeOut = 1;


    public ActionDisplay(Player player){


        for(String configName : ConfigMapManager.getFileConfigurationNameMap().values()){
            if(configName.contains("Players_")){
                FileConfiguration useConfig = ConfigMapManager.getFileConfigurationMap().get(configName);
                List<String> st = useConfig.getKeys(false).contains(player.getName()) ? useConfig.getStringList(player.getName()+".Action") : useConfig.getStringList("Default.Action");
                for(String stringList : st){
                    if(stringList.contains("action=sendTitle")){
                        setActionTitle(stringList);
                    }

                }
            }
        }

    }
    public void setActionTitle(String string){
            String[] list = string.split(";");
            for(String stringList : list){
                if(stringList.contains("action=sendTitle")){
                    String[] stl = stringList.split("=");
                    setAction(stl[1]);
                }
                if(stringList.contains("trigger=attacked")){
                    String[] stl = stringList.split("=");
                    setTrigger(stl[1]);
                    cd.getLogger().info("trigger="+stl.length);
                }
                if(stringList.contains("title=")){
                    String[] stl = stringList.split("=");
                    cd.getLogger().info("title="+stl.length);
//                    if(stl[1] == null){
//                        setTitle("");
//                    }
                    if(stl.length == 2){
                        setTitle(stl[1]);
                    }
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
    public void sendTitle(Player player){
        player.sendTitle(title,subTitle,fadeIn,stay,fadeOut);
    }

    public String getAction() {
        return action;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setAction(String action) {
        this.action = action;
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

    public void setFadeIn(int fadeIn) {
        this.fadeIn = fadeIn;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }

    public void setFadeOut(int fadeOut) {
        this.fadeOut = fadeOut;
    }


}
