package tw.darkk6.mcmod.meddle.farmhelper;

import java.util.Vector;

import net.fybertech.meddleapi.MeddleClient.IKeyBindingState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MainOrOffHand;
import net.minecraft.util.MovingObjectPosition;
import tw.darkk6.mcmod.meddle.farmhelper.plant.IPlants;
import tw.darkk6.mcmod.meddle.farmhelper.plant.PlantManager;
import tw.darkk6.mcmod.meddle.farmhelper.util.Config;
import tw.darkk6.mcmod.meddle.farmhelper.util.Log;
import tw.darkk6.mcmod.meddle.farmhelper.util.Reference;
import tw.darkk6.mcmod.meddle.farmhelper.util.Util;

public class EventHandler implements IKeyBindingState {

	@Override
	public void onsetKeyBindState(int keycode, boolean state, KeyBinding keybinding) {
		//根據 MeddleAPI 的 Transformer , 滑鼠右鍵也是 Keybinding , 所以可以這樣做
		if( (!state) || keybinding==null) return;
		// keybinding.h() => getDescription
		if(Config.INTERACT_KEY_NAME.equals(keybinding.h())){
			//取得十字準心面對的那一格，如果沒東西，或者點選的不是 Block , 則結束
			Minecraft mc=Minecraft.getMinecraft();
			EntityPlayerSP player=mc.thePlayer;
			if(player==null) return;//避免出錯，檢查一下
			// player.a => player.rayTrace
			MovingObjectPosition pointed=player.a(Util.playerBlockReachDistance(), 1.0F);
			if( pointed==null || pointed.typeOfHit!=MovingObjectPosition.MovingObjectType.BLOCK) return;
			//觸發事件
			this.onBlockRightClick(
					new InteractEventWarpper(pointed.getBlockPos(),pointed.sideHit)
				);
		}
	}
	
	// player.worldObj => player.l
	private void onBlockRightClick(InteractEventWarpper e){
		Minecraft mc=Minecraft.getMinecraft();
		EntityPlayerSP player=mc.thePlayer;
		if(player==null) return;//避免出錯，檢查一下
		
		// Main慣用手拿的東西 , off 副手拿的東西 - 1.9
		ItemStack stackMain=player.getHeldItem(MainOrOffHand.MAIN_HAND);
		ItemStack stackOff=player.getHeldItem(MainOrOffHand.OFF_HAND);
		ItemStack stack=null;//實際要種植的東西
		MainOrOffHand whichHand=null;
		
		//1.9 - 先確認主手拿的東西是否為可種植且設定檔中有啟用 , 如果可以就讓 stack 指向 stackMain
		//      反之，就偵測副手 , 並讓 stack 指向 stackOff 
		if(PlantManager.isPlantEnabled(stackMain) && PlantManager.canPlantAt(player.l, e.pos, stackMain)){
			stack=stackMain;
			whichHand=MainOrOffHand.MAIN_HAND;
		}else if(PlantManager.isPlantEnabled(stackOff) && PlantManager.canPlantAt(player.l, e.pos, stackOff)){
			stack=stackOff;
			whichHand=MainOrOffHand.OFF_HAND;
		}else return; //主副手都不是要重的東西，就結束
		
		//當要種植副手的東西時，要檢查，主手的物品是不是只是被禁用而已
		if(whichHand==MainOrOffHand.OFF_HAND && stackMain!=null){
			//如果 MainHand 的 Item 可以被種在這一格，就代表只是禁用而已，這時候要提示訊息
			if(PlantManager.canPlantAt(player.l, e.pos, stackMain)){
				Log.showMainHandMustNull();
				return;
			}
		}
		
		int count=stack.stackSize;//手上道具的數量
		if(count == 1) return;//如果手上道具只有一個，就不做任何搜尋
		Vector<BlockPos> list=null;
		IPlants plant=PlantManager.get(stack.getItem());
		// 要先判斷該格是否能種植 , 不能種植就不繼續
		if(!plant.canPlantAt(player.l, e.pos)) return;
		list = plant.doBFSSearch(player.l, e.pos, count, Reference.config.fasterBFS);
		
		//==== BFS 搜尋完成，開始種植 =====
		//數量內的點都找完了 , 將有效數量的種下去，跳過 0 是因為  0=作用的那一格
		for(int i=1;i<count && i<list.size();i++){
			//這裡不能使用 itemStack.onItemUse , 那個不會與 Server 通訊 , 物品只是看起來有而已
			// 1.9 , 改用 processRightClickBlock , 1.8.8=>onPlayerRightClick
			Util.rightClickBlock(
					player,
					(WorldClient)(player.l), 
					stack, 
					list.get(i),
					e.face,
					player.getPositionVector(),
					whichHand
				);
		}
	}
}
