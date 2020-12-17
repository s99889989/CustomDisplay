package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static com.comphenix.protocol.wrappers.EnumWrappers.ChatType.CHAT;
import static com.comphenix.protocol.wrappers.EnumWrappers.ChatType.GAME_INFO;
import static com.comphenix.protocol.wrappers.EnumWrappers.TitleAction.ACTIONBAR;

public class ActionBar {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String message = "";

    private Player player = null;
    private LivingEntity self = null;
    private LivingEntity target = null;

    ProtocolManager pm = ProtocolLibrary.getProtocolManager();

    public ActionBar(Player player, String firstString){
        setActionBar(player,firstString);
    }



    public void setActionBar(Player player, String firstString){
        this.player = player;
        this.self = player;
        for(String allString : new StringFind().getStringMessageList(firstString)){
            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = new StringConversion2(self,target,strings[1],"Character").valueConv();
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

    public void Packet(){

        pm.addPacketListener(new PacketAdapter(PacketAdapter.params().plugin(cd).clientSide().serverSide().listenerPriority(ListenerPriority.NORMAL).gamePhase(GamePhase.PLAYING).optionAsync().options(ListenerOptions.SKIP_PLUGIN_VERIFIER).types(PacketType.Play.Server.CHAT)) {


            @Override
            public void onPacketSending(PacketEvent event) {

                Player player = event.getPlayer();

                PacketContainer packet = event.getPacket();
                PacketType packetType = event.getPacketType();

                if(packetType.equals(PacketType.Play.Server.CHAT)){
                    if(packet.getChatTypes().read(0) == CHAT){

                    }
                    if(packet.getChatTypes().read(0) == GAME_INFO){
                        boolean stats = ConfigMapManager.getFileConfigurationMap().get("config.yml").getBoolean("MMOcoreActionBarStats");
                        boolean spell = ConfigMapManager.getFileConfigurationMap().get("config.yml").getBoolean("MMOcoreActionBarSpell");
                        try{
                            ((Object) null).hashCode();
                        }catch (Exception exception){
                            BaseComponent[] b = ( BaseComponent[]) packet.getModifier().read(1);
                            StackTraceElement[] traces = exception.getStackTrace();
                            for(StackTraceElement trace : traces){
                                if(trace.getClassName().contains("mmocore.listener.SpellCast")){
                                    ActionManager.getMmocore_ActionBar_Spell_Map().put(player.getUniqueId(),BaseComponent.toLegacyText(b));
                                    event.setCancelled(spell);
                                    break;
                                }else if(trace.getClassName().contains("mmocore.api.PlayerActionBar")){
                                    ActionManager.getMmocore_ActionBar_Stats_Map().put(player.getUniqueId(),BaseComponent.toLegacyText(b));
                                    event.setCancelled(stats);
                                    break;
                                }else if(trace.getClassName().contains("customdisplay.PackListener") || trace.getClassName().contains("com.comphenix.protocol") || trace.getClassName().contains("NetworkManager") || trace.getClassName().contains("PlayerConnection") || trace.getClassName().contains("CraftPlayer") || trace.getClassName().contains("mmocore.api.player.PlayerData")){
                                    event.setCancelled(spell & stats);
                                    continue;
                                }else {
                                    //player.sendMessage("Other"+BaseComponent.toLegacyText(b));
                                    //cd.getLogger().info(trace.getClassName());
                                    //player.sendMessage(BaseComponent.toLegacyText(b));
                                }
                            }
                        }


                    }
                }

            }

        });

    }

}
