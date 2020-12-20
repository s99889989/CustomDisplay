package com.daxton.customdisplay.listener.mmolib;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.stats.StatType;
import net.mmogroup.mmolib.api.event.PlayerAttackEvent;
import net.mmogroup.mmolib.api.item.NBTItem;
import net.mmogroup.mmolib.api.player.MMOPlayerData;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static net.mmogroup.mmolib.api.stat.SharedStat.*;
import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class MMOLibListener implements Listener{

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private LivingEntity target;

    private UUID playerUUID;

    private UUID targetUUID;

    private double damageNumberPAE = 0.0;

    private String damageType = "";



    @EventHandler(
            priority = EventPriority.MONITOR,
            ignoreCancelled = true
    )
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        target = (LivingEntity) event.getEntity();
        double damageNumber = event.getFinalDamage();
        PlaceholderManager.getDamage_Number_Map().put(event.getDamager().getUniqueId(),String.valueOf(damageNumber));
        if(event.getDamager() instanceof Player){
            player = (Player) event.getDamager();
            playerUUID = player.getUniqueId();
            targetUUID = target.getUniqueId();


            ItemStack itemStack = player.getInventory().getItemInMainHand();
            NBTItem nbtItem = NBTItem.get(itemStack);
            boolean b = nbtItem.hasType();
            String s = NBTItem.get(itemStack).getString("MMOITEMS_ITEM_NAME");

            /**基礎傷害**/
            double attack_damage = MMOPlayerData.get(playerUUID).getStatMap().getStat(ATTACK_DAMAGE);
            /**額外物理攻擊**/
            double physical_damage = MMOPlayerData.get(playerUUID).getStatMap().getStat(PHYSICAL_DAMAGE);
            physical_damage = (attack_damage/100)*physical_damage;
            /**爆擊增幅**/
            double physical_STRIKE_POWER = MMOPlayerData.get(playerUUID).getStatMap().getStat(CRITICAL_STRIKE_POWER);
            physical_STRIKE_POWER = (attack_damage+physical_damage)*((physical_STRIKE_POWER+180)/100);

            if(damageType.contains("WEAPON")){
                if(damageNumber > physical_STRIKE_POWER ){
                    new PlayerTrigger(player).onCrit(player,target);
                }else {
                    new PlayerTrigger(player).onAttack(player,target);
                }
            }

            if(damageType.contains("MAGIC")){
                new PlayerTrigger(player).onMagic(player,target);
            }

        }else {
            return;
        }

    }


    @EventHandler(
            //ignoreCancelled = true,
            //priority = EventPriority.MONITOR
    )
    public void c(PlayerAttackEvent event) {
//        StatMap stats = event.getData().getStatMap();
//        cd.getLogger().info("額外魔法攻擊: "+stats.getStat(MAGICAL_DAMAGE));
        damageNumberPAE = event.getAttack().getDamage();
        damageType = event.getAttack().getTypes().toString();
    }



}
