package com.daxton.customdisplay.task.action.list;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.ConfigFind;
import com.daxton.customdisplay.api.character.StringConversion;
import com.daxton.customdisplay.api.character.StringConversion2;
import com.daxton.customdisplay.api.character.StringFind;
import com.daxton.customdisplay.manager.ActionManager;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.task.action.ClearAction;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.comphenix.protocol.wrappers.EnumWrappers.ChatType.CHAT;
import static com.comphenix.protocol.wrappers.EnumWrappers.ChatType.GAME_INFO;
import static java.lang.Integer.parseInt;
import static org.bukkit.Color.*;
import static org.bukkit.Particle.REDSTONE;

public class SendParticles {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private String taskID = "";
    private Player player = null;
    private LivingEntity self = null;
    private LivingEntity target = null;

    private static String hexString = "0123456789ABCDEF";

    private String function = "";
    private Particle putParticle;
    private Particle inParticle;
    private Particle.DustOptions color = new Particle.DustOptions(fromRGB(0xFF0000), 1);
    private Location location = new Location(Bukkit.getWorld("world"),0,0,0);
    private String aims = "";
    private double extra = 0;
    private int count = 10;
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double xOffset = 0;
    private double yOffset = 0;
    private double zOffset = 0;


    public SendParticles(){



    }

    public void setParticles(LivingEntity self,LivingEntity target, String firstString, String taskID){
        this.taskID = taskID;
        this.player = (Player) self;
        this.self = self;
        this.target = target;
        stringSetting(firstString);


    }

    public void stringSetting(String firstString){
        List<String> stringList = new StringFind().getStringList(firstString);
        for(String allString : stringList) {
            if (allString.toLowerCase().contains("function=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    function = strings[1];
                }
            }
            if(allString.toLowerCase().contains("@=")){
                String[] strings = allString.split("=");
                if(strings.length == 2){
                    aims = strings[1];
                }
            }
            if (allString.toLowerCase().contains("particle=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        putParticle = Enum.valueOf(Particle.class,strings[1].toUpperCase());
                    }catch (Exception exception){

                    }

                }
            }
            if (allString.toLowerCase().contains("color=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {


                    try{
                        BigInteger bigint=new BigInteger(strings[1], 16);
                        int numb =bigint.intValue();
                        color = new Particle.DustOptions(fromRGB(numb), 1);
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("color錯誤"+strings[1]);
                    }
                }
            }
            if (allString.toLowerCase().contains("extra=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        extra = Double.valueOf(strings[1]);
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("extra錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("count=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        count = Integer.valueOf(strings[1]);
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("count錯誤"+strings[1]);
                    }

                }
            }

        }

        for(String allString : new StringFind().getStringMessageList(firstString)){
            if (allString.toLowerCase().contains("x=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        x = Double.valueOf(new StringConversion2(self,target,strings[1],"Character").valueConv());
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("x錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("y=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        y = Double.valueOf(new StringConversion2(self,target,strings[1],"Character").valueConv());
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("y錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("z=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        z = Double.valueOf(new StringConversion2(self,target,strings[1],"Character").valueConv());
                    }catch (NumberFormatException exception){
                        //cd.getLogger().info("z錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("xoffset=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        xOffset = Double.valueOf(new StringConversion2(self,target,strings[1],"Character").valueConv());
                    }catch (NumberFormatException exception){
                       // cd.getLogger().info("xOffset錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("yoffset=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        yOffset = Double.valueOf(new StringConversion2(self,target,strings[1],"Character").valueConv());
                    }catch (NumberFormatException exception){
                       // cd.getLogger().info("yOffset錯誤");
                    }

                }
            }
            if (allString.toLowerCase().contains("zoffset=")) {
                String[] strings = allString.split("=");
                if (strings.length == 2) {
                    try{
                        zOffset = Double.valueOf(new StringConversion2(self,target,strings[1],"Character").valueConv());
                    }catch (NumberFormatException exception){
                       // cd.getLogger().info("zOffset錯誤");
                    }

                }
            }
        }

        if(aims.toLowerCase().contains("target")){
            location = target.getLocation().add(x,y,z);
        }else if(aims.toLowerCase().contains("self")){
            location = player.getLocation().add(x,y,z);
        }else if(aims.toLowerCase().contains("world")){
            location = new Location(player.getWorld(),x,y,z);
        }else {
            location = location.add(x,y,z);
        }

        if(function.toLowerCase().contains("remove")){
            if(ActionManager.getSendParticles_ProtocolManager_Map().get(taskID) == null){
                ActionManager.getSendParticles_ProtocolManager_Map().put(taskID,ProtocolLibrary.getProtocolManager());
            }
            if(ActionManager.getSendParticles_ProtocolManager_Map().get(taskID) != null){
                Packet();
            }
        }else {
            sendParticle();
        }
    }
    public void sendParticle(){
        try{
            if(putParticle == REDSTONE){
                self.getWorld().spawnParticle(putParticle, location.add(x, y, z), count, xOffset, yOffset, zOffset, extra,color);
            }else {
                target.getWorld().spawnParticle(putParticle, location.add(x, y, z), count, xOffset, yOffset, zOffset, extra);
            }
        }catch (Exception e){
            //cd.getLogger().info(e.toString());
        }


    }

    public void Packet(){

        ActionManager.getSendParticles_ProtocolManager_Map().get(taskID).addPacketListener(new PacketAdapter(PacketAdapter.params().plugin(cd).clientSide().serverSide().listenerPriority(ListenerPriority.NORMAL).gamePhase(GamePhase.PLAYING).optionAsync().options(ListenerOptions.SKIP_PLUGIN_VERIFIER).types(PacketType.Play.Server.WORLD_PARTICLES)) {

            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                PacketType packetType = event.getPacketType();

                if(packetType.equals(PacketType.Play.Server.WORLD_PARTICLES)){
                    Particle type = packet.getNewParticles().read(0).getParticle();
                    inParticle = type;
                    if(putParticle == inParticle){
                        event.setCancelled(true);
                    }

                }

            }

        });

    }

    public Particle getPutParticle() {
        return putParticle;
    }
}
