package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.util.List;

public class ActionBar {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String message = "";

    private Player player;

    public ActionBar(Player player, String firstString){
        setActionBar(player,firstString);
    }



    public void setActionBar(Player player, String firstString){
        this.player = player;
        List<String> stringList = new StringFind().getStringMessageList(firstString);
        for(String allString : stringList){
            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = new StringConversion().getString("Character",strings[1],player);
                    String ac = ActionManager.getActionBar_String_Map().get(player.getUniqueId());
                    if(ac != null){
                        message = message.replace("{cd_mmocore_actionbar}",ac);
                    }else {
                        message = message.replace("{cd_mmocore_actionbar}","");
                    }

                }
            }

        }

    }

    public void sendActionBar(){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }

}
