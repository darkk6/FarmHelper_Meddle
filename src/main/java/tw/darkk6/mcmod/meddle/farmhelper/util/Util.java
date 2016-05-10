package tw.darkk6.mcmod.meddle.farmhelper.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MainOrOffHand;
import net.minecraft.util.Vec3;

public class Util {

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static float playerBlockReachDistance(){
		try{
			Object pc=getPlayerController();
			Class cls = Class.forName("bkq");
			// float  playerController.BlockReachDistance() => bkq.d()
			Method m=cls.getMethod("d");
			Float result=(Float)m.invoke(pc);
			return result.floatValue();
		}catch(Exception e){
			Log.info(e.getMessage());
			return 4.0F;
		}
	}
	
	/*
	 * 	return
	 * 		 0 : 成功
	 * 		-1 : 無法取得 PlayerController
	 * 		-2 : 無法呼叫 processRightClickBlock
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int rightClickBlock(EntityPlayerSP player,WorldClient world,ItemStack stack, BlockPos pos,EnumFacing facing,Vec3 posVec,MainOrOffHand hand){
		//Minecraft mc=Minecraft.getMinecraft();
		//EntityPlayerSP player=mc.thePlayer;
		//交叉查詢得到 playerController 是 minecraft.c => bkq
		// processRightClickBlock => a
		try{
			Class cls=Class.forName("bkq");
			Method m=cls.getMethod("a",
						EntityPlayerSP.class,WorldClient.class,ItemStack.class,
						BlockPos.class,EnumFacing.class,Vec3.class,MainOrOffHand.class
					);
			Object playerController = getPlayerController();
			if(playerController==null) return -1;
			m.invoke(playerController,
						player, world,stack,pos,facing,posVec,hand
					);
		}catch(Exception e){
			return -2;
		}
		return 0;
	}
	
	@SuppressWarnings("rawtypes")
	private static Object getPlayerController(){
		try{
			Class cls = Minecraft.class;
			Field f=cls.getField("c");
			Object pc=f.get(Minecraft.getMinecraft());
			return pc;
		}catch(Exception e){
			return null;
		}
	}
}
