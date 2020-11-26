package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.util.ContentUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class SendActionBar {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public SendActionBar(Player player,String string){
        String[] strings1 = string.split("=");
        string = strings1[1].toLowerCase().replace("]","");
        string = new ContentUtil(string,player,"Character").getOutputString();
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(string));

    }
}
