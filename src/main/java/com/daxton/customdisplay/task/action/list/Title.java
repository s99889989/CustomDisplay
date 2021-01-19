package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Title {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private String title = "";
    private String subTitle = "";
    private int fadeIn = 1;
    private int stay = 1;
    private int fadeOut = 1;
    private String aims = "self";

    public Title(){

    }

    public void setTitle(LivingEntity self,LivingEntity target, String firstString,String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){
        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("fadein=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try{
                        fadeIn = Integer.valueOf(strings[1]);
                    }catch (NumberFormatException exception){

                    }
                }
            }
            if(string.toLowerCase().contains("stay=")){
                String[] strings= string.split("=");
                if(strings.length == 2){
                    try{
                        stay = Integer.valueOf(strings[1]);
                    }catch (NumberFormatException exception){

                    }
                }


            }
            if(string.toLowerCase().contains("fadeout=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    try{
                        fadeOut = Integer.valueOf(strings[1]);
                    }catch (NumberFormatException exception){

                    }
                }
            }
            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
        }

        for(String string : new StringFind().getStringMessageList(firstString)){
            if(string.toLowerCase().contains("title=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    if(strings.length == 2){
                        title = new ConversionMain().valueOf(self,target,strings[1]);
                    }
                }
            }

            if(string.toLowerCase().contains("subtitle=") || string.toLowerCase().contains("subt=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    subTitle = new ConversionMain().valueOf(self,target,strings[1]);
                }

            }

        }

        if(target instanceof Player & aims.toLowerCase().contains("target")){
            player = (Player) target;
        }else {
            if(self instanceof Player){
                player = (Player) self;
            }
        }
        if(player != null){
            sendTitle();
        }


    }


    public void sendTitle(){
        player.sendTitle(title,subTitle,fadeIn,stay,fadeOut);
    }


}
