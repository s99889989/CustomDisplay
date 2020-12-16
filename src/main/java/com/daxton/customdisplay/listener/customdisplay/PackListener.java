package com.daxton.customdisplay.listener.customdisplay;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.*;
import com.daxton.customdisplay.CustomDisplay;

import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.task.action.list.Message;
import com.daxton.customdisplay.task.action.list.SendParticles;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import org.bukkit.event.Listener;


import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.comphenix.protocol.wrappers.EnumWrappers.ChatType.*;
import static com.comphenix.protocol.wrappers.EnumWrappers.TitleAction.ACTIONBAR;


public class PackListener implements Listener{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ProtocolManager pm;

    private Table<UUID, String, String> entityName = HashBasedTable.create();

    public PackListener(){

            pm = ProtocolLibrary.getProtocolManager();
            pm.addPacketListener(new PacketAdapter(PacketAdapter.params().plugin(cd).clientSide().serverSide().listenerPriority(ListenerPriority.NORMAL).gamePhase(GamePhase.PLAYING).optionAsync().options(ListenerOptions.SKIP_PLUGIN_VERIFIER).types(PacketType.Play.Server.TITLE, PacketType.Play.Client.FLYING,PacketType.Play.Server.WORLD_PARTICLES,PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.ENTITY_METADATA, PacketType.Play.Server.CHAT)) {
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

                /**發送名稱數據包**/
                public void sendNamePacket(Player player, LivingEntity target) {
                    //player.sendMessage("怪物出生"+target.getName());
                    WrappedDataWatcher watcher = new WrappedDataWatcher();
                    watcher.setEntity(target);

                    WrappedDataWatcher.Serializer booleanSerializer = WrappedDataWatcher.Registry.get(Boolean.class);
                    WrappedDataWatcher.Serializer stringSerializer = WrappedDataWatcher.Registry.get(String.class);

                    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, stringSerializer), "喵喵犬");
                    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, booleanSerializer), true);

                    PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);

                    packet.getIntegers().write(0, target.getEntityId());
                    packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
                    for(Player p : Bukkit.getOnlinePlayers()){
                        try {
                            pm.sendServerPacket(p, packet, false);
                        } catch (InvocationTargetException ex) {
                            ex.printStackTrace();
                        }
                    }
//                    try {
//                        pm.sendServerPacket(player, packet, false);
//                    } catch (InvocationTargetException ex) {
//                        ex.printStackTrace();
//                    }
                }

                /**發送名稱數據包2**/
                public void sendNamePacket2(Player player, LivingEntity target) {
                    //player.sendMessage("怪物出生"+target.getName());
                    WrappedDataWatcher watcher = new WrappedDataWatcher();

                    WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(String.class);
                    WrappedDataWatcher.WrappedDataWatcherObject object = new WrappedDataWatcher.WrappedDataWatcherObject(2, serializer);
                    watcher.setObject(object, ComponentSerializer.parse("喵喵犬")[0].toLegacyText());
                    watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

                    PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);

                    packet.getIntegers().write(0, target.getEntityId());
                    packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
                    for(Player p : Bukkit.getOnlinePlayers()){
                        try {
                            pm.sendServerPacket(p, packet, false);
                        } catch (InvocationTargetException ex) {
                            ex.printStackTrace();
                        }
                    }

                }

                public void sendNamePacket3(Player player) {
                    Location loc = player.getLocation();
                    PacketContainer packet = new PacketContainer(PacketType.Play.Server.SPAWN_ENTITY);
                    packet.getModifier().writeDefaults();
                    int entityID = (int)(Math.random() * Integer.MAX_VALUE);
                    packet.getIntegers().write(0, entityID);
                    packet.getUUIDs().write(0, UUID.randomUUID());
                    packet.getEntityTypeModifier().write(0, EntityType.ARMOR_STAND);
                    packet.getDoubles().write(0, loc.getX());
                    packet.getDoubles().write(1, loc.getY());
                    packet.getDoubles().write(2, loc.getZ());

                    try {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                    PacketContainer packet2 = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
                    packet2.getModifier().writeDefaults();
                    packet2.getIntegers().write(0, entityID);

                    WrappedDataWatcher metadata = new WrappedDataWatcher();
                    Optional<?> opt = Optional.of(WrappedChatComponent.fromChatMessage("displayName")[0].getHandle());
                    metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt);
                    metadata.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true);

                    packet2.getWatchableCollectionModifier().write(0, metadata.getWatchableObjects());
                    try {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet2);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
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

                    if(packetType.equals(PacketType.Play.Server.CHAT)){
                        if(packet.getChatTypes().read(0) == CHAT){

                        }
                        if(packet.getChatTypes().read(0) == GAME_INFO){
                            try{
                                ((Object) null).hashCode();
                            }catch (Exception exception){
                                BaseComponent[] b = ( BaseComponent[]) packet.getModifier().read(1);
                                StackTraceElement[] traces = exception.getStackTrace();
                                for(StackTraceElement trace : traces){
                                    if(trace.getClassName().contains("mmocore.listener.SpellCast")){
                                        //cd.getLogger().info(BaseComponent.toLegacyText(b));
                                        //player.sendMessage("mmocore"+BaseComponent.toLegacyText(b));
                                        ActionManager.getMmocore_ActionBar_Spell_Map().put(player.getUniqueId(),BaseComponent.toLegacyText(b));
                                        event.setCancelled(true);
                                        break;
                                    }else if(trace.getClassName().contains("mmocore.api.PlayerActionBar")){
                                        //player.sendMessage("mmocore"+BaseComponent.toLegacyText(b));
                                        ActionManager.getMmocore_ActionBar_Stats_Map().put(player.getUniqueId(),BaseComponent.toLegacyText(b));
                                        event.setCancelled(true);
                                        break;
                                    }else if(trace.getClassName().contains("customdisplay.PackListener")){
                                        //player.sendMessage("Other"+BaseComponent.toLegacyText(b));
                                        event.setCancelled(true);
                                        continue;
                                    }else {
                                        //player.sendMessage("Other"+BaseComponent.toLegacyText(b));
                                        //cd.getLogger().info(trace.getClassName());
                                    }
                                }
                            }
                            //sendPacket(player, "String text", ACTIONBAR, 1, 1, 1);

                        }
                    }



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
                        }
                        if(ActionManager.getParticles_Map().get(player.getName()) != null){
                            boolean b = ActionManager.getParticles_Map().get(player.getName()).getResult(type);
                            if(type == ActionManager.getParticles_Map().get(player.getName()).getPutParticle()){
                                event.setCancelled(b);
                            }
                        }


                    }

                }

            });

    }


}
