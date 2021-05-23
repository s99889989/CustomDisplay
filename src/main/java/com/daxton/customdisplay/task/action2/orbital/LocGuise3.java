package com.daxton.customdisplay.task.action2.orbital;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.entity.PacketEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class LocGuise3 {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private PacketEntity packetEntity;

    public LocGuise3(){

    }

    public void setItemEntity(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID, Location inputLocation){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        /**實體的類型**/
        String entityType = actionMapHandle.getString(new String[]{"entitytype","et"},"ARMOR_STAND");

        /**實體的顯示名稱**/
        String entityName = actionMapHandle.getString(new String[]{"entityname","en"},null);

        /**實體是否看的見**/
        boolean visible = actionMapHandle.getBoolean(new String[]{"visible"},false);

        /**獲得物品的ID**/
        String itemID = actionMapHandle.getString(new String[]{"itemid"},null);

        /**要裝備的部位**/
        String equipmentSlot = actionMapHandle.getString(new String[]{"equipmentslot","es"},"HEAD");

        /**顯示的時間**/
        int duration = actionMapHandle.getInt(new String[]{"duration","dt"},-1);

        //是否要移動
        boolean teleport = actionMapHandle.getBoolean(new String[]{"teleport","tp"}, false);

        Location location = actionMapHandle.getLocation2(inputLocation);
        //cd.getLogger().info(inputLocation.getY()+" : "+location.getY());
        List<Entity> entityList = Arrays.asList(self.getChunk().getEntities());
        entityList.forEach(entity -> {
            if(entity instanceof Player){

                Player player = (Player) entity;
                //Location location2 = location.add(0,-getEntityHight(entityType),0);
                if(this.packetEntity == null){

                    this.packetEntity = new PacketEntity().createPacketEntity(entityType, player, location);

                }else {
                    if(teleport){
                        this.packetEntity.teleport(location, player);
                    }
                }
                if(this.packetEntity != null){

                    if(duration < 0){
                        packetEntity.delete();
                        return;
                    }

                    String ent = this.packetEntity.getEntityTypeName();

                    if(ent.equals("ARMOR_STAND")){

                        setArmorStand(self ,target , action_Map, location);
                    }

                    if(!visible){
                        this.packetEntity.setVisible();
                    }

                    if(itemID != null){
                        this.packetEntity.appendItem(itemID,equipmentSlot);
                    }

                    if(entityName != null){
                        packetEntity.appendTextLine(entityName);
                    }

                    if(duration > 0){
                        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                packetEntity.delete();

                            }
                        };
                        bukkitRunnable.runTaskLater(cd,duration);
                    }

                }

            }
        });

    }


    public void setArmorStand(LivingEntity self, LivingEntity target, Map<String, String> action_Map, Location location){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String[] HeadAddLoc = actionMapHandle.getStringList(new String[]{"HeadAddLoc","hal"},new String[]{"false","false"},"\\|",2);
        boolean headPitch = Boolean.parseBoolean(HeadAddLoc[0]);
        boolean headYaw = Boolean.parseBoolean(HeadAddLoc[1]);

        /**頭的角度**/
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
        if(location != null){

            if(headPitch){
                //cd.getLogger().info(" Pitch: "+location.getPitch());
                headX += location.getPitch();
            }
            if(headYaw){
                //cd.getLogger().info(" Yaw: "+location.getYaw());
                headY += location.getYaw();
            }

        }

        this.packetEntity.setArmorStandAngle("head",headX,headY,headZ);
        /**身體的角度**/
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
        this.packetEntity.setArmorStandAngle("body",bodyX,bodyY,bodyZ);
        /**左手的角度**/
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
        this.packetEntity.setArmorStandAngle("leftarm",leftArmX,leftArmY,leftArmZ);
        /**右手的角度**/
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
        this.packetEntity.setArmorStandAngle("rightarm",rightArmX,rightArmY,rightArmZ);
        /**左腿的角度**/
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
        this.packetEntity.setArmorStandAngle("leftleg",leftLegX,leftLegY,leftLegZ);
        /**右腿的角度**/
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
        this.packetEntity.setArmorStandAngle("rightleg",rightLegX,rightLegY,rightLegZ);
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
}
