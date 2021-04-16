package com.daxton.customdisplay.listener.customdisplay;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.entity.LookTarget;
import com.daxton.customdisplay.api.item.PlayerEquipment;
import com.daxton.customdisplay.api.player.damageformula.FormulaDelay;
import com.daxton.customdisplay.api.player.data.PlayerData;
import com.daxton.customdisplay.api.player.profession.BossBarSkill;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.manager.ListenerManager;
import com.daxton.customdisplay.manager.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;


public class EquipmentListener implements Listener {

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();
    /**登入時**/
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(@NotNull final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        new PlayerEquipment().reloadEquipment(player,10);


    }

    /**切換手上物品時**/
    @EventHandler(priority = EventPriority.LOW )
    public void onItemHeld(PlayerItemHeldEvent event){

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        String uuidString = player.getUniqueId().toString();
        int key = event.getNewSlot();
        int old = event.getPreviousSlot();
        PlayerData playerData = PlayerManager.getPlayerDataMap().get(playerUUID);
        if(ListenerManager.getCast_On_Stop().get(uuidString) != null){
            boolean cast = ListenerManager.getCast_On_Stop().get(uuidString);
            if(!cast){
                new PlayerEquipment().reloadEquipment(player,key);
            }
        }

        //new PlayerAttribute(player);

        if(ListenerManager.getCast_On_Stop().get(uuidString) != null){
            boolean cast = ListenerManager.getCast_On_Stop().get(uuidString);
            if(cast){
                List<String> action = playerData.skill_Key_Map.get(uuidString+"."+key);
                String skillName = playerData.skill_Name_Map.get(uuidString+"."+key);

                if(skillName != null && action != null && action.size() > 0){
                    /**技能設定檔**/
                    FileConfiguration skillConfig = ConfigMapManager.getFileConfigurationMap().get("Class_Skill_Skills_"+skillName+".yml");
                    /**技能是否需要目標**/
                    boolean needTarget = skillConfig.getBoolean(skillName+".NeedTarget");
                    /**目標最遠距離**/
                    int targetDistance = skillConfig.getInt(skillName+".TargetDistance");

                    LivingEntity target = LookTarget.getLivingTarget(player,targetDistance);
                    if(needTarget){
                        if(target != null){
                            /**技能動作延遲初始化**/
                            if(PlayerManager.cost_Delay_Boolean_Map.get(uuidString) == null){
                                PlayerManager.cost_Delay_Boolean_Map.put(uuidString,true);
                            }
                            /**技能獨立延遲初始化**/
                            if(PlayerManager.skill_Cool_Down_Boolean_Map.get(uuidString+"."+key) == null){
                                PlayerManager.skill_Cool_Down_Boolean_Map.put(uuidString+"."+key,true);
                            }
                            /**技能動作延遲**/
                            /**技能獨立延遲**/
                            if(PlayerManager.cost_Delay_Boolean_Map.get(uuidString) != null && PlayerManager.skill_Cool_Down_Boolean_Map.get(uuidString+"."+key) != null){
                                boolean costDelay = PlayerManager.cost_Delay_Boolean_Map.get(uuidString);
                                boolean coolDown = PlayerManager.skill_Cool_Down_Boolean_Map.get(uuidString+"."+key);
                                if(costDelay && coolDown){ //
                                    PlayerManager.cost_Delay_Boolean_Map.put(uuidString,false);
                                    new FormulaDelay().setCost(player,target,skillName,action,targetDistance);

                                    PlayerManager.skill_Cool_Down_Boolean_Map.put(uuidString+"."+key,false);
                                    new FormulaDelay().skillCD(player,skillName,key);

                                }
                            }

                        }
                    }else {
                        /**技能動作延遲初始化**/
                        if(PlayerManager.cost_Delay_Boolean_Map.get(uuidString) == null){
                            PlayerManager.cost_Delay_Boolean_Map.put(uuidString,true);
                        }
                        /**技能獨立延遲初始化**/
                        if(PlayerManager.skill_Cool_Down_Boolean_Map.get(uuidString+"."+key) == null){
                            PlayerManager.skill_Cool_Down_Boolean_Map.put(uuidString+"."+key,true);
                        }
                        /**技能動作延遲**/
                        /**技能獨立延遲**/
                        if(PlayerManager.cost_Delay_Boolean_Map.get(uuidString) != null && PlayerManager.skill_Cool_Down_Boolean_Map.get(uuidString+"."+key) != null){
                            boolean costDelay = PlayerManager.cost_Delay_Boolean_Map.get(uuidString);
                            boolean coolDown = PlayerManager.skill_Cool_Down_Boolean_Map.get(uuidString+"."+key);
                            if(costDelay && coolDown){
                                PlayerManager.cost_Delay_Boolean_Map.put(uuidString,false);
                                new FormulaDelay().setCost(player,target,skillName,action,targetDistance);

                                PlayerManager.skill_Cool_Down_Boolean_Map.put(uuidString+"."+key,false);
                                new FormulaDelay().skillCD(player,skillName,key);

                            }
                        }
                    }


                }
            }

        }


        if(PlayerManager.even_Cancel_Map.get(uuidString) != null){
            boolean cc = PlayerManager.even_Cancel_Map.get(uuidString);
            if(cc == true){
                event.setCancelled(true);
            }
        }

        //new PlayerConfig(player).setAttrStats(player);
    }

    /**按下F時**/
    @EventHandler(priority = EventPriority.LOW)
    public void onSwapHand(PlayerSwapHandItemsEvent event){
        Player player = event.getPlayer();
        String uuidString = player.getUniqueId().toString();
        /**主手位置**/
        int key = player.getInventory().getHeldItemSlot();

        if(ListenerManager.getCast_On_Stop().get(uuidString) != null){
            boolean b = ListenerManager.getCast_On_Stop().get(uuidString);
            if(b){
                new BossBarSkill().closeSkill(player);
            }else {
                new BossBarSkill().openSkill(player,key);
            }
        }

        event.setCancelled(true);


//        Player player = event.getPlayer();
//        String uuidString = player.getUniqueId().toString();
//        if(PlayerDataMap.even_Cancel_Map.get(uuidString) != null){
//            event.setCancelled(PlayerDataMap.even_Cancel_Map.get(uuidString));
//            player.sendMessage("PLE"+true);
//        }
    }

    /**關閉背包**/
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        new PlayerEquipment().reloadEquipment(player,10);

        //new PlayerAttribute(player);
        //new PlayerConfig(player).setAttrStats(player);
    }

//    /**移動物品時**/
//    @EventHandler(
//            priority = EventPriority.HIGH,
//            ignoreCancelled = true
//    )
//    public void registerClicks(InventoryClickEvent event) {
//        if(event.getWhoClicked() instanceof Player){
//
//            Player player = ((Player) event.getWhoClicked()).getPlayer();
//
//        }
//
//    }


//    @EventHandler
//    public void onAction(PlayerInteractEvent event){
//        Player player = event.getPlayer();
//        //cd.getLogger().info(player.getName()+" : "+event.getAction().toString());
//        //player.sendMessage();
//
//
//
////        int[] arrau = {8,4,6};
////        List<String> s = new ArrayList<>();
////        Arrays.stream(s.toArray()).forEach(System.out::println);
//
//    }


}
