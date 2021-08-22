package com.daxton.customdisplay.listener.protocollib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.daxton.customdisplay.CustomDisplay;

import com.daxton.customdisplay.manager.PlaceholderManager;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.comphenix.protocol.wrappers.EnumWrappers.ChatType.GAME_INFO;


public class PackListener implements Listener{

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public ProtocolManager pm;

    public PackListener(){

            pm = ProtocolLibrary.getProtocolManager();
            pm.addPacketListener(new PacketAdapter(PacketAdapter.params().plugin(cd).clientSide().serverSide().listenerPriority(ListenerPriority.NORMAL).gamePhase(GamePhase.PLAYING).optionAsync().options(ListenerOptions.SKIP_PLUGIN_VERIFIER).types(PacketType.Play.Server.TITLE ,PacketType.Play.Server.WORLD_PARTICLES,PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.ENTITY_METADATA, PacketType.Play.Server.CHAT, PacketType.Play.Server.ANIMATION, PacketType.Play.Server.LIGHT_UPDATE)) {
                @Override
                public void onPacketReceiving(PacketEvent event) {
                    PacketContainer packet = event.getPacket();
                    PacketType packetType = event.getPacketType();
                    Player player = event.getPlayer();

                }


                @Override
                public void onPacketSending(PacketEvent event) {
                    Player player = event.getPlayer();

                    PacketContainer packet = event.getPacket();
                    PacketType packetType = event.getPacketType();

                    if(packetType.equals(PacketType.Play.Server.CHAT)){
                        if(packet.getChatTypes().read(0) == GAME_INFO){
//                            BaseComponent[] b = ( BaseComponent[]) packet.getModifier().read(1);
//                            PlaceholderManager.getMmocore_ActionBar_Spell_Map().put(player.getUniqueId(),BaseComponent.toLegacyText(b));
                            String uuidString = player.getUniqueId().toString();
                            if(PlaceholderManager.getActionBar_function().get(uuidString) == null){
                                PlaceholderManager.getActionBar_function().put(uuidString,false);
                            }else {
                                event.setCancelled(PlaceholderManager.getActionBar_function().get(uuidString));
                            }

//                            try{
//                                ((Object) null).hashCode();
//                            }catch (Exception exception){
//                                StackTraceElement[] traces = exception.getStackTrace();
//                                for(StackTraceElement trace : traces){
//                                    cd.getLogger().info(trace.getClassName());
//                                }
//                            }

                        }
                    }

//                    if(packetType.equals(PacketType.Play.Server.LIGHT_UPDATE)){
//                        int x0 =  packet.getIntegers().read(0);
//                        int x1 =  packet.getIntegers().read(1);
//                        int x2 =  packet.getIntegers().read(2);
//                        int x3 =  packet.getIntegers().read(3);
//                        int x4 =  packet.getIntegers().read(4);
//                        int x5 =  packet.getIntegers().read(5);
//                        boolean b = packet.getBooleans().read(0);
//
//
//
//                        player.sendMessage("收到光"+b+":"+x0+" : "+x1+" : "+x2+" : "+x3+" : "+x4+" : "+x5);
//
//                    }

//                    if(packetType.equals(PacketType.Play.Server.ENTITY_METADATA)){
//                        List<WrappedWatchableObject> wrappedWatchableObjectList = packet.getWatchableCollectionModifier().read(0);
//                        wrappedWatchableObjectList.forEach(wrappedWatchableObject -> {
//                            int i = wrappedWatchableObject.getIndex();
//                            if(i == 2){
//
//                                Object value = wrappedWatchableObject.getValue();
//                                if(!value.toString().equals("Optional.empty")){
//
//                                    Optional<?> opt = (Optional<?>) value;
//                                    String oo = opt.toString();
//                                    int start = oo.indexOf("s=[TextComponent{text='")+23;
//                                    int end = oo.indexOf("',", start);
//                                    String name = oo.substring(start, end);
//                                    //cd.getLogger().info(name);
//                                    if(name.startsWith("CustomDisplay")){
//                                        name = name.replace("CustomDisplay","");
//                                        player.sendMessage(name);
//                                    }else {
//                                        event.setCancelled(true);
//
//                                    }
//
//                                }
//
//
//                            }
//
//                        });
//
//                    }

                    if(packetType.equals(PacketType.Play.Server.WORLD_PARTICLES)){
                        Particle type = packet.getNewParticles().read(0).getParticle();
                        String uuidString = player.getUniqueId().toString();

                        if(PlaceholderManager.getParticles_function().get(uuidString+"function") != null){
                            String function = PlaceholderManager.getParticles_function().get(uuidString+"function");
                            if(function.toLowerCase().contains("remove")){
                                if(PlaceholderManager.getParticles_function().get(uuidString+"particle") != null){
                                    String particle = PlaceholderManager.getParticles_function().get(uuidString+"particle");
                                    if(type.toString().toLowerCase().contains(particle)){
                                        event.setCancelled(true);
                                    }
                                }
                            }

                        }else {
                            PlaceholderManager.getParticles_function().put(uuidString+"function","false");
                        }


                    }


//                    if(packetType.equals(PacketType.Play.Server.WORLD_PARTICLES)){
//                        Particle type = packet.getNewParticles().read(0).getParticle();
//                        if(ActionManager.getJudgment_Message_Map().get(player.getName()) == null){
//                            ActionManager.getJudgment_Message_Map().put(player.getName(),new Message());
//                        }
//                        if(ActionManager.getJudgment_Message_Map().get(player.getName()) != null){
//                            ActionManager.getJudgment_Message_Map().get(player.getName()).setParticle(type);
//                        }
//                        if(ActionManager.getParticles_Map().get(player.getName()) == null){
//                            ActionManager.getParticles_Map().put(player.getName(),new SendParticles());
//                        }
//                        if(ActionManager.getParticles_Map().get(player.getName()) != null){
//                            boolean b = ActionManager.getParticles_Map().get(player.getName()).getResult(type);
//                            if(type == ActionManager.getParticles_Map().get(player.getName()).getPutParticle()){
//                                event.setCancelled(b);
//                            }
//                        }
//
//
//                    }

                }

            });

    }


}
