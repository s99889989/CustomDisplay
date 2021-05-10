package com.daxton.customdisplay.task.action2.entity;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.DiscordManager;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.TextChannel;
import org.bukkit.entity.LivingEntity;

import java.util.List;
import java.util.Map;

public class DCMessage3 {

    public DCMessage3(){

    }

    public static void setDCMessage(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        long channelID = actionMapHandle.getLong(new String[]{"channel","c"},1L);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListTarget();
        livingEntityList.forEach(livingEntity -> {

            ActionMapHandle actionMapHandle2 = new ActionMapHandle(action_Map, self, livingEntity);
            /**獲得內容**/

            String message = actionMapHandle2.getString(new String[]{"message","m"},"");

            sendDCMessage(message, channelID);
        });



    }

    public static void sendDCMessage(String message,long channelID){

        TextChannel channel = (TextChannel) DiscordManager.client
                .getChannelById(Snowflake.of(channelID))
                .block();
        channel.createMessage(message).block();
    }

}
