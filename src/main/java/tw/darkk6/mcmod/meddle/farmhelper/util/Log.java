package tw.darkk6.mcmod.meddle.farmhelper.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
	private static Logger log=LogManager.getLogger(Reference.LOG_TAG);
	
	public static Logger info(String msg){
		log.info(msg);
		return log;
	}
	//顯示主手必須要是空的訊息
	public static void showMainHandMustNull(){
		infoChat("如要快速種植副手物品，主手請勿拿著可種植的其他物品");
	}
	
	public static void logChat(String msg){
		try{
			ChatComponentText txt=new ChatComponentText(TextFormatting.RED+"["+Reference.LOG_TAG+"] ");
			txt.a(TextFormatting.RESET+msg);
			Minecraft.getMinecraft().thePlayer.addChatMessage(txt);
		}catch(Exception e){
			Log.info(msg);
		}
	}
	
	public static void infoChat(String msg){
		try{
			ChatComponentText txt=new ChatComponentText(TextFormatting.GOLD+"["+Reference.LOG_TAG+"] ");
			txt.a(TextFormatting.RESET+msg);
			Minecraft.getMinecraft().thePlayer.addChatMessage(txt);
		}catch(Exception e){
			Log.info(msg);
		}
	}
}
