package tw.darkk6.mcmod.meddle.farmhelper.util;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MainOrOffHand;
import net.minecraft.util.Vec3;
import tw.darkk6.meddle.api.util.APIUtil;
import tw.darkk6.meddle.api.util.PlayerControllerMP;

public class Util {

	public static float playerBlockReachDistance(){
		PlayerControllerMP pcmp=APIUtil.getPlayerController();
		return pcmp.getBlockReachDistance();
	}
	
	public static void rightClickBlock(EntityPlayerSP player,WorldClient world,ItemStack stack, BlockPos pos,EnumFacing facing,Vec3 posVec,MainOrOffHand hand){
		PlayerControllerMP pcmp=APIUtil.getPlayerController();
		pcmp.processRightClickBlock(player, world,stack,pos,facing,posVec,hand);
	}
}
