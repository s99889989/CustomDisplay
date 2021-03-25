package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.other.StringFind;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class Title {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

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


        /**獲得目標**/
        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);
        if(!(targetList.isEmpty())){
            for (LivingEntity livingEntity : targetList){
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;
                    String title = new SetValue(player,target,firstString,"[];","","title=").getString();

                    String subTitle = new SetValue(player,target,firstString,"[];","","subtitle=").getString();

                    int fadeIn = new SetValue(player,target,firstString,"[];","10","title=").getInt(10);

                    int duration = new SetValue(player,target,firstString,"[];","70","duration=").getInt(70);

                    int fadeOut = new SetValue(player,target,firstString,"[];","70","fadeout=").getInt(20);
                    sendTitle(player, title, subTitle, fadeIn, duration, fadeOut);
                }
            }
        }

    }


    public void sendTitle(Player player, String title, String subTitle, int fadeIn, int duration, int fadeOut){
        player.sendTitle(title,subTitle,fadeIn,duration,fadeOut);
    }


}
