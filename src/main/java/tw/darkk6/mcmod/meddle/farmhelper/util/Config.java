package tw.darkk6.mcmod.meddle.farmhelper.util;

import java.io.File;

import net.fybertech.meddleapi.ConfigFile;

public class Config {
	
	//使用道具(預設為滑鼠右鍵)的  Key Name
	public static String INTERACT_KEY_NAME = "key.use";
	
	public boolean beetroot=true,carrot=true,melon=false,wart=true,potato=true,pumpkin=false,wheat=true;
	public boolean fasterBFS=false;
	
	
	private long lastModify=0L;
	private ConfigFile cfg;
	private File file;
	
	public Config(File file){
		cfg=new ConfigFile(file);
		this.file=file;
		reload();
		lastModify = file.lastModified();
	}
	
	public boolean update(){
		if(lastModify != file.lastModified()){
			reload();
			lastModify = file.lastModified();
			return true;
		}
		return false;
	}
	
	private void reload(){
		
		cfg.load();
		
		this.wheat=((Boolean)cfg.get(ConfigFile.key("general", "wheat", Boolean.valueOf(wheat), "小麥種子"))).booleanValue();
		this.melon=((Boolean)cfg.get(ConfigFile.key("general", "melon", Boolean.valueOf(melon), "西瓜種子"))).booleanValue();
		this.pumpkin=((Boolean)cfg.get(ConfigFile.key("general", "pumpkin", Boolean.valueOf(pumpkin), "南瓜種子"))).booleanValue();
		this.beetroot=((Boolean)cfg.get(ConfigFile.key("general", "beetroot", Boolean.valueOf(beetroot), "甜菜種子"))).booleanValue();
		this.potato=((Boolean)cfg.get(ConfigFile.key("general", "potato", Boolean.valueOf(potato), "馬鈴薯"))).booleanValue();
		this.carrot=((Boolean)cfg.get(ConfigFile.key("general", "carrot", Boolean.valueOf(carrot), "胡蘿蔔"))).booleanValue();
		this.wart=((Boolean)cfg.get(ConfigFile.key("general", "wart", Boolean.valueOf(wart), "地獄疙瘩"))).booleanValue();

		this.fasterBFS=((Boolean)cfg.get(ConfigFile.key("general", "fasterBFS", Boolean.valueOf(fasterBFS), "使用十字形搜尋法"))).booleanValue();

		if(cfg.hasChanged()) cfg.save();
		Log.infoChat("設定檔重新載入完成");
	}
	
	
}
