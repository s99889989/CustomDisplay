package com.daxton.customdisplay.task.titledisply;

import org.bukkit.entity.Player;

public class CustomTitle {

    public CustomTitle(Player player){
        player.sendTitle("","",1,1,1);
    }

}
