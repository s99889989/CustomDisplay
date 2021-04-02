package com.daxton.customdisplay.task.action.list;

import org.bukkit.entity.LivingEntity;

public class Teleport {

    public Teleport(){

    }

    public void setTp(LivingEntity self, LivingEntity target, String firstString, String taskID){

        self.teleport(target.getLocation());
    }

}
