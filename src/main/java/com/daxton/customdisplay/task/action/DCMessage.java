package com.daxton.customdisplay.task.action;

import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.DiscordManager;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.TextChannel;
import org.bukkit.entity.LivingEntity;

public class DCMessage {

    public DCMessage(){

    }

    public void setDCMessage(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        /**獲得內容**/
        String message = customLineConfig.getString(new String[]{"message","m"},"",self,target);

        long channelID = customLineConfig.getLong(new String[]{"channel","c"},1L,self,target);

        sendDCMessage(message, channelID);

    }

    public void sendDCMessage(String message,long channelID){

        TextChannel channel = (TextChannel) DiscordManager.client
                .getChannelById(Snowflake.of(channelID))
                .block();
        channel.createMessage(message).block();
    }

}
