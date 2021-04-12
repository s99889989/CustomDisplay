package com.daxton.customdisplay.task.action.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.comphenix.protocol.wrappers.EnumWrappers.TitleAction.ACTIONBAR;

public class ActionBar2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ActionBar2(){

    }

    public void setActionBar(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        boolean remove = customLineConfig.getBoolean(new String[]{"remove"}, false,self,target);

        String message = customLineConfig.getString(new String[]{"message","m"},"",self,target);

        List<LivingEntity> livingEntityList = customLineConfig.getLivingEntityList(self,target);

        livingEntityList.forEach(livingEntity -> {
            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;

                PlaceholderManager.getActionBar_function().put(player.getUniqueId().toString(),remove);
                sendActionBar(player,message);
            }
        });

    }


    public void sendActionBar(Player player,String message){

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
