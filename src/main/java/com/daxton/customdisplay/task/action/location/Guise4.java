package com.daxton.customdisplay.task.action.location;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.api.entity.GuiseEntity;
import com.daxton.customdisplay.manager.ActionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.Map;



public class Guise4 {

    public Guise4(){

    }

    public static void setGuise(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID, Location inputLocation){
        CustomDisplay cd = CustomDisplay.getCustomDisplay();
        //cd.getLogger().info("Guise");
        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        //標記名稱
        String mark = actionMapHandle.getString(new String[]{"mark","mk"},"0");
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
        //是否要移動
        boolean teleport = actionMapHandle.getBoolean(new String[]{"teleport","tp"}, false);
        //顯示的時間
        int duration = actionMapHandle.getInt(new String[]{"duration","dt"},0);

        if(ActionManager.guise_Map.get(taskID+mark) == null){
           // cd.getLogger().info("創建");
            Location location;
            if(inputLocation != null){
                location = actionMapHandle.getLocation(inputLocation);
            }else {
                location = actionMapHandle.getLocation(null);
            }
            if(location != null){
                location = location.add(0,-getEntityHight(entityType),0);
                GuiseEntity guiseEntity = new GuiseEntity().createPacketEntity(entityType, location);
                if(guiseEntity != null){
                    ActionManager.guise_Map.put(taskID+mark, guiseEntity);

                    if (duration > 0) {
                        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                //cd.getLogger().info("刪除");
                                guiseEntity.delete();
                                ActionManager.guise_Map.remove(taskID+mark);
                            }
                        };
                        bukkitRunnable.runTaskLater(cd, duration);
                    }

                }
            }

        }else {
           // cd.getLogger().info("移動");
            GuiseEntity guiseEntity = ActionManager.guise_Map.get(taskID+mark);
            if(teleport){
                Location location;
                if(inputLocation != null){
                    location = actionMapHandle.getLocation(inputLocation);
                }else {
                    Location oldLocation = guiseEntity.getLocation();
                    location = actionMapHandle.getLocation(oldLocation);
                }
                if(location != null){
                    guiseEntity.teleport(location);
                }
            }
        }

        if(ActionManager.guise_Map.get(taskID+mark) != null){
            //cd.getLogger().info("改動");

            GuiseEntity guiseEntity = ActionManager.guise_Map.get(taskID+mark);

            String ent = guiseEntity.getEntityTypeName();
            if (ent.equals("ARMOR_STAND")) {
                setArmorStand(self, target, action_Map, guiseEntity);
            }

            if (visible != guiseEntity.isVisible()) {
                guiseEntity.setVisible(visible);
            }

            if (itemID != null) {
                guiseEntity.appendItem(itemID, equipmentSlot);
            }

            if (entityName != null) {
                guiseEntity.appendTextLine(entityName);
            }

            if (duration < 0) {
                guiseEntity.delete();
                ActionManager.guise_Map.remove(taskID+mark);
            }

        }



    }

    public static void setArmorStand(LivingEntity self, LivingEntity target, Map<String, String> action_Map, GuiseEntity packetEntity){

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);

        String[] HeadAddLoc = actionMapHandle.getStringList(new String[]{"HeadAddLoc","hal"},new String[]{"false","false"},"\\|",2);
        boolean headPitch = Boolean.parseBoolean(HeadAddLoc[0]);
        boolean headYaw = Boolean.parseBoolean(HeadAddLoc[1]);

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

            Location location = packetEntity.getLocation();
            if(headPitch){
                headX += location.getPitch();
            }
            if(headYaw){
                headY += location.getYaw();
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

    public static double getEntityHight(String entityType){
        World world = Bukkit.getWorlds().get(0);
        double hight = 0.5;
        try {
            EntityType entityType1 = Enum.valueOf(EntityType.class ,entityType.toUpperCase());
            Entity entity = world.spawnEntity(new Location(world, 0, 0, 0), entityType1);
            hight += entity.getHeight();
            entity.remove();
        }catch (NullPointerException exception){

            Entity entity = world.spawnEntity(new Location(world, 0, 0, 0), EntityType.ARMOR_STAND);
            hight += entity.getHeight();
            entity.remove();
        }
        return hight;
    }

}
