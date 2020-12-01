package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ActionBar {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String message = "";

    private Player player;

    public ActionBar(Player player, String firstString){
        setActionBar(player,firstString);
    }

    public void sendActionBar(){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

    public void setActionBar(Player player, String firstString){
        this.player = player;
        List<String> stringList = new StringFind().getStringList(firstString);
        for(String allString : stringList){
            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = new StringConversion().getString("Character",strings[1],player);
                }
            }

        }


    }

}
