package com.daxton.customdisplay.task.action2.location;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.entity.PacketEntity;
import com.daxton.customdisplay.api.location.DirectionLocation;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Guise3 {

    private final CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Map<String, String> action_Map;
    private LivingEntity self;
    private LivingEntity target;
    private String taskID;

    private PacketEntity packetEntity;

    private final Map<String, PacketEntity> packetEntity_Map = new ConcurrentHashMap<>();
    private final Map<String, Location> location_Map = new ConcurrentHashMap<>();

    public Guise3(){

    }

    public void setGuise(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        this.taskID = taskID;
        this.self = self;
        this.target = target;
        this.action_Map = action_Map;

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        String mark = actionMapHandle.getString(new String[]{"mark","mk"},"0");

        setOther(taskID+mark);

    }

    public void setOther(String livTaskID){

        ActionMapHandle actionMapHandle = new ActionMapHandle(this.action_Map, this.self, this.target);

        //實體的類型
        String entityType = actionMapHandle.getString(new String[]{"entitytype","et"},"ARMOR_STAND");

        //實體的顯示名稱
        String entityName = actionMapHandle.getString(new String[]{"entityname","en"},null);

        //實體是否看的見
        boolean visible = actionMapHandle.getBoolean(new String[]{"visible"},false);

        //獲得物品的ID
        String itemID = actionMapHandle.getString(new String[]{"itemid"},null);

        //要裝備的部位
        String equipmentSlot = actionMapHandle.getString(new String[]{"equipmentslot","es"},"HEAD");

        //座標要加上自己還是目標
        boolean teleport = actionMapHandle.getBoolean(new String[]{"teleport","tp"}, false);

        //顯示的時間
        int duration = actionMapHandle.getInt(new String[]{"duration","dt"},-1);

        List<Entity> entityList = Arrays.asList(self.getChunk().getEntities());
        entityList.forEach(entity -> {

            if(entity instanceof Player){
                Player player = (Player) entity;

                Location oldlocation = null;
                if(location_Map.get(livTaskID) != null){
                    oldlocation = location_Map.get(livTaskID);
                }
                Location location = actionMapHandle.getLocation(oldlocation);


                if(packetEntity_Map.get(livTaskID) == null){
                    if(location != null){
                        // player.sendMessage(livTaskID+"高度: "+location.getY());
                        location = location.add(0,-getEntityHight(entityType),0);
                        packetEntity_Map.put(livTaskID, new PacketEntity().createPacketEntity(entityType, player, location));

                        //location.setY(location.getY()-getEntityHight(entityType));
                        location_Map.put(livTaskID, location);

                    }
                }else {
                    if(location != null){
                        if(teleport){
                            //player.sendMessage(livTaskID+"變換高度: "+location.getY());
                            PacketEntity packetEntity1 = packetEntity_Map.get(livTaskID);
                            location_Map.put(livTaskID, location);
                            packetEntity1.teleport(location, player);
                        }
                    }
                }


                if(packetEntity_Map.get(livTaskID) != null){
                    PacketEntity packetEntity = packetEntity_Map.get(livTaskID);

                    String ent = packetEntity.getEntityTypeName();
                    if(ent.equals("ARMOR_STAND")){
                        setArmorStand(self , target, action_Map, packetEntity);
                    }

                    if(!visible){
                        packetEntity.setVisible();
                    }

                    if(itemID != null){
                        packetEntity.appendItem(itemID,equipmentSlot);
                    }

                    if(entityName != null){
                        packetEntity.appendTextLine(entityName);
                    }

                    if(duration > 0){
                        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                //player.sendMessage("刪除: >0 ");
                                packetEntity.delete();
                                packetEntity_Map.remove(livTaskID);
                                location_Map.remove(livTaskID);
                                if(ActionManager.judgment_Guise_Map2.get(taskID) != null){
                                    ActionManager.judgment_Guise_Map2.remove(taskID);
                                }
                            }
                        };
                        bukkitRunnable.runTaskLater(cd,duration);
                    }else if(duration < 0){
                        //player.sendMessage("刪除: <0 ");
                        packetEntity.delete();
                        packetEntity_Map.remove(livTaskID);
                        location_Map.remove(livTaskID);
                        if(ActionManager.judgment_Guise_Map2.get(taskID) != null){
                            ActionManager.judgment_Guise_Map2.remove(taskID);
                        }
                    }


                }



            }
        });

    }

    public void setArmorStand(LivingEntity self, LivingEntity target, Map<String, String> action_Map, PacketEntity packetEntity){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        //頭的角度
        float headX = 0;
        float headY = 0;
        float headZ = 0;
        String[] headAngle = actionMapHandle.getStringList(new String[]{"head","h"},new String[]{"0","0","0"},"\\|",3);
        if(headAngle.length == 3){

            try {
                headX = Float.parseFloat(headAngle[0]);
                headY = Float.parseFloat(headAngle[1]);
                headZ = Float.parseFloat(headAngle[2]);
            }catch (NumberFormatException exception){
                headX = 0;
                headY = 0;
                headZ = 0;
            }
        }
        packetEntity.setArmorStandAngle("head",headX,headY,headZ);
        //身體的角度
        float bodyX = 0;
        float bodyY = 0;
        float bodyZ = 0;
        String[] bodyAngle = actionMapHandle.getStringList(new String[]{"body","b"},new String[]{"0","0","0"},"\\|",3);
        if(bodyAngle.length == 3){

            try {
                bodyX = Float.parseFloat(bodyAngle[0]);
                bodyY = Float.parseFloat(bodyAngle[1]);
                bodyZ = Float.parseFloat(bodyAngle[2]);
            }catch (NumberFormatException exception){
                bodyX = 0;
                bodyY = 0;
                bodyZ = 0;
            }
        }
        packetEntity.setArmorStandAngle("body",bodyX,bodyY,bodyZ);
        //左手的角度
        float leftArmX = 0;
        float leftArmY = 0;
        float leftArmZ = 0;
        String[] leftArmAngle = actionMapHandle.getStringList(new String[]{"leftarm","lar"},new String[]{"0","0","0"},"\\|",3);
        if(leftArmAngle.length == 3){

            try {
                leftArmX = Float.parseFloat(leftArmAngle[0]);
                leftArmY = Float.parseFloat(leftArmAngle[1]);
                leftArmZ = Float.parseFloat(leftArmAngle[2]);
            }catch (NumberFormatException exception){
                leftArmX = 0;
                leftArmY = 0;
                leftArmZ = 0;
            }
        }
        packetEntity.setArmorStandAngle("leftarm",leftArmX,leftArmY,leftArmZ);
        //右手的角度
        float rightArmX = 0;
        float rightArmY = 0;
        float rightArmZ = 0;
        String[] rightArmAngle = actionMapHandle.getStringList(new String[]{"rightarm","rar"},new String[]{"0","0","0"},"\\|",3);
        if(rightArmAngle.length == 3){

            try {
                rightArmX = Float.parseFloat(rightArmAngle[0]);
                rightArmY = Float.parseFloat(rightArmAngle[1]);
                rightArmZ = Float.parseFloat(rightArmAngle[2]);
            }catch (NumberFormatException exception){
                rightArmX = 0;
                rightArmY = 0;
                rightArmZ = 0;
            }
        }
        packetEntity.setArmorStandAngle("rightarm",rightArmX,rightArmY,rightArmZ);
        //左腿的角度
        float leftLegX = 0;
        float leftLegY = 0;
        float leftLegZ = 0;
        String[] leftLegAngle = actionMapHandle.getStringList(new String[]{"leftleg","llg"},new String[]{"0","0","0"},"\\|",3);
        if(leftLegAngle.length == 3){

            try {
                leftLegX = Float.parseFloat(leftLegAngle[0]);
                leftLegY = Float.parseFloat(leftLegAngle[1]);
                leftLegZ = Float.parseFloat(leftLegAngle[2]);
            }catch (NumberFormatException exception){
                leftLegX = 0;
                leftLegY = 0;
                leftLegZ = 0;
            }
        }
        packetEntity.setArmorStandAngle("leftleg",leftLegX,leftLegY,leftLegZ);
        //右腿的角度
        float rightLegX = 0;
        float rightLegY = 0;
        float rightLegZ = 0;
        String[] rightLegAngle = actionMapHandle.getStringList(new String[]{"rightleg","rlg"},new String[]{"0","0","0"},"\\|",3);
        if(rightLegAngle.length == 3){

            try {
                rightLegX = Float.parseFloat(rightLegAngle[0]);
                rightLegY = Float.parseFloat(rightLegAngle[1]);
                rightLegZ = Float.parseFloat(rightLegAngle[2]);
            }catch (NumberFormatException exception){
                rightLegX = 0;
                rightLegY = 0;
                rightLegZ = 0;
            }
        }
        packetEntity.setArmorStandAngle("rightleg",rightLegX,rightLegY,rightLegZ);
    }

    public double getEntityHight(String entityType){
        World world = Bukkit.getWorlds().get(0);
        double hight = 0.5;
        try {
            EntityType entityType1 = Enum.valueOf(EntityType.class ,entityType.toUpperCase());
            Entity entity = (Entity) world.spawnEntity(new Location(world, 0, 0, 0), entityType1);
            hight += entity.getHeight();
            entity.remove();
        }catch (NullPointerException exception){

            Entity entity = (Entity) world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ARMOR_STAND);
            hight += entity.getHeight();
            entity.remove();
        }
        return hight;
    }

    public PacketEntity getPacketEntity() {
        return packetEntity;
    }

    public void setPacketEntity(PacketEntity packetEntity) {
        this.packetEntity = packetEntity;
    }
}
