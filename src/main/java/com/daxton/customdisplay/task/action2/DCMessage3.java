package com.daxton.customdisplay.task.action2;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.DiscordManager;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.TextChannel;
import org.bukkit.entity.LivingEntity;

import java.util.Map;

public class DCMessage3 {

    public DCMessage3(){

    }

    public void setDCMessage(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**獲得內容**/
        String message = actionMapHandle.getString(new String[]{"message","m"},"");

        long channelID = actionMapHandle.getLong(new String[]{"channel","c"},1L);

        sendDCMessage(message, channelID);

    }

    public void sendDCMessage(String message,long channelID){

        TextChannel channel = (TextChannel) DiscordManager.client
                .getChannelById(Snowflake.of(channelID))
                .block();
        channel.createMessage(message).block();
    }

}
