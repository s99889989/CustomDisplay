package com.daxton.customdisplay.task.action.list;

import com.daxton.customdisplay.CustomDisplay;
import com.daxton.customdisplay.api.character.stringconversion.ConversionMain;
import com.daxton.customdisplay.api.entity.Aims;
import com.daxton.customdisplay.api.other.SetValue;
import com.daxton.customdisplay.api.other.StringFind;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

import static org.bukkit.Color.fromRGB;

public class Message{

    private CustomDisplay cd = CustomDisplay.getCustomDisplay();

    public Message(){

    }

    public void setMessage(LivingEntity self,LivingEntity target, String firstString,String taskID){



        /**獲得目標**/
        List<LivingEntity> targetList = new Aims().valueOf(self,target,firstString);
        if(!(targetList.isEmpty())){
            for (LivingEntity livingEntity : targetList){
                if(livingEntity instanceof Player){
                    Player player = (Player) livingEntity;

                    /**獲得內容**/
                    String message = new SetValue(self,player,firstString,"[];","","m=","message=").getString();

                    sendMessage(self, player, message);
                }
            }
        }

    }

    public void sendMessage(LivingEntity self, Player player,  String message){

        if(message.contains("{") && message.contains("}")){
            TextComponent all = new TextComponent("");
            for(String me : new StringFind().getBlockList(message,"{}")){
                all.addExtra(getKey(self, player, me));
            }
            player.spigot().sendMessage(all);
        }else {
            player.sendMessage(message);
        }

    }

    public TextComponent getKey(LivingEntity self,LivingEntity target, String messageString){
        TextComponent textComponent = new TextComponent(messageString);
        File filePatch = new File(cd.getDataFolder(),"Message");
        for(String ymlName : filePatch.list()){
            if(ymlName.contains(".yml")){

                File fileYmlPatch = new File(cd.getDataFolder(),"Message/"+ymlName);
                FileConfiguration messageConfig = YamlConfiguration.loadConfiguration(fileYmlPatch);
                ConfigurationSection buttonNameList = messageConfig.getConfigurationSection("");
                if(buttonNameList.getKeys(false).contains(messageString)){
                    String text = messageConfig.getString(messageString+".Text");
                    boolean bold = messageConfig.getBoolean(messageString+".Bold");
                    if(text.contains("&")){
                        text = new ConversionMain().valueOf(self,target,text);
                    }
                    String color = messageConfig.getString(messageString+".Color");
                    String click_action = messageConfig.getString(messageString+".ClickEvent.Action");
                    String click_value = messageConfig.getString(messageString+".ClickEvent.Text");
                    if(click_value.contains("&")){
                        click_value = new ConversionMain().valueOf(self,target,click_value);
                    }

                    String hover_action = messageConfig.getString(messageString+".HoverEvent.Action");
                    String hover_value = messageConfig.getString(messageString+".HoverEvent.Text");
                    if(hover_value.contains("&")){
                        hover_value = new ConversionMain().valueOf(self,target,hover_value);
                    }
                    textComponent.setBold(bold);
                    if(color != null){
//                        BigInteger bigint = new BigInteger(color, 16);
//                        int numb = bigint.intValue();
//                        fromRGB(numb)
                        textComponent.setColor(ChatColor.valueOf(color));
                    }

                    if(click_action != null && click_value != null){

                        textComponent.setClickEvent(new ClickEvent(Enum.valueOf(ClickEvent.Action.class,click_action),click_value));
                    }

                    if(hover_action != null && hover_value != null){

                        textComponent.setHoverEvent( new HoverEvent(Enum.valueOf(HoverEvent.Action.class,hover_action), new Text(hover_value)));
                    }
                    if(text != null){
                        textComponent.setText(text);
                    }

                    //player.sendMessage("包含: "+click_action);
                }
                //buttonNameList.getKeys(false).forEach((line)->{player.sendMessage(line);});


            }

        }
        return textComponent;
    }


}
