package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;

public class ActionBar {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String message = "";
    private String messageTarge = "self";

    private Player player;
    private LivingEntity target = null;

    public ActionBar(Player player, String firstString){
        setActionBar(player,firstString);
    }



    public void setActionBar(Player player, String firstString){
        this.player = player;
        messageTarge = "self";
        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("messagetarge=") || string.toLowerCase().contains("mt=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    if(strings[1].toLowerCase().contains("target")){
                        messageTarge = "target";
                    }else {
                        messageTarge = "self";
                    }
                }
            }
        }


        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    if(messageTarge.toLowerCase().contains("target")){
                        message = new StringConversion("Character",strings[1],target).getResultString();
                    }else {
                        message = new StringConversion("Character",strings[1],player).getResultString();
                    }
                }
            }

        }

    }

    public void sendActionBar(){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

}
