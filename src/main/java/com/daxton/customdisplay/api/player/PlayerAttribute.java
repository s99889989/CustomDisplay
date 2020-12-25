package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;

public class PlayerAttribute {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public PlayerAttribute(){

    }

    public void addAttribute(Player player){

        Attribute maxHealth = Attribute.GENERIC_MAX_HEALTH;
        AttributeInstance playerMaxHealth = player.getAttribute(maxHealth);
        for(AttributeModifier attributeModifier : playerMaxHealth.getModifiers()){
            player.sendMessage(attributeModifier.getName()+"量: "+attributeModifier.getAmount());
        }
//        double addNumber = 20;
//        AttributeModifier healthModifier = new AttributeModifier("CustomDisplayHealth", addNumber, AttributeModifier.Operation.ADD_NUMBER);
//        playerMaxHealth.addModifier(healthModifier);
//        player.sendMessage("血量: "+playerMaxHealth.getValue());
        //player.saveData();

    }

    public void removeAttribute(Player player){
        Attribute maxHealth = Attribute.GENERIC_MAX_HEALTH;
        AttributeInstance playerMaxHealth = player.getAttribute(maxHealth);
        for(AttributeModifier attributeModifier : playerMaxHealth.getModifiers()){
            if(attributeModifier.getName().toLowerCase().contains("customdisplay")){
                playerMaxHealth.removeModifier(attributeModifier);
            }
            player.sendMessage(attributeModifier.getName());
        }
        cd.getLogger().info("血量: "+playerMaxHealth.getValue());
    }

}
