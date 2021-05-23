package com.daxton.customdisplay.task.action2.location;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.ActionManager;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.beykerykt.lightapi.LightAPI;
import ru.beykerykt.lightapi.LightType;
import ru.beykerykt.lightapi.chunks.ChunkInfo;
import ru.beykerykt.lightapi.request.RequestSteamMachine;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SetLight {

    public static void setLight(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        int lightLevel = actionMapHandle.getInt(new String[]{"lightlevel","ll"},0);

        //顯示的時間
        int duration = actionMapHandle.getInt(new String[]{"duration","dt"},-1);

        Location location = actionMapHandle.getLocation(null);

//        if(self instanceof Player){
//            Player player = (Player) self;
//
//            sendLight(player);
//        }

        if(location != null){

            createLight(location, lightLevel, true);

            if(duration > 0){
                BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                    @Override
                    public void run() {

                        deleteLight(location, true);

                    }
                };
                bukkitRunnable.runTaskLater(cd,duration);
            }else if(duration < 0){

                deleteLight(location, true);

            }

        }

//        int x = (int) location.getX();
//        int y = (int) location.getY();
//        int z = (int) location.getZ();

        //setRawLightLevel(location.getWorld(), lightType, x, y, z, lightLevel);

//        List<Entity> entityList = Arrays.asList(self.getChunk().getEntities());
//        entityList.forEach(entity -> {
//            if(entity instanceof Player){
//                Player player = (Player) entity;
//                sendPacket(player, x, y, z);
//            }
//        });

    }

    private static void createLight(Location location, int level, boolean flag) {

        LightAPI.createLight(location, LightType.BLOCK, level, true);
        up(location);
    }

    private static void deleteLight(Location location, boolean flag) {

        LightAPI.deleteLight(location, LightType.BLOCK, true);

        up(location);
    }

    private static void up(Location location){
        for(ChunkInfo info: LightAPI.collectChunks(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ())){
            LightAPI.updateChunks(info);
        }
    }

//    public static void upLight(World world){
//        HashSet<Chunk> chunks = new HashSet<>();
//        for (Map.Entry<BlockPosition, IBlockData> entry : modified.entrySet()) {
//            Chunk chunk = world.getChunkProvider().getChunkAt(entry.getKey().getX() >> 4, entry.getKey().getZ() >> 4, true);
//            chunks.add(chunk);
//            chunk.setType(entry.getKey(), entry.getValue(), false);
//        }
//    }

    public static void sendLight(Player player){

        World world = player.getWorld();
        Chunk chunk = ((CraftWorld) world).getHandle().getChunkAt(player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ());
        PacketPlayOutLightUpdate packet = new PacketPlayOutLightUpdate(chunk.getPos(), chunk.e(), true);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

    }

    public static void sendPacket(Player player, int x, int y ,int z) {

        PacketContainer packet = new PacketContainer(PacketType.Play.Server.LIGHT_UPDATE);

        packet.getIntegers().write(player.getLocation().getChunk().getX(), x);
        packet.getIntegers().write(player.getLocation().getChunk().getZ(), y);

        packet.getBooleans().write(0, true);


        packet.getIntegers().write(2, z);
        packet.getIntegers().write(3, x);
        packet.getIntegers().write(4, y);
        packet.getIntegers().write(5, z);

        List<byte[]> g = new ArrayList<>();
        List<byte[]> h = new ArrayList<>();

        //packet.get

        packet.getBlockPositionCollectionModifier().write(0, Collections.emptyList());
        packet.getBlockPositionCollectionModifier().write(1, Collections.emptyList());


        try {
            ActionManager.protocolManager.sendServerPacket(player, packet, false);
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }


    @SuppressWarnings("SameParameterValue")
    private static void setRawLightLevel(World world, String type, int blockX, int blockY, int blockZ, int lightlevel) {




        WorldServer worldServer = ((CraftWorld) world).getHandle();
        final BlockPosition position = new BlockPosition(blockX, blockY, blockZ);
        final LightEngineThreaded lightEngine = worldServer.getChunkProvider().getLightEngine();

        final int finalLightLevel = lightlevel < 0 ? 0 : lightlevel > 15 ? 15 : lightlevel;

        if(type.toLowerCase().contains("sky")){
            LightEngineLayerEventListener layer = lightEngine.a(EnumSkyBlock.SKY);
            if (!(layer instanceof LightEngineSky)) return;
            LightEngineSky les = (LightEngineSky) layer;
            if (finalLightLevel == 0) {
                les.a(position);
            } else if (les.a(SectionPosition.a(position)) != null) {
                try {
                    //lightEngineLayer_a(les, position, finalLightLevel);
                } catch (NullPointerException ignore) {
                    // To prevent problems with the absence of the NibbleArray, even
                    // if les.a(SectionPosition.a(position)) returns non-null value (corrupted data)
                }
            }
        }else {
            LightEngineLayerEventListener layer = lightEngine.a(EnumSkyBlock.BLOCK);
            if (!(layer instanceof LightEngineBlock)) return;
            LightEngineBlock leb = (LightEngineBlock) layer;
            if (finalLightLevel == 0) {
                leb.a(position);
            } else if (leb.a(SectionPosition.a(position)) != null) {
                try {
                    leb.a(position, finalLightLevel);
                } catch (NullPointerException ignore) {
                    // To prevent problems with the absence of the NibbleArray, even
                    // if leb.a(SectionPosition.a(position)) returns non-null value (corrupted data)
                }
            }
        }




    }



}
