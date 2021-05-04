package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.player.PlayerManager;

import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

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
            PlayerManager.core_Formula_Map.put(s,formulaString);
            PlayerManager.core_Boolean_Map.put(s,b);
        ;});



    }

    /**設定原版屬性**/
    public void setBukkitAttribute(Player player){
        FileConfiguration fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Class_CustomCore.yml");
        boolean healthB = fileConfiguration.getBoolean("CoreAttribute.Health.enable");
        /**設置血量**/
        if(healthB){
            String healthString =  fileConfiguration.getString("CoreAttribute.Health.formula");
            healthString = ConversionMain.valueOf(player,null,healthString);
            double health = 20;
            try{
                health = Double.valueOf(healthString);
            }catch (NumberFormatException exception){

            }



            new PlayerBukkitAttribute().addAttribute(player,"GENERIC_MAX_HEALTH","ADD_NUMBER",health,"setBaseValue");
//            AttributeInstance attribute_Max_Health = player.getAttribute(GENERIC_MAX_HEALTH);
//            attribute_Max_Health.setBaseValue(health);
        }





        /**設置移動速度**/

        boolean moveBoolean = fileConfiguration.getBoolean("CoreAttribute.Move_Speed.enable");
        if(moveBoolean){
            String moveString = fileConfiguration.getString("CoreAttribute.Move_Speed.formula");
            moveString = ConversionMain.valueOf(player,null,moveString);
            double move = 0.12;
            try {
                move = Double.valueOf(moveString);
            }catch (NumberFormatException exception){

            }
            //cd.getLogger().info("移動速度: "+move);
            AttributeInstance attribute_Move_Speed = player.getAttribute(GENERIC_MOVEMENT_SPEED);
            attribute_Move_Speed.setBaseValue(move);

        }


    }
    /**設定其他屬性**/
    public void setCoreAttribute(Player player){

        String uuidString = player.getUniqueId().toString();

        /**攻擊速度**/
        String attackSpeedString = PlayerManager.core_Formula_Map.get("Attack_Speed");
        attackSpeedString = ConversionMain.valueOf(player,null,attackSpeedString);
        int  attackSpeed = 10;
        try {
            double number = Arithmetic.eval(attackSpeedString);
            String numberDec = new NumberUtil(number,"#").getDecimalString();
            attackSpeed = Integer.valueOf(numberDec);
        }catch (Exception exception){
            attackSpeed = 10;
        }
        PlayerManager.attack_Count_Map.put(uuidString,attackSpeed);
        PlayerManager.cost_Count_Map.put(uuidString,0);

    }

}
