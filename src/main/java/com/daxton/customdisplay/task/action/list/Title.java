package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.entity.Player;

public class Title {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private String title = "";

    private String subTitle = "";

    private int fadeIn = 1;

    private int stay = 1;

    private int fadeOut = 1;


    public Title(Player player, String string){
        this.player = player;
        string = string.toLowerCase().replace("sendtitle[","").replace("]","");
        String[] st1 = string.split(";");
        for(String stringList : st1){
            if(stringList.contains("title=")){
                String[] stl = stringList.split("=");
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



    public void sendTitle(){
        player.sendTitle(title,subTitle,fadeIn,stay,fadeOut);
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
