package com.daxton.customdisplay.api.player.data.set;


import com.daxton.customdisplay.api.other.StringConversion;
import com.daxton.customdisplay.manager.ConfigMapManager;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


import static org.bukkit.attribute.Attribute.GENERIC_MOVEMENT_SPEED;

public class PlayerAttributeCore {

    public PlayerAttributeCore(){

    }

    //設定原版屬性
    public static void setBukkitAttribute(Player player){
        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");
        boolean healthB = fileConfiguration.getBoolean("CoreAttribute.Health.enable");
        //設置血量
        if(healthB){
            String healthString =  fileConfiguration.getString("CoreAttribute.Health.formula");
            double health = StringConversion.getDouble(player, null, 0, healthString);
            PlayerBukkitAttribute.removeAddAttribute(player,"GENERIC_MAX_HEALTH","ADD_NUMBER",health,"setBaseValue");
        }


        //設置移動速度
        boolean moveBoolean = fileConfiguration.getBoolean("CoreAttribute.Move_Speed.enable");
        if(moveBoolean){
            String moveString = fileConfiguration.getString("CoreAttribute.Move_Speed.formula");
            double move = StringConversion.getDouble(player, null, 0.12, moveString);
            //cd.getLogger().info("移動速度: "+move);
            AttributeInstance attribute_Move_Speed = player.getAttribute(GENERIC_MOVEMENT_SPEED);
            attribute_Move_Speed.setBaseValue(move);
        }





    }


}
