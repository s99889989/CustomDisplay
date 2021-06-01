package com.daxton.customdisplay.task.action.player;

import com.daxton.customdisplay.api.action.ActionMapHandle;
import com.daxton.customdisplay.manager.player.PlayerManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getServer;

public class setMoney {

    public static void setM(LivingEntity self, LivingEntity target, Map<String, String> action_Map, String taskID){
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }

        ActionMapHandle actionMapHandle = new ActionMapHandle(action_Map, self, target);
        //數量
        double amount = actionMapHandle.getDouble(new String[]{"amount","a"},10);

        List<LivingEntity> livingEntityList = actionMapHandle.getLivingEntityListSelf();

        livingEntityList.forEach(livingEntity -> {
            if(livingEntity instanceof Player){
                Player player = (Player) livingEntity;
                if(amount >= 0){
                    PlayerManager.econ.depositPlayer(player, amount);
                }else {
                    PlayerManager.econ.withdrawPlayer(player, amount);
                }

            }
        });

//        if(self instanceof Player){
//            Player player = (Player) self;
//            player.sendMessage(String.format("你有 %s", PlayerManager.econ.format(PlayerManager.econ.getBalance(player.getName()))));
//            EconomyResponse r = PlayerManager.econ.depositPlayer(player, 10);
//            if(r.transactionSuccess()) {
//                player.sendMessage(String.format("你得到 %s 你現在有 %s", PlayerManager.econ.format(r.amount), PlayerManager.econ.format(r.balance)));
//            } else {
//                player.sendMessage(String.format("發生錯誤: %s", r.errorMessage));
//            }
//
//        }

    }

}
