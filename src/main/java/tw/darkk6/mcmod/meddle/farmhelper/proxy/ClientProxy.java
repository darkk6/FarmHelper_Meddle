package tw.darkk6.mcmod.meddle.farmhelper.proxy;

import java.io.File;

import net.fybertech.meddle.Meddle;
import net.fybertech.meddleapi.MeddleClient;
import tw.darkk6.mcmod.meddle.farmhelper.EventHandler;
import tw.darkk6.mcmod.meddle.farmhelper.plant.PlantManager;
import tw.darkk6.mcmod.meddle.farmhelper.util.Config;
import tw.darkk6.mcmod.meddle.farmhelper.util.Reference;


public class ClientProxy extends CommonProxy{
	
	public EventHandler eventhandler = new EventHandler();
	
	@Override
	public void init(){
		super.init();
		Reference.config=new Config(new File(Meddle.getConfigDir(),Reference.MODID+".cfg"));
		PlantManager.update();
		//Meddle 的作法是 hook Keybinding 事件，右鍵也是 Keybinding
		MeddleClient.registerKeyBindStateHandler(eventhandler);
	}
}
