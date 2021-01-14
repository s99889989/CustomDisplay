package com.daxton.customdisplay.api.player;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.StringConversion;
import com.daxton.customdisplay.api.other.Arithmetic;
import com.daxton.customdisplay.api.other.NumberUtil;
import com.daxton.customdisplay.manager.PermissionManager;
import com.daxton.customdisplay.manager.PlayerDataMap;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class PlayerData {

    CustomDisplay cd = CustomDisplay.getCustomDisplay();

    private Player player;

    private FileConfiguration fileConfiguration;

    private BukkitRunnable bukkitRunnable;

    private String uuidString = "";

    private double mana;

    /**動作列表**/
    private List<String> playerActionList = new ArrayList<>();

    /**觸發的動作列表**/
    private Map<String,List<String>> action_Trigger_Map = new HashMap<>();

    public PlayerData(Player player){
        this.player = player;
        uuidString = player.getUniqueId().toString();
        String attackCore = cd.getConfigManager().config.getString("AttackCore");

        /**屬性初始值**/
        if(attackCore.toLowerCase().contains("customcore")){
            new PlayerBukkitAttribute().setDefault(player);
            PlayerDataMap.getCore_Attribute_Map().put(uuidString,new CoreAttribute(player));
            PlayerDataMap.getCore_Attribute_Map().get(uuidString).setDefault();

            mana = PlayerDataMap.getCore_Attribute_Map().get(uuidString).getAttribute("Health_Regeneration");

            File customCoreFile = new File(cd.getDataFolder(),"Class/CustomCore.yml");
            FileConfiguration customCoreConfig = YamlConfiguration.loadConfiguration(customCoreFile);
            String attackSpeedString = customCoreConfig.getString("Formula.Attack_Speed.Speed");
            attackSpeedString = new StringConversion(player,null,attackSpeedString,"Character").valueConv();
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
            File playerFilePatch = new File(cd.getDataFolder(),"Players/"+uuidString+"/"+uuidString+".yml");
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFilePatch);
            for (int i = 1 ;i < 9 ;i++){
                String bindSkill = playerConfig.getString(uuidString+".Binds."+i+".SkillName");
                if(!(bindSkill.equals("null"))){
                    int key = i -1;
                    File skillFile = new File(cd.getDataFolder(),"Class/Skill/Skills/"+bindSkill+".yml");
                    FileConfiguration skillConfig = YamlConfiguration.loadConfiguration(skillFile);
                    List<String> skillAction = skillConfig.getStringList(bindSkill+".Action");
                    PlayerDataMap.skill_Key_Map.put(uuidString+"."+key,skillAction);
                }

            }

        }


        new PlayerConfig(player).createFile();

        setPlayerActionList();
        setActionList();
        //health_Regeneration(player);
    }

    public void health_Regeneration(Player player){
        bukkitRunnable = new BukkitRunnable(){
            @Override
            public void run() {

                if(player.getHealth() != player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()){
                    double giveHealth = player.getHealth()+PlayerDataMap.getCore_Attribute_Map().get(uuidString).getAttribute("Health_Regeneration");
                    double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();

                    if(giveHealth > maxHealth){
                        giveHealth = giveHealth - (giveHealth - maxHealth);
                    }
                    //cd.getLogger().info("回血:"+giveHealth);
                    //player.sendMessage("回血:"+PlayerDataMap.getCore_Attribute_Map().get(uuidString).getAttribute("Health_Regeneration"));
                    player.setHealth(giveHealth);
                }


            }
        };
        bukkitRunnable.runTaskTimer(cd,0,200);
    }


    /**獲取動作列表**/
    public void setPlayerActionList() {
        String uuidString = player.getUniqueId().toString();
        playerActionList.clear();
        File inputFile = new File(cd.getDataFolder(),"Players/"+uuidString+"/"+uuidString+".yml");
        FileConfiguration inputConfig = YamlConfiguration.loadConfiguration(inputFile);
        List<String> setList = inputConfig.getStringList(uuidString+".Action");
        List<String> thisList = new ArrayList<>();
        for(String set : setList){
            File inputFile2 = new File(cd.getDataFolder(),"Class/Action/"+set+".yml");
            FileConfiguration inputConfig2 = YamlConfiguration.loadConfiguration(inputFile2);
            List<String> actionList = inputConfig2.getStringList("Action");
            for(String string : actionList){
                thisList.add(string);
            }


        }

        for(String stringConfig : PermissionManager.getPermission_String_Map().values()){
            if(player.hasPermission(stringConfig)){
                for(String list : PermissionManager.getPermission_FileConfiguration_Map().get(stringConfig).getStringList("Action")){
                    thisList.add(list);
                }
            }
        }

        playerActionList = thisList.stream().distinct().collect(Collectors.toList());


    }

    public void setActionList(){
        if(playerActionList.size() > 0){
            for(String actionString : playerActionList){
                /**當攻擊時**/
                if(actionString.toLowerCase().contains("~onattack")){
                    if(action_Trigger_Map.get("~onattack") == null){
                        action_Trigger_Map.put("~onattack",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onattack") != null){
                        action_Trigger_Map.get("~onattack").add(actionString);
                    }
                }
                /**當爆擊時**/
                if(actionString.toLowerCase().contains("~oncrit")){
                    if(action_Trigger_Map.get("~oncrit") == null){
                        action_Trigger_Map.put("~oncrit",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~oncrit") != null){
                        action_Trigger_Map.get("~oncrit").add(actionString);
                    }
                }
                /**當魔法攻擊時**/
                if(actionString.toLowerCase().contains("~onmagic")){
                    if(action_Trigger_Map.get("~onmagic") == null){
                        action_Trigger_Map.put("~onmagic",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onmagic") != null){
                        action_Trigger_Map.get("~onmagic").add(actionString);
                    }
                }
                /**當魔法攻擊爆擊時**/
                if(actionString.toLowerCase().contains("~onmcrit")){
                    if(action_Trigger_Map.get("~onmcrit") == null){
                        action_Trigger_Map.put("~onmcrit",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onmcrit") != null){
                        action_Trigger_Map.get("~onmcrit").add(actionString);
                    }
                }
                /**當攻擊失敗時**/
                if(actionString.toLowerCase().contains("~onatkmiss")){
                    if(action_Trigger_Map.get("~onatkmiss") == null){
                        action_Trigger_Map.put("~onatkmiss",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onatkmiss") != null){
                        action_Trigger_Map.get("~onatkmiss").add(actionString);
                    }
                }
                /**被攻擊時**/
                if(actionString.toLowerCase().contains("~ondamaged")){
                    if(action_Trigger_Map.get("~ondamaged") == null){
                        action_Trigger_Map.put("~ondamaged",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~ondamaged") != null){
                        action_Trigger_Map.get("~ondamaged").add(actionString);
                    }
                }
                /**當回血時**/
                if(actionString.toLowerCase().contains("~onregainhealth")){
                    if(action_Trigger_Map.get("~onregainhealth") == null){
                        action_Trigger_Map.put("~onregainhealth",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onregainhealth") != null){
                        action_Trigger_Map.get("~onregainhealth").add(actionString);
                    }
                }
                /**當登入時**/
                if(actionString.toLowerCase().contains("~onjoin")){
                    if(action_Trigger_Map.get("~onjoin") == null){
                        action_Trigger_Map.put("~onjoin",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onjoin") != null){
                        action_Trigger_Map.get("~onjoin").add(actionString);
                    }
                }
                /**當登出時**/
                if(actionString.toLowerCase().contains("~onquit")){
                    if(action_Trigger_Map.get("~onquit") == null){
                        action_Trigger_Map.put("~onquit",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onquit") != null){
                        action_Trigger_Map.get("~onquit").add(actionString);
                    }
                }
                /**移動時**/
                if(actionString.toLowerCase().contains("~onmove")){
                    if(action_Trigger_Map.get("~onmove") == null){
                        action_Trigger_Map.put("~onmove",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onmove") != null){
                        action_Trigger_Map.get("~onmove").add(actionString);
                    }
                }
                /**蹲下時**/
                if(actionString.toLowerCase().contains("~onsneak")){
                    if(action_Trigger_Map.get("~onsneak") == null){
                        action_Trigger_Map.put("~onsneak",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onsneak") != null){
                        action_Trigger_Map.get("~onsneak").add(actionString);
                    }
                }
                /**站起來時**/
                if(actionString.toLowerCase().contains("~onstandup")){
                    if(action_Trigger_Map.get("~onstandup") == null){
                        action_Trigger_Map.put("~onstandup",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onstandup") != null){
                        action_Trigger_Map.get("~onstandup").add(actionString);
                    }
                }
                /**當死亡時**/
                if(actionString.toLowerCase().contains("~ondeath")){
                    if(action_Trigger_Map.get("~ondeath") == null){
                        action_Trigger_Map.put("~ondeath",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~ondeath") != null){
                        action_Trigger_Map.get("~ondeath").add(actionString);
                    }
                }
                /**當按下案件F時，一開始會觸發為ON，登出重新計算**/
                if(actionString.toLowerCase().contains("~onkeyfon")){
                    if(action_Trigger_Map.get("~onkeyfon") == null){
                        action_Trigger_Map.put("~onkeyfon",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkeyfon") != null){
                        action_Trigger_Map.get("~onkeyfon").add(actionString);
                    }
                }
                /**再按下案件F時，觸發為OFF，登出重新計算**/
                if(actionString.toLowerCase().contains("~onkeyfoff")){
                    if(action_Trigger_Map.get("~onkeyfoff") == null){
                        action_Trigger_Map.put("~onkeyfoff",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkeyfoff") != null){
                        action_Trigger_Map.get("~onkeyfoff").add(actionString);
                    }
                }
                /**當切換到物品欄1時**/
                if(actionString.toLowerCase().contains("~onkey1")){
                    if(action_Trigger_Map.get("~onkey1") == null){
                        action_Trigger_Map.put("~onkey1",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey1") != null){
                        action_Trigger_Map.get("~onkey1").add(actionString);
                    }
                }
                /**當切換到物品欄2時**/
                if(actionString.toLowerCase().contains("~onkey2")){
                    if(action_Trigger_Map.get("~onkey2") == null){
                        action_Trigger_Map.put("~onkey2",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey2") != null){
                        action_Trigger_Map.get("~onkey2").add(actionString);
                    }
                }
                /**當切換到物品欄3時**/
                if(actionString.toLowerCase().contains("~onkey3")){
                    if(action_Trigger_Map.get("~onkey3") == null){
                        action_Trigger_Map.put("~onkey3",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey3") != null){
                        action_Trigger_Map.get("~onkey3").add(actionString);
                    }
                }
                /**當切換到物品欄4時**/
                if(actionString.toLowerCase().contains("~onkey4")){
                    if(action_Trigger_Map.get("~onkey4") == null){
                        action_Trigger_Map.put("~onkey4",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey4") != null){
                        action_Trigger_Map.get("~onkey4").add(actionString);
                    }
                }
                /**當切換到物品欄5時**/
                if(actionString.toLowerCase().contains("~onkey5")){
                    if(action_Trigger_Map.get("~onkey5") == null){
                        action_Trigger_Map.put("~onkey5",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey5") != null){
                        action_Trigger_Map.get("~onkey5").add(actionString);
                    }
                }
                /**當切換到物品欄6時**/
                if(actionString.toLowerCase().contains("~onkey6")){
                    if(action_Trigger_Map.get("~onkey6") == null){
                        action_Trigger_Map.put("~onkey6",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey6") != null){
                        action_Trigger_Map.get("~onkey6").add(actionString);
                    }
                }
                /**當切換到物品欄7時**/
                if(actionString.toLowerCase().contains("~onkey7")){
                    if(action_Trigger_Map.get("~onkey7") == null){
                        action_Trigger_Map.put("~onkey7",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey7") != null){
                        action_Trigger_Map.get("~onkey7").add(actionString);
                    }
                }
                /**當切換到物品欄8時**/
                if(actionString.toLowerCase().contains("~onkey8")){
                    if(action_Trigger_Map.get("~onkey8") == null){
                        action_Trigger_Map.put("~onkey8",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey8") != null){
                        action_Trigger_Map.get("~onkey8").add(actionString);
                    }
                }
                /**當切換到物品欄9時**/
                if(actionString.toLowerCase().contains("~onkey9")){
                    if(action_Trigger_Map.get("~onkey9") == null){
                        action_Trigger_Map.put("~onkey9",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onkey9") != null){
                        action_Trigger_Map.get("~onkey9").add(actionString);
                    }
                }
                /**當說話時**/
                if(actionString.toLowerCase().contains("~onchat")){
                    if(action_Trigger_Map.get("~onchat") == null){
                        action_Trigger_Map.put("~onchat",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onchat") != null){
                        action_Trigger_Map.get("~onchat").add(actionString);
                    }
                }
                /**當輸入指令時**/
                if(actionString.toLowerCase().contains("~oncommand")){
                    if(action_Trigger_Map.get("~oncommand") == null){
                        action_Trigger_Map.put("~oncommand",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~oncommand") != null){
                        action_Trigger_Map.get("~oncommand").add(actionString);
                    }
                }
                /**當等級提升時**/
                if(actionString.toLowerCase().contains("~onlevelup")){
                    if(action_Trigger_Map.get("~onlevelup") == null){
                        action_Trigger_Map.put("~onlevelup",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onlevelup") != null){
                        action_Trigger_Map.get("~onlevelup").add(actionString);
                    }
                }
                /**當等級降低時**/
                if(actionString.toLowerCase().contains("~onleveldown")){
                    if(action_Trigger_Map.get("~onleveldown") == null){
                        action_Trigger_Map.put("~onleveldown",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onleveldown") != null){
                        action_Trigger_Map.get("~onleveldown").add(actionString);
                    }
                }
                /**當獲得經驗值時**/
                if(actionString.toLowerCase().contains("~onexpup")){
                    if(action_Trigger_Map.get("~onexpup") == null){
                        action_Trigger_Map.put("~onexpup",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onexpup") != null){
                        action_Trigger_Map.get("~onexpup").add(actionString);
                    }
                }
                /**當失去經驗值時**/
                if(actionString.toLowerCase().contains("~onexpdown")){
                    if(action_Trigger_Map.get("~onexpdown") == null){
                        action_Trigger_Map.put("~onexpdown",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onexpdown") != null){
                        action_Trigger_Map.get("~onexpdown").add(actionString);
                    }
                }
                /**當怪物死亡時.(死亡原因必須是該玩家)**/
                if(actionString.toLowerCase().contains("~onmobdeath")){
                    if(action_Trigger_Map.get("~onmobdeath") == null){
                        action_Trigger_Map.put("~onmobdeath",new ArrayList<>());
                    }
                    if(action_Trigger_Map.get("~onmobdeath") != null){
                        action_Trigger_Map.get("~onmobdeath").add(actionString);
                    }
                }

            }
        }
    }

    public Player getPlayer() {
        return player;
    }
    /**動作列表**/
    public List<String> getPlayerActionList() {
        return playerActionList;
    }
    /**觸發的動作列表**/
    public Map<String, List<String>> getAction_Trigger_Map() {
        return action_Trigger_Map;
    }

    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable;
    }
}
