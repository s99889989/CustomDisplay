package com.daxton.customdisplay.api.player.data.set;

import com.daxton.customdisplay.api.config.CustomLineConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerAction2 {

    /**觸發的動作列表**/
    private Map<String,List<CustomLineConfig>> action_Trigger_Map = new HashMap<>();

    public PlayerAction2(){

    }
    public PlayerAction2(Map<String, List<CustomLineConfig>> action_Trigger_Map){
        this.action_Trigger_Map = action_Trigger_Map;
    }
    /**丟入玩家動作列表，回傳動作Map**/
    public Map<String,List<CustomLineConfig>> setPlayerAction(List<String> actionList){
        if(actionList.size() > 0){
            for(String actionString : actionList){

                setAction(actionString);

            }
        }

        return action_Trigger_Map;
    }



    public void setAction(String actionString){

        /**當攻擊時**/
        if(actionString.toLowerCase().contains("~onattack")){
            if(action_Trigger_Map.get("~onattack") == null){
                action_Trigger_Map.put("~onattack",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onattack") != null){
                action_Trigger_Map.get("~onattack").add(new CustomLineConfig(actionString));
            }
        }
        /**當爆擊時**/
        if(actionString.toLowerCase().contains("~oncrit")){
            if(action_Trigger_Map.get("~oncrit") == null){
                action_Trigger_Map.put("~oncrit",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~oncrit") != null){
                action_Trigger_Map.get("~oncrit").add(new CustomLineConfig(actionString));
            }
        }
        /**當魔法攻擊時**/
        if(actionString.toLowerCase().contains("~onmagic")){
            if(action_Trigger_Map.get("~onmagic") == null){
                action_Trigger_Map.put("~onmagic",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onmagic") != null){
                action_Trigger_Map.get("~onmagic").add(new CustomLineConfig(actionString));
            }
        }
        /**當魔法攻擊爆擊時**/
        if(actionString.toLowerCase().contains("~onmcrit")){
            if(action_Trigger_Map.get("~onmcrit") == null){
                action_Trigger_Map.put("~onmcrit",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onmcrit") != null){
                action_Trigger_Map.get("~onmcrit").add(new CustomLineConfig(actionString));
            }
        }
        /**當攻擊失敗時**/
        if(actionString.toLowerCase().contains("~onatkmiss")){
            if(action_Trigger_Map.get("~onatkmiss") == null){
                action_Trigger_Map.put("~onatkmiss",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onatkmiss") != null){
                action_Trigger_Map.get("~onatkmiss").add(new CustomLineConfig(actionString));
            }
        }
        /**被攻擊時**/
        if(actionString.toLowerCase().contains("~ondamaged")){
            if(action_Trigger_Map.get("~ondamaged") == null){
                action_Trigger_Map.put("~ondamaged",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~ondamaged") != null){
                action_Trigger_Map.get("~ondamaged").add(new CustomLineConfig(actionString));
            }
        }
        /**被攻擊失敗時**/
        if(actionString.toLowerCase().contains("~ondamagedmiss")){
            if(action_Trigger_Map.get("~ondamagedmiss") == null){
                action_Trigger_Map.put("~ondamagedmiss",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~ondamagedmiss") != null){
                action_Trigger_Map.get("~ondamagedmiss").add(new CustomLineConfig(actionString));
            }
        }
        /**當回血時**/
        if(actionString.toLowerCase().contains("~onregainhealth")){
            if(action_Trigger_Map.get("~onregainhealth") == null){
                action_Trigger_Map.put("~onregainhealth",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onregainhealth") != null){
                action_Trigger_Map.get("~onregainhealth").add(new CustomLineConfig(actionString));
            }
        }
        /**當登入時**/
        if(actionString.toLowerCase().contains("~onjoin")){
            if(action_Trigger_Map.get("~onjoin") == null){
                action_Trigger_Map.put("~onjoin",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onjoin") != null){
                action_Trigger_Map.get("~onjoin").add(new CustomLineConfig(actionString));
            }
        }
        /**當登出時**/
        if(actionString.toLowerCase().contains("~onquit")){
            if(action_Trigger_Map.get("~onquit") == null){
                action_Trigger_Map.put("~onquit",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onquit") != null){
                action_Trigger_Map.get("~onquit").add(new CustomLineConfig(actionString));
            }
        }
        /**移動時**/
        if(actionString.toLowerCase().contains("~onmove")){
            if(action_Trigger_Map.get("~onmove") == null){
                action_Trigger_Map.put("~onmove",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onmove") != null){
                action_Trigger_Map.get("~onmove").add(new CustomLineConfig(actionString));
            }
        }
        /**蹲下時**/
        if(actionString.toLowerCase().contains("~onsneak")){
            if(action_Trigger_Map.get("~onsneak") == null){
                action_Trigger_Map.put("~onsneak",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onsneak") != null){
                action_Trigger_Map.get("~onsneak").add(new CustomLineConfig(actionString));
            }
        }
        /**站起來時**/
        if(actionString.toLowerCase().contains("~onstandup")){
            if(action_Trigger_Map.get("~onstandup") == null){
                action_Trigger_Map.put("~onstandup",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onstandup") != null){
                action_Trigger_Map.get("~onstandup").add(new CustomLineConfig(actionString));
            }
        }
        /**當死亡時**/
        if(actionString.toLowerCase().contains("~ondeath")){
            if(action_Trigger_Map.get("~ondeath") == null){
                action_Trigger_Map.put("~ondeath",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~ondeath") != null){
                action_Trigger_Map.get("~ondeath").add(new CustomLineConfig(actionString));
            }
        }
        /**當按下案件F時，一開始會觸發為ON，登出重新計算**/
        if(actionString.toLowerCase().contains("~onkeyfon")){
            if(action_Trigger_Map.get("~onkeyfon") == null){
                action_Trigger_Map.put("~onkeyfon",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onkeyfon") != null){
                action_Trigger_Map.get("~onkeyfon").add(new CustomLineConfig(actionString));
            }
        }
        /**再按下案件F時，觸發為OFF，登出重新計算**/
        if(actionString.toLowerCase().contains("~onkeyfoff")){
            if(action_Trigger_Map.get("~onkeyfoff") == null){
                action_Trigger_Map.put("~onkeyfoff",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onkeyfoff") != null){
                action_Trigger_Map.get("~onkeyfoff").add(new CustomLineConfig(actionString));
            }
        }
        /**當切換到物品欄1時**/
        if(actionString.toLowerCase().contains("~onkey1")){
            if(action_Trigger_Map.get("~onkey1") == null){
                action_Trigger_Map.put("~onkey1",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onkey1") != null){
                action_Trigger_Map.get("~onkey1").add(new CustomLineConfig(actionString));
            }
        }
        /**當切換到物品欄2時**/
        if(actionString.toLowerCase().contains("~onkey2")){
            if(action_Trigger_Map.get("~onkey2") == null){
                action_Trigger_Map.put("~onkey2",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onkey2") != null){
                action_Trigger_Map.get("~onkey2").add(new CustomLineConfig(actionString));
            }
        }
        /**當切換到物品欄3時**/
        if(actionString.toLowerCase().contains("~onkey3")){
            if(action_Trigger_Map.get("~onkey3") == null){
                action_Trigger_Map.put("~onkey3",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onkey3") != null){
                action_Trigger_Map.get("~onkey3").add(new CustomLineConfig(actionString));
            }
        }
        /**當切換到物品欄4時**/
        if(actionString.toLowerCase().contains("~onkey4")){
            if(action_Trigger_Map.get("~onkey4") == null){
                action_Trigger_Map.put("~onkey4",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onkey4") != null){
                action_Trigger_Map.get("~onkey4").add(new CustomLineConfig(actionString));
            }
        }
        /**當切換到物品欄5時**/
        if(actionString.toLowerCase().contains("~onkey5")){
            if(action_Trigger_Map.get("~onkey5") == null){
                action_Trigger_Map.put("~onkey5",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onkey5") != null){
                action_Trigger_Map.get("~onkey5").add(new CustomLineConfig(actionString));
            }
        }
        /**當切換到物品欄6時**/
        if(actionString.toLowerCase().contains("~onkey6")){
            if(action_Trigger_Map.get("~onkey6") == null){
                action_Trigger_Map.put("~onkey6",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onkey6") != null){
                action_Trigger_Map.get("~onkey6").add(new CustomLineConfig(actionString));
            }
        }
        /**當切換到物品欄7時**/
        if(actionString.toLowerCase().contains("~onkey7")){
            if(action_Trigger_Map.get("~onkey7") == null){
                action_Trigger_Map.put("~onkey7",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onkey7") != null){
                action_Trigger_Map.get("~onkey7").add(new CustomLineConfig(actionString));
            }
        }
        /**當切換到物品欄8時**/
        if(actionString.toLowerCase().contains("~onkey8")){
            if(action_Trigger_Map.get("~onkey8") == null){
                action_Trigger_Map.put("~onkey8",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onkey8") != null){
                action_Trigger_Map.get("~onkey8").add(new CustomLineConfig(actionString));
            }
        }
        /**當切換到物品欄9時**/
        if(actionString.toLowerCase().contains("~onkey9")){
            if(action_Trigger_Map.get("~onkey9") == null){
                action_Trigger_Map.put("~onkey9",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onkey9") != null){
                action_Trigger_Map.get("~onkey9").add(new CustomLineConfig(actionString));
            }
        }
        /**當說話時**/
        if(actionString.toLowerCase().contains("~onchat")){
            if(action_Trigger_Map.get("~onchat") == null){
                action_Trigger_Map.put("~onchat",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onchat") != null){
                action_Trigger_Map.get("~onchat").add(new CustomLineConfig(actionString));
            }
        }
        /**當輸入指令時**/
        if(actionString.toLowerCase().contains("~oncommand")){
            if(action_Trigger_Map.get("~oncommand") == null){
                action_Trigger_Map.put("~oncommand",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~oncommand") != null){
                action_Trigger_Map.get("~oncommand").add(new CustomLineConfig(actionString));
            }
        }
        /**當等級提升時**/
        if(actionString.toLowerCase().contains("~onlevelup")){
            if(action_Trigger_Map.get("~onlevelup") == null){
                action_Trigger_Map.put("~onlevelup",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onlevelup") != null){
                action_Trigger_Map.get("~onlevelup").add(new CustomLineConfig(actionString));
            }
        }
        /**當等級降低時**/
        if(actionString.toLowerCase().contains("~onleveldown")){
            if(action_Trigger_Map.get("~onleveldown") == null){
                action_Trigger_Map.put("~onleveldown",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onleveldown") != null){
                action_Trigger_Map.get("~onleveldown").add(new CustomLineConfig(actionString));
            }
        }
        /**當獲得經驗值時**/
        if(actionString.toLowerCase().contains("~onexpup")){
            if(action_Trigger_Map.get("~onexpup") == null){
                action_Trigger_Map.put("~onexpup",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onexpup") != null){
                action_Trigger_Map.get("~onexpup").add(new CustomLineConfig(actionString));
            }
        }
        /**當失去經驗值時**/
        if(actionString.toLowerCase().contains("~onexpdown")){
            if(action_Trigger_Map.get("~onexpdown") == null){
                action_Trigger_Map.put("~onexpdown",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onexpdown") != null){
                action_Trigger_Map.get("~onexpdown").add(new CustomLineConfig(actionString));
            }
        }
        /**當怪物死亡時.(死亡原因必須是該玩家)**/
        if(actionString.toLowerCase().contains("~onmobdeath")){
            if(action_Trigger_Map.get("~onmobdeath") == null){
                action_Trigger_Map.put("~onmobdeath",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onmobdeath") != null){
                action_Trigger_Map.get("~onmobdeath").add(new CustomLineConfig(actionString));
            }
        }
        /**當MythicMobs怪物死亡時.(死亡原因必須是該玩家)**/
        if(actionString.toLowerCase().contains("~onmmobdeath")){
            if(action_Trigger_Map.get("~onmmobdeath") == null){
                action_Trigger_Map.put("~onmmobdeath",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onmmobdeath") != null){
                action_Trigger_Map.get("~onmmobdeath").add(new CustomLineConfig(actionString));
            }
        }
        /**當MythicMobs的怪物掉落經驗**/
        if(actionString.toLowerCase().contains("~onmmobdropexp")){
            if(action_Trigger_Map.get("~onmmobdropexp") == null){
                action_Trigger_Map.put("~onmmobdropexp",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onmmobdropexp") != null){
                action_Trigger_Map.get("~onmmobdropexp").add(new CustomLineConfig(actionString));
            }
        }
        /**當MythicMobs的怪物掉落金錢**/
        if(actionString.toLowerCase().contains("~onmmobdropmoney")){
            if(action_Trigger_Map.get("~onmmobdropmoney") == null){
                action_Trigger_Map.put("~onmmobdropmoney",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onmmobdropmoney") != null){
                action_Trigger_Map.get("~onmmobdropmoney").add(new CustomLineConfig(actionString));
            }
        }
        /**當MythicMobs的怪物掉落物品**/
        if(actionString.toLowerCase().contains("~onmmobdropitem")){
            if(action_Trigger_Map.get("~onmmobdropitem") == null){
                action_Trigger_Map.put("~onmmobdropitem",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~onmmobdropitem") != null){
                action_Trigger_Map.get("~onmmobdropitem").add(new CustomLineConfig(actionString));
            }
        }
        /**當左鍵點擊時**/
        if(actionString.toLowerCase().contains("~leftclickair")){
            if(action_Trigger_Map.get("~leftclickair") == null){
                action_Trigger_Map.put("~leftclickair",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~leftclickair") != null){
                action_Trigger_Map.get("~leftclickair").add(new CustomLineConfig(actionString));
            }
        }
        /**當左鍵點擊方塊時**/
        if(actionString.toLowerCase().contains("~leftclickblock")){
            if(action_Trigger_Map.get("~leftclickblock") == null){
                action_Trigger_Map.put("~leftclickblock",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~leftclickblock") != null){
                action_Trigger_Map.get("~leftclickblock").add(new CustomLineConfig(actionString));
            }
        }
        /**當右鍵點擊時**/
        if(actionString.toLowerCase().contains("~rightclickair")){
            if(action_Trigger_Map.get("~rightclickair") == null){
                action_Trigger_Map.put("~rightclickair",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~rightclickair") != null){
                action_Trigger_Map.get("~rightclickair").add(new CustomLineConfig(actionString));
            }
        }
        /**當右鍵點擊方塊時**/
        if(actionString.toLowerCase().contains("~rightclickblock")){
            if(action_Trigger_Map.get("~rightclickblock") == null){
                action_Trigger_Map.put("~rightclickblock",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~rightclickblock") != null){
                action_Trigger_Map.get("~rightclickblock").add(new CustomLineConfig(actionString));
            }
        }
        /**當踩到壓力板時**/
        if(actionString.toLowerCase().contains("~pressureplate")){
            if(action_Trigger_Map.get("~pressureplate") == null){
                action_Trigger_Map.put("~pressureplate",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~pressureplate") != null){
                action_Trigger_Map.get("~pressureplate").add(new CustomLineConfig(actionString));
            }
        }
        /**當裝備檢查時**/
        if(actionString.toLowerCase().contains("~eqmcheck")){
            if(action_Trigger_Map.get("~eqmchecke") == null){
                action_Trigger_Map.put("~eqmcheck",new ArrayList<>());
            }
            if(action_Trigger_Map.get("~eqmcheck") != null){
                action_Trigger_Map.get("~eqmcheck").add(new CustomLineConfig(actionString));
            }
        }

    }


}
