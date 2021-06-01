package com.daxton.customdisplay.task.action.entity;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class setGlow3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();



    public setGlow3(){


    }

    public static void setGlow(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        ChatColor color = actionMapHandle.getChatColor(new String[]{"color","c"},"WHITE");

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListTarget();
        List<Entity> entityList = Arrays.asList(self.getChunk().getEntities());
        entityList.forEach(entity -> {
            if(entity instanceof Player){
                Player player = (Player) entity;
                List<PacketContainer> packets = new ArrayList<>();
                livingEntityList.forEach(livingEntity -> {
                    createTeam(livingEntity);
                    packets.add(createTeam(livingEntity));
                    packets.add(setGlowing(livingEntity));
                    packets.add(addEntity(livingEntity, color));
                    packets.add(upTeam(livingEntity));
                });
                packets.forEach((packet)->sendPack(player,packet));
            }
        });


    }

    /**建立隊伍**/
    public static PacketContainer createTeam(LivingEntity livingEntity){
        PacketContainer packet = ActionManager.protocolManager.createPacket( PacketType.Play.Server.SCOREBOARD_TEAM );

        packet.getStrings().write(0, "TestTeam");
        packet.getIntegers().write(0, 0);

        packet.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, ChatColor.BLUE);
        packet.getStrings().write(1, "ALWAYS");
        packet.getSpecificModifier(Collection.class).write(0, Arrays.asList( new String[]{livingEntity.getUniqueId().toString()} ));
        return packet;

    }

    /**用發光標記隊友**/
    public static PacketContainer setGlowing(LivingEntity livingEntity) {

        PacketContainer packet = ActionManager.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, livingEntity.getEntityId());

        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
        watcher.setEntity(livingEntity);
        watcher.setObject(0, serializer, (byte) (0x40));
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        return packet;

    }

    /**將生物添加到隊伍中**/
    public static PacketContainer addEntity(LivingEntity livingEntity, ChatColor color){
        PacketContainer packet = ActionManager.protocolManager.createPacket( PacketType.Play.Server.SCOREBOARD_TEAM );

        packet.getStrings().write(0, "TestTeam");
        packet.getIntegers().write(0, 3);

        packet.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, color);
        packet.getStrings().write(1, "ALWAYS");
        packet.getSpecificModifier(Collection.class).write(0, Arrays.asList( new String[]{livingEntity.getUniqueId().toString()} ));
        return packet;
    }

    /**更新隊伍訊息**/
    public static PacketContainer upTeam(LivingEntity livingEntity){
        PacketContainer packet = ActionManager.protocolManager.createPacket( PacketType.Play.Server.SCOREBOARD_TEAM );

        packet.getStrings().write(0, "TestTeam");
        packet.getIntegers().write(0, 2);

        packet.getEnumModifier(ChatColor.class, MinecraftReflection.getMinecraftClass("EnumChatFormat")).write(0, ChatColor.BLUE);
        packet.getStrings().write(1, "ALWAYS");
        packet.getSpecificModifier(Collection.class).write(0, Arrays.asList( new String[]{livingEntity.getUniqueId().toString()} ));
        return packet;
    }

    /**發送封包**/
    public static void sendPack(Player player,PacketContainer packet){

        try {
            ActionManager.protocolManager.sendServerPacket( player, packet );
        } catch ( InvocationTargetException exception ) {
            exception.printStackTrace();
        }
    }




}
