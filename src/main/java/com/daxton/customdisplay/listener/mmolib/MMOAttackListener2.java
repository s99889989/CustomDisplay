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

public class MMOAttackListener2 implements Listener{

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
    public void onAttack(EntityDamageByEntityEvent event){ //PlayerAttackEvent pevent,
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
            physical_STRIKE_POWER = (attack_damage+physical_damage)*((physical_STRIKE_POWER+200)/100);

            /**額外魔法攻擊**/
            PlayerData data = PlayerData.get(player);
            double magical_damage = data.getStats().getStat(StatType.MAGIC_DAMAGE);
            magical_damage = (magical_damage/100);

            /**額外粒子攻擊**/
            double projectile_DAMAGE = MMOPlayerData.get(playerUUID).getStatMap().getStat(PROJECTILE_DAMAGE);
            /**額外技能攻擊**/
            double skill_DAMAGE = MMOPlayerData.get(playerUUID).getStatMap().getStat(SKILL_DAMAGE);

            /**魔法爆擊增幅**/
            double spell_CRITICAL_STRIKE_POWER = MMOPlayerData.get(playerUUID).getStatMap().getStat(SPELL_CRITICAL_STRIKE_POWER);
            spell_CRITICAL_STRIKE_POWER = (spell_CRITICAL_STRIKE_POWER/100) + 1.5;

            /**最終傷害**/
            double final_damage = attack_damage+physical_damage;
//            player.sendMessage("基礎傷害"+attack_damage);
//            player.sendMessage("額外物理攻擊"+physical_damage);
//            player.sendMessage("爆擊增幅"+physical_STRIKE_POWER);

            if(damageType.contains("WEAPON")){
                //player.sendMessage("WEAPON： "+damageType); damageNumber > (physical_STRIKE_POWER-2)
                if(damageNumber > 2 & damageNumber > (physical_STRIKE_POWER-50) ){

                    new PlayerTrigger(player).onCrit(player,target);

                }else {
                    new PlayerTrigger(player).onAttack(player,target);
                }
            }
            if(damageType.contains("MAGIC")){
//                player.sendMessage("顯示傷害: "+damageNumber);
//                player.sendMessage("估算傷害: "+((damageNumberPAE*magical_damage)*spell_CRITICAL_STRIKE_POWER));

                if(damageNumber > ((damageNumberPAE*magical_damage)*spell_CRITICAL_STRIKE_POWER)){

                    new PlayerTrigger(player).onMCrit(player,target);

                }else {

                    new PlayerTrigger(player).onMagic(player,target);

                }

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
