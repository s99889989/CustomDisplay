package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.comphenix.protocol.wrappers.EnumWrappers.TitleAction.ACTIONBAR;

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
        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")){
            sendPacket(player, message, ACTIONBAR, 1, 1, 1);
        }else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
        }

    }

    public void sendPacket(Player player, String text, EnumWrappers.TitleAction action, int fadeIn, int time, int fadeOut) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);

        packet.getTitleActions().write(0, action);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(text));
        packet.getIntegers().write(0, fadeIn);
        packet.getIntegers().write(1, time);
        packet.getIntegers().write(2, fadeOut);
        try {
            ActionManager.protocolManager.sendServerPacket(player, packet, false);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

}
