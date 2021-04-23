package com.daxton.customdisplay.task.action2.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Map;

public class Title3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private LivingEntity self = null;
    private LivingEntity target = null;
    private Map<String, String> action_Map;
    private String taskID = "";

    public Title3(){

    }

    public void setTitle(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        this.self = self;
        this.target = target;
        this.action_Map = action_Map;
        this.taskID = taskID;
        setOther();
    }

    public void setOther(){

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        String title = actionMapHandle.getString(new String[]{"title","t"},"");

        String subTitle = actionMapHandle.getString(new String[]{"subtitle","s"},"");

        int fadeIn = actionMapHandle.getInt(new String[]{"fadein","fi"},10);

        int duration = actionMapHandle.getInt(new String[]{"duration","d"},70);

        int fadeOut = actionMapHandle.getInt(new String[]{"fadeout","fo"},70);

        if(self instanceof Player){
            Player player = (Player) self;
            sendTitle(player, title, subTitle, fadeIn, duration, fadeOut);
        }


    }


    public void sendTitle(Player player, String title, String subTitle, int fadeIn, int duration, int fadeOut){
        player.sendTitle(title,subTitle,fadeIn,duration,fadeOut);
    }


}
