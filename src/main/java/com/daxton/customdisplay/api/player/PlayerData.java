package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.manager.ConfigMapManager;
import com.daxton.customdisplay.task.action.JudgmentAction;
import net.mmogroup.mmolib.api.event.PlayerAttackEvent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.serverct.ersha.jd.event.AttrEntityCritEvent;
import org.serverct.ersha.jd.event.AttrEntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private FileConfiguration fileConfiguration;

    private List<String> playerActionList;

    private List<String> onAttakList = new ArrayList<>();
    private List<String> onCrit = new ArrayList<>();
    private List<String> onDamagedList = new ArrayList<>();
    private List<String> onJoinList = new ArrayList<>();

    public PlayerData(Player player){
        this.player = player;
        setPlayerActionList();
        setActionList();
    }
    /**獲取動作列表**/
    public void setPlayerActionList() {
        for(String configName : ConfigMapManager.getFileConfigurationNameMap().values()){

            if(configName.contains("Players_"+player.getName()+".yml")){
                fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Players_"+player.getName()+".yml");
                break;
            }else{
                fileConfiguration = ConfigMapManager.getFileConfigurationMap().get("Players_Default.yml");
            }
        }
        playerActionList = fileConfiguration.getStringList("Action");
    }

    public void setActionList(){
        if(playerActionList.size() > 0){
            for(String actionString : playerActionList){
                if(actionString.toLowerCase().contains("~onattack")){
                    onAttakList.add(actionString);
                }
                if(actionString.toLowerCase().contains("~ondamaged")){
                    onDamagedList.add(actionString);
                }
                if(actionString.toLowerCase().contains("~onjoin")){
                    onJoinList.add(actionString);
                }
                if(actionString.toLowerCase().contains("~oncrit")){
                    onCrit.add(actionString);
                }
            }
        }
    }

    public void runAction(String type, Event event){
        if(type.toLowerCase().contains("~onattack") && onAttakList.size() > 0){

            for(String actionString : onAttakList){
                LivingEntity target = null;
                double damageNumber = 0;
                if(Bukkit.getPluginManager().isPluginEnabled("MMOLib")){
                    PlayerAttackEvent playerAttackEvent = (PlayerAttackEvent) event;
                    target = playerAttackEvent.getEntity();
                    damageNumber = playerAttackEvent.getAttack().getDamage();
                }else if(Bukkit.getPluginManager().isPluginEnabled("AttributePlus")){
                    AttrEntityDamageEvent attrEntityDamageEvent = (AttrEntityDamageEvent) event;
                    target = (LivingEntity) attrEntityDamageEvent.getEntity();
                    damageNumber = attrEntityDamageEvent.getDamage();
                }else {
                    EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
                    target = (LivingEntity) entityDamageByEntityEvent.getEntity();
                    damageNumber = entityDamageByEntityEvent.getFinalDamage();
                }
                //target.getWorld().spawnParticle(Particle.LAVA, target.getLocation().add(0.0, target.getHeight() / 2.0, 0.0), 8, 0.0, 0.0, 0.0, 0.15);
                new JudgmentAction().execute(player,target,actionString,damageNumber,String.valueOf((int)(Math.random()*100000)));
            }
        }
        if(type.toLowerCase().contains("~oncrit") && onCrit.size() > 0){

            for(String actionString : onCrit){
                LivingEntity target = null;
                double damageNumber = 0;
                if(Bukkit.getPluginManager().isPluginEnabled("MMOLib")){
                    PlayerAttackEvent playerAttackEvent = (PlayerAttackEvent) event;
                    target = playerAttackEvent.getEntity();
                    damageNumber = playerAttackEvent.getAttack().getDamage();
                }else if(Bukkit.getPluginManager().isPluginEnabled("AttributePlus")){
                    AttrEntityCritEvent attrEntityDamageEvent = (AttrEntityCritEvent) event;
                    target = (LivingEntity) attrEntityDamageEvent.getEntity();
                    damageNumber = attrEntityDamageEvent.getCritDamage();
                }else {
                    EntityDamageByEntityEvent entityDamageByEntityEvent = (EntityDamageByEntityEvent) event;
                    target = (LivingEntity) entityDamageByEntityEvent.getEntity();
                    damageNumber = entityDamageByEntityEvent.getFinalDamage();
                }
                //target.getWorld().spawnParticle(Particle.LAVA, target.getLocation().add(0.0, target.getHeight() / 2.0, 0.0), 8, 0.0, 0.0, 0.0, 0.15);
                new JudgmentAction().execute(player,target,actionString,damageNumber,"~oncrit"+(int)(Math.random()*100000));
            }
        }
        if(type.toLowerCase().contains("~ondamaged") && onDamagedList.size() > 0){
            for(String actionString : onDamagedList){
                new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
            }
        }
        if(type.toLowerCase().contains("~onjoin") && onJoinList.size() > 0){
            for(String actionString : onJoinList){
                new JudgmentAction().execute(player,actionString,String.valueOf((int)(Math.random()*100000)));
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public List<String> getPlayerActionList() {
        return playerActionList;
    }
}
