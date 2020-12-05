package com.daxton.customdisplay.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedParticle;
import com.daxton.customdisplay.CustomDisplay;

import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action.list.Message;
import com.daxton.customdisplay.task.action.list.SendParticles;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

import org.bukkit.event.Listener;


import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;

import static org.bukkit.Particle.DAMAGE_INDICATOR;


public class PackListener implements Listener{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ProtocolManager pm;

    public PackListener(){

            pm = ProtocolLibrary.getProtocolManager();
            pm.addPacketListener(new PacketAdapter(PacketAdapter.params().plugin(cd).clientSide().serverSide().listenerPriority(ListenerPriority.NORMAL).gamePhase(GamePhase.PLAYING).optionAsync().options(ListenerOptions.SKIP_PLUGIN_VERIFIER).types(PacketType.Play.Server.TITLE, PacketType.Play.Client.FLYING,PacketType.Play.Server.WORLD_PARTICLES)) {
                @Override
                public void onPacketReceiving(PacketEvent event) {
                    PacketContainer packet = event.getPacket();
                    PacketType packetType = event.getPacketType();
                    Player player = event.getPlayer();


                    if (packetType.equals(PacketType.Play.Client.FLYING)) {
                        //player.sendMessage("玩家移動");
                        //customDisplay.getLogger().info("玩家移動");
                        //sendPacket(player, "玩家移動", ACTIONBAR, 1, 1, 1);
//                    double x = packet.getDoubles().getValues().get(0);
//                    double y = packet.getDoubles().getValues().get(1);
//                    double z = packet.getDoubles().getValues().get(2);
//
//                    float yaw = packet.getFloat().getValues().get(0);
//                    float pitch = packet.getFloat().getValues().get(1);
//
//                    boolean onGround = packet.getBooleans().getValues().get(0);
//                    boolean hasPos = packet.getBooleans().getValues().get(1);
//                    boolean hasLook = packet.getBooleans().getValues().get(2);
//
//                    packet.getDoubles().write(0, Double.NaN);
//                    packet.getDoubles().write(1, Double.NaN);
//                    packet.getDoubles().write(2, Double.NaN);
//
//                    packet.getFloat().writeSafely(0, 0f);
//                    packet.getFloat().writeSafely(1, 0f);


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
                        pm.sendServerPacket(player, packet, false);
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onPacketSending(PacketEvent event) {
                    Player player = event.getPlayer();
                    PacketContainer packet = event.getPacket();
                    PacketType packetType = event.getPacketType();

//                    List particles = packet.getNewParticles().getValues();
//                    if(type == DAMAGE_INDICATOR){
//
//                        event.setCancelled(true);
//                        int index = particles.indexOf(DAMAGE_INDICATOR);
//                        int max = 10;
//                        int min = 1;
//                        if(false){
//                            Random randomno = new Random();
//                            int rand = randomno.nextInt(max - min +1)+min;
//                            packet.getIntegers().write(index,rand);
//                        }else {
//                            event.setCancelled(true);
//                        }
//
//                    }

                    if(packetType.equals(PacketType.Play.Server.WORLD_PARTICLES)){

                        Particle type = packet.getNewParticles().read(0).getParticle();
                        if(ActionManager.getJudgment_Message_Map().get(player.getName()) == null){
                            ActionManager.getJudgment_Message_Map().put(player.getName(),new Message());
                        }
                        if(ActionManager.getJudgment_Message_Map().get(player.getName()) != null){
                            ActionManager.getJudgment_Message_Map().get(player.getName()).setParticle(type);
                        }
                        if(ActionManager.getParticles_Map().get(player.getName()) == null){
                            ActionManager.getParticles_Map().put(player.getName(),new SendParticles());
                            event.setCancelled(ActionManager.getParticles_Map().get(player.getName()).getResult(type));
                        }else{
                            event.setCancelled(ActionManager.getParticles_Map().get(player.getName()).getResult(type));
                        }
                    }

                }

            });

    }


}
