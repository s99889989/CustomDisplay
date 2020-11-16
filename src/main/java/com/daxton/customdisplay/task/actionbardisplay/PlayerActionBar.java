package com.daxton.customdisplay.task.actionbardisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.listener.PackListener;
import com.daxton.customdisplay.util.ContentUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerActionBar extends PackListener{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private BukkitRunnable bukkitRunnable;

    private String finalString;

    public PlayerActionBar(Player p){
        finalString = "";
        bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                finalString = new ContentUtil(cd.getConfigManager().action_bar_content,p,"resource\\Character").getOutputString();
//                if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")){
//                    sendPacket(p,change_health_content2,ACTIONBAR,1,1,1);
//                }else {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(finalString));
//                }
            }
        };
        bukkitRunnable.runTaskTimer(cd,1,cd.getConfigManager().action_bar_refrsh);
    }
//    public void sendPacket(Player player, String text, EnumWrappers.TitleAction action, int fadeIn, int time, int fadeOut){
//        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
//
//        packet.getTitleActions().write(0, action);
//        packet.getChatComponents().write(0, WrappedChatComponent.fromText(text));
//        packet.getIntegers().write(0, fadeIn);
//        packet.getIntegers().write(1, time);
//        packet.getIntegers().write(2,fadeOut);
//        try{
//            pm.sendServerPacket(player, packet, false);
//        }catch (InvocationTargetException ex){
//            ex.printStackTrace();
//        }
//    }
    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable;
    }
}
