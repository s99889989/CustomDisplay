package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.manager.PlayerDataMap;
import io.lumine.xikage.mythicmobs.utils.config.file.FileConfiguration;
import io.lumine.xikage.mythicmobs.utils.config.file.YamlConfiguration;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import java.io.File;

import static org.bukkit.attribute.Attribute.GENERIC_MAX_HEALTH;
import static org.bukkit.attribute.Attribute.GENERIC_MOVEMENT_SPEED;

public class PlayerAttributeCore {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerAttributeCore(){

    }

    /**設置核心公式字串**/
    public void setFormula(){
        File file = new File(cd.getDataFolder(),"Class/CustomCore.yml");
        FileConfiguration coreConfig = YamlConfiguration.loadConfiguration(file);
        coreConfig.getConfigurationSection("CoreAttribute").getKeys(false).forEach(s -> {
            String formulaString = coreConfig.getString("CoreAttribute."+s+".formula");
            boolean b = coreConfig.getBoolean("CoreAttribute."+s+".enable");
            PlayerDataMap.core_Formula_Map.put(s,formulaString);
            PlayerDataMap.core_Boolean_Map.put(s,b);
            ;});



    }

    /**設定原版屬性**/
    public void setBukkitAttribute(Player player){


        /**設置血量**/
        boolean healthBoolean = Boolean.valueOf(PlayerDataMap.core_Boolean_Map.get("Health"));
        if(healthBoolean){
            String healthString = PlayerDataMap.core_Formula_Map.get("Health");
            healthString = new ConversionMain().valueOf(player,null,healthString);
            double health = 20;
            try{
                health = Double.valueOf(healthString);
                AttributeInstance attribute_Max_Health = player.getAttribute(GENERIC_MAX_HEALTH);
                attribute_Max_Health.setBaseValue(health);
            }catch (NumberFormatException exception){
                AttributeInstance attribute_Max_Health = player.getAttribute(GENERIC_MAX_HEALTH);
                attribute_Max_Health.setBaseValue(health);
            }

        }

        /**設置移動速度**/

        boolean moveBoolean = Boolean.valueOf(PlayerDataMap.core_Boolean_Map.get("Move_Speed"));;
        if(moveBoolean){
            String moveString = PlayerDataMap.core_Formula_Map.get("Move_Speed");
            moveString = new ConversionMain().valueOf(player,null,moveString);
            double move = 0.12;
            try {
                move = Double.valueOf(moveString);
                AttributeInstance attribute_Move_Speed = player.getAttribute(GENERIC_MOVEMENT_SPEED);
                attribute_Move_Speed.setBaseValue(move);
            }catch (NumberFormatException exception){
                AttributeInstance attribute_Move_Speed = player.getAttribute(GENERIC_MOVEMENT_SPEED);
                attribute_Move_Speed.setBaseValue(move);
            }

        }


    }
    /**設定其他屬性**/
    public void setCoreAttribute(Player player){

        String uuidString = player.getUniqueId().toString();

        /**攻擊速度**/
        String attackSpeedString = PlayerDataMap.core_Formula_Map.get("Attack_Speed");
        attackSpeedString = new ConversionMain().valueOf(player,null,attackSpeedString);
        int  attackSpeed = 10;
        try {
            double number = Arithmetic.eval(attackSpeedString);
            String numberDec = new NumberUtil(number,"#").getDecimalString();
            attackSpeed = Integer.valueOf(numberDec);
        }catch (Exception exception){
            attackSpeed = 10;
        }
        PlayerDataMap.attack_Count_Map.put(uuidString,attackSpeed);
        PlayerDataMap.cost_Count_Map.put(uuidString,0);

    }

}
