package com.daxton.customdisplay.listener.mmolib;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.Convert;
import com.daxton.customdisplay.api.player.PlayerTrigger;
import com.daxton.customdisplay.api.player.PlayerTrigger2;
import com.daxton.customdisplay.manager.PlaceholderManager;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.stats.StatType;
import net.citizensnpcs.api.CitizensAPI;
import net.mmogroup.mmolib.api.event.PlayerAttackEvent;
import net.mmogroup.mmolib.api.item.NBTItem;
import net.mmogroup.mmolib.api.player.MMOPlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

import static net.mmogroup.mmolib.api.stat.SharedStat.*;
import static net.mmogroup.mmolib.api.stat.SharedStat.SPELL_CRITICAL_STRIKE_POWER;
import static org.bukkit.entity.EntityType.ARMOR_STAND;

public class MMOCoreListener implements Listener {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private UUID playerUUID;

    private UUID targetUUID;

    private double damageNumber = 0;

    private double damageNumberPAE = 0.0;

    private String damageType = "";


    @EventHandler(priority = EventPriority.MONITOR)
    public void onAttack(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof LivingEntity) || event.getEntity().getType() == ARMOR_STAND){
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }

        LivingEntity target = (LivingEntity) event.getEntity();
        damageNumber = event.getFinalDamage();
        player = Convert.convertPlayer(event.getDamager());
        if(player != null){

            String uuidString = player.getUniqueId().toString();
            String tUUIDSTring = target.getUniqueId().toString();


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

            /**額外魔法攻擊**/
            PlayerData data = PlayerData.get(player);
            double magical_damage = data.getStats().getStat(StatType.MAGIC_DAMAGE);
            magical_damage = (magical_damage/100);

            /**魔法爆擊增幅**/
            double spell_CRITICAL_STRIKE_POWER = MMOPlayerData.get(playerUUID).getStatMap().getStat(SPELL_CRITICAL_STRIKE_POWER);
            spell_CRITICAL_STRIKE_POWER = (spell_CRITICAL_STRIKE_POWER/100) + 1.5;


            if (event.isCancelled()) {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>","Miss");
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,"Miss");
                new PlayerTrigger2(player).onAtkMiss(player,target);
                return;
            }else {
                PlaceholderManager.getCd_Placeholder_Map().put(uuidString+"<cd_attack_number>",String.valueOf(damageNumber));
                PlaceholderManager.cd_Attack_Number.put(uuidString+tUUIDSTring,String.valueOf(damageNumber));
            }

            if(damageType.contains("PHYSICAL")){
                if(damageNumber > physical_STRIKE_POWER ){
                    new PlayerTrigger2(player).onCrit(player,target);
                }else {
                    new PlayerTrigger2(player).onAttack(player,target);
                }
            }
            if(damageType.contains("MAGIC")){
                if(damageNumber > ((damageNumberPAE*magical_damage)*spell_CRITICAL_STRIKE_POWER)){
                    new PlayerTrigger2(player).onMCrit(player,target);
                }else {
                    new PlayerTrigger2(player).onMagic(player,target);
                }
            }

        }

    }

    @EventHandler
    public void c(PlayerAttackEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if(Bukkit.getServer().getPluginManager().getPlugin("Citizens") !=null){
            if(CitizensAPI.getNPCRegistry().isNPC(event.getEntity())){
                return;
            }
        }

        damageNumberPAE = event.getAttack().getDamage();
        damageType = event.getAttack().getTypes().toString();
    }



}
