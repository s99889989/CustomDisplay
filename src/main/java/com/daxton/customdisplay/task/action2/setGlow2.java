package com.daxton.customdisplay.task.action2;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class setGlow2 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    List<PacketContainer> packets = new ArrayList<>();

    public setGlow2(){


    }

    public void setGlow(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        ChatColor color = customLineConfig.getChatColor(new String[]{"color","c"},"WHITE",self,target);

        if(self instanceof Player){
            Player player = (Player) self;
            createTeam(target);
            setGlowing(target);
            addEntity(target, color);
            upTeam(target);

            packets.forEach((packet)->sendPack(player,packet));
        }




    }

    /**建立隊伍**/
    public void createTeam(LivingEntity livingEntity){
        PacketContainer packet = ActionManager.protocolManager.createPacket( PacketType.Play.Server.SCOREBOARD_TEAM );

        packet.getStrings().write(0, "TestTeam");
        packet.getIntegers().write(0, 0);

        packet.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, ChatColor.BLUE);
        packet.getStrings().write(1, "ALWAYS");
        packet.getSpecificModifier(Collection.class).write(0, Arrays.asList( new String[]{livingEntity.getUniqueId().toString()} ));
        packets.add(packet);
    }

    /**用發光標記隊友**/
    public void setGlowing(LivingEntity livingEntity) {

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, livingEntity.getEntityId());

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
        watcher.setEntity(livingEntity);
        watcher.setObject(0, serializer, (byte) (0x40));
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        packets.add(packet);

    }

    /**將生物添加到隊伍中**/
    public void addEntity(LivingEntity livingEntity, ChatColor color){
        PacketContainer packet = ActionManager.protocolManager.createPacket( PacketType.Play.Server.SCOREBOARD_TEAM );

        packet.getStrings().write(0, "TestTeam");
        packet.getIntegers().write(0, 3);

        packet.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, color);
        packet.getStrings().write(1, "ALWAYS");
        packet.getSpecificModifier(Collection.class).write(0, Arrays.asList( new String[]{livingEntity.getUniqueId().toString()} ));
        packets.add(packet);
    }

    /**更新隊伍訊息**/
    public void upTeam(LivingEntity livingEntity){
        PacketContainer packet = ActionManager.protocolManager.createPacket( PacketType.Play.Server.SCOREBOARD_TEAM );

        packet.getStrings().write(0, "TestTeam");
        packet.getIntegers().write(0, 2);

        packet.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, ChatColor.BLUE);
        packet.getStrings().write(1, "ALWAYS");
        packet.getSpecificModifier(Collection.class).write(0, Arrays.asList( new String[]{livingEntity.getUniqueId().toString()} ));
        packets.add(packet);
    }

    /**發送封包**/
    public void sendPack(Player player,PacketContainer packet){

        try {
            ActionManager.protocolManager.sendServerPacket( player, packet );
        } catch ( InvocationTargetException exception ) {
            exception.printStackTrace();
        }
    }




}
