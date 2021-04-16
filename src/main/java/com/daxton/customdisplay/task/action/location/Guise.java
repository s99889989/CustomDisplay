package com.daxton.customdisplay.task.action.location;


import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.config.CustomLineConfig;
import com.daxton.customdisplay.api.entity.PacketEntity;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class Guise {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private PacketEntity packetEntity;

    public Guise(){

    }

    public void setItemEntity(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String taskID){

        /**實體的類型**/
        String entityType = customLineConfig.getString(new String[]{"entitytype","et"},"ARMOR_STAND",self,target);

        /**實體的顯示名稱**/
        String entityName = customLineConfig.getString(new String[]{"entityname","en"},null,self,target);

        /**實體是否看的見**/
        boolean visible = customLineConfig.getBoolean(new String[]{"visible"},true,self,target);

        /**獲得物品的ID**/
        String itemID = customLineConfig.getString(new String[]{"itemid"},null,self,target);

        /**要裝備的部位**/
        String equipmentSlot = customLineConfig.getString(new String[]{"equipmentslot","es"},"HEAD",self,target);

        /**座標要加上自己還是目標**/
        String locTarget = customLineConfig.getString(new String[]{"loctarget","lt"},null,self,target);

        /**顯示的時間**/
        int duration = customLineConfig.getInt(new String[]{"duration","dt"},-1,self,target);

        /**對目標增加座標量**/
        double x = 0;
        double y = 0;
        double z = 0;
        String[] locAdds = customLineConfig.getStringList(new String[]{"locadd","la"},new String[]{"0","0","0"},"\\|",3, self, target);
        if(locAdds.length == 3){

            try {
                x = Double.parseDouble(locAdds[0]);
                y = Double.parseDouble(locAdds[1]);
                z = Double.parseDouble(locAdds[2]);
            }catch (NumberFormatException exception){
                x = 0;
                y = 0;
                z = 0;
            }
        }
        Location location = null;
        if(locTarget != null){
            if(locTarget.toLowerCase().contains("self")){

                location = self.getLocation();

            }
            if(locTarget.toLowerCase().contains("target")){
                if(target != null){
                    location = target.getLocation();
                }
            }
        }else {
            location = self.getLocation();
        }

        if(location != null){
            location = location.add(x,y,z);
        }

        runItemEntity(self, target, customLineConfig, entityType, location, visible, itemID, entityName, equipmentSlot, duration);

    }

    public void runItemEntity(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig, String entityType, Location location, boolean visible, String itemID, String entityName, String equipmentSlot, int duration){
        if(self instanceof Player){

            Player player = ((Player) self).getPlayer();

            if(this.packetEntity == null){

                this.packetEntity = new PacketEntity().createPacketEntity(entityType, player, location);


                String ent = this.packetEntity.getEntityTypeName();
                if(ent.equals("ARMOR_STAND")){
                    setArmorStand(self ,target ,customLineConfig);
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

            }
            if(this.packetEntity != null){

                this.packetEntity.teleport(location, player);

                String ent = this.packetEntity.getEntityTypeName();
                if(ent.equals("ARMOR_STAND")){
                    setArmorStand(self ,target ,customLineConfig);
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
                }else if(duration < 0){
                    packetEntity.delete();
                }

            }



            //DRAGON_FIREBALL ARMOR_STAND

            //packetEntity.velocity(moveEntity(player));


        }
    }

    public void setArmorStand(LivingEntity self, LivingEntity target, CustomLineConfig customLineConfig){

        /**頭的角度**/
        float headX = 0;
        float headY = 0;
        float headZ = 0;
        String[] headAngle = customLineConfig.getStringList(new String[]{"head","h"},new String[]{"0","0","0"},"\\|",3, self, target);
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
        this.packetEntity.setArmorStandAngle("head",headX,headY,headZ);
        /**身體的角度**/
        float bodyX = 0;
        float bodyY = 0;
        float bodyZ = 0;
        String[] bodyAngle = customLineConfig.getStringList(new String[]{"body","b"},new String[]{"0","0","0"},"\\|",3, self, target);
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
        String[] leftArmAngle = customLineConfig.getStringList(new String[]{"leftarm","lar"},new String[]{"0","0","0"},"\\|",3, self, target);
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
        String[] rightArmAngle = customLineConfig.getStringList(new String[]{"rightarm","rar"},new String[]{"0","0","0"},"\\|",3, self, target);
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
        String[] leftLegAngle = customLineConfig.getStringList(new String[]{"leftleg","llg"},new String[]{"0","0","0"},"\\|",3, self, target);
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
        String[] rightLegAngle = customLineConfig.getStringList(new String[]{"rightleg","rlg"},new String[]{"0","0","0"},"\\|",3, self, target);
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


    /**給向量移動**/
    public Vector moveEntity(Player player){


        double angle = 180;
        double hight = 1;

        double pitch = ((player.getLocation().getPitch() + 90) * Math.PI) / 180;
        double yaw  = ((player.getLocation().getYaw() + 90+angle)  * Math.PI) / 180;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double z = Math.sin(pitch) * Math.sin(yaw);

        Vector vector = new Vector(x, 0, z);
        Vector vector1 = new Vector(0,hight,0);
        Vector vector2 = vector.add(vector1);


        return vector2;
    }

    public PacketEntity getPacketEntity() {
        return packetEntity;
    }

    public void setPacketEntity(PacketEntity packetEntity) {
        this.packetEntity = packetEntity;
    }
}
