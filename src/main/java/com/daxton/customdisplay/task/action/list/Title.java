package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class Title {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target = null;

    private String messageTarge = "self";

    private String title = "";

    private String subTitle = "";

    private int fadeIn = 1;

    private int stay = 1;

    private int fadeOut = 1;


    public Title(Player player, String firstString){
        this.player = player;
        messageTarge = "self";
        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("messagetarge=") || string.toLowerCase().contains("mt=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    if(strings[1].toLowerCase().contains("target")){
                        messageTarge = "target";
                    }else {
                        messageTarge = "self";
                    }
                }
            }
        }

        for(String strings : new StringFind().getStringList(firstString)){
            if(strings.contains("fadeIn=")){
                String[] stl = strings.split("=");
                setFadeIn(Integer.valueOf(stl[1]));
            }
            if(strings.contains("stay=")){
                String[] stl = strings.split("=");
                setStay(Integer.valueOf(stl[1]));
            }
            if(strings.contains("fadeOut=")){
                String[] stl = strings.split("=");
                setFadeOut(Integer.valueOf(stl[1]));
            }
        }


        for(String string : new StringFind().getStringMessageList(firstString)){
            if(string.toLowerCase().contains("title=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    if(strings.length == 2){
                        if(messageTarge.toLowerCase().contains("target")){
                            setTitle(new StringConversion("Character",strings[1],target).getResultString());
                        }else {
                            setTitle(new StringConversion("Character",strings[1],player).getResultString());
                        }
                    }

                }
            }

            if(string.contains("subtitle=") || string.contains("subt=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    if(messageTarge.toLowerCase().contains("target")){
                        setSubTitle(new StringConversion("Character",strings[1],target).getResultString());
                    }else {
                        setSubTitle(new StringConversion("Character",strings[1],player).getResultString());
                    }
                }

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
