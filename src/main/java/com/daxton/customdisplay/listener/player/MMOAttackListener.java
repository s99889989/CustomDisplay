package com.daxton.customdisplay.listener.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerData;
import com.daxton.customdisplay.manager.PlayerDataMap;
import net.mmogroup.mmolib.MMOLib;
import net.mmogroup.mmolib.api.event.PlayerAttackEvent;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import java.util.UUID;

import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class MMOAttackListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target;

    private UUID playerUUID;

    private UUID targetUUID;

    @EventHandler
    public void onAttack(PlayerAttackEvent event) {

        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        target = event.getEntity();
        String st = event.getData().getPlayer().toString();
        double damageNumber = event.getAttack().getDamage();



        if(event.getData().getPlayer() instanceof Player){
            player = event.getData().getPlayer();
            playerUUID = player.getUniqueId();
            targetUUID = target.getUniqueId();
            PlayerData playerData = PlayerDataMap.getPlayerDataMap().get(playerUUID);
            playerData.runAction("~onattack",event);
        }else {
            return;
        }

        //MMOLib.plugin.getDamage().damage(this.player.getPlayer(), target, new AttackResult(sweep - event.getAttack().getDamage(), new DamageType[]{DamageType.SKILL, DamageType.PHYSICAL}));
    }

}
