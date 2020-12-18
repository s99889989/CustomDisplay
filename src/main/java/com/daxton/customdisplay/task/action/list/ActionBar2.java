package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager2;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static com.comphenix.protocol.wrappers.EnumWrappers.ChatType.GAME_INFO;
import static com.comphenix.protocol.wrappers.EnumWrappers.TitleAction.ACTIONBAR;

public class ActionBar2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player = null;
    private LivingEntity self = null;
    private LivingEntity target = null;
    private String firstString = "";
    private String taskID = "";

    private String message = "";
    private String function = "add";
    private String aims = "self";
    private List<String> classList = new ArrayList();

    public ActionBar2(){

    }

    public void setActionBar(LivingEntity self,LivingEntity target, String firstString,String taskID){
        this.self = self;
        this.target = target;
        this.firstString = firstString;
        this.taskID = taskID;
        setOther();

    }
    public void setOther(){

        for(String string : new StringFind().getStringList(firstString)){
            if(string.toLowerCase().contains("function=") || string.toLowerCase().contains("fc=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    function = strings[1];
                }
            }

            if(string.toLowerCase().contains("@=")){
                String[] strings = string.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
        }


        for(String allString : new StringFind().getStringMessageList(firstString)){

            if(allString.toLowerCase().contains("message=") || allString.toLowerCase().contains("m=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    message = new StringConversion2(self,target,strings[1],"Character").valueConv();
                }
            }

            if (allString.toLowerCase().contains("class=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    classList = new StringFind().getStringListClass(strings[1]);
                }
            }

        }

        if(target instanceof Player & aims.toLowerCase().contains("target")){
            player = (Player) target;
        }else {
            if(self instanceof Player){
                player = (Player) self;
            }
        }


        if(function.toLowerCase().contains("remove") || function.toLowerCase().contains("log")){
            if(ActionManager2.getSendParticles_ProtocolManager_Map().get(taskID) == null){
                ActionManager2.getSendParticles_ProtocolManager_Map().put(taskID,ProtocolLibrary.getProtocolManager());
            }
            if(ActionManager2.getSendParticles_ProtocolManager_Map().get(taskID) != null){
                Packet();
            }
        }else {
            if(player != null){
                sendActionBar();
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
            ActionManager2.protocolManager.sendServerPacket(player, packet, false);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    public void Packet(){

        ActionManager2.getSendParticles_ProtocolManager_Map().get(taskID).addPacketListener(new PacketAdapter(PacketAdapter.params().plugin(cd).clientSide().serverSide().listenerPriority(ListenerPriority.NORMAL).gamePhase(GamePhase.PLAYING).optionAsync().options(ListenerOptions.SKIP_PLUGIN_VERIFIER).types(PacketType.Play.Server.CHAT)) {

            @Override
            public void onPacketSending(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();
                PacketType packetType = event.getPacketType();

                if(packetType.equals(PacketType.Play.Server.CHAT)){
                    if(packet.getChatTypes().read(0) == GAME_INFO){
                        BaseComponent[] b = ( BaseComponent[]) packet.getModifier().read(1);
                        event.setCancelled(true);

                        try{
                            ((Object) null).hashCode();
                        }catch (Exception exception){
                            StackTraceElement[] traces = exception.getStackTrace();
                            List list = new ArrayList();

                            for(StackTraceElement trace : traces){
                                list.add(trace.getClassName());
                                if(function.toLowerCase().contains("log")){
                                    cd.getLogger().info(trace.getClassName());
                                    //ActionManager.getAction_Bar_Class_Map().put(player.getUniqueId(),trace.getClassName());
                                }

                            }
                            if(function.toLowerCase().contains("remove")){
                                if(classList.size() > 0){
                                    for(String mess : classList){
                                        if(list.contains(mess)){
                                            PlaceholderManager.getAction_Bar_Class_Map().put(mess,BaseComponent.toLegacyText(b));
                                            event.setCancelled(true);
                                        }
                                    }
                                }
                            }

                        }


                    }
                }

            }

        });

    }

}
