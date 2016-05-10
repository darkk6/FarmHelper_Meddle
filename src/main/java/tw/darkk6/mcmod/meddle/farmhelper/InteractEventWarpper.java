package tw.darkk6.mcmod.meddle.farmhelper;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class InteractEventWarpper {
	public BlockPos pos;
	public EnumFacing face;//若不知道可以是 null
	
	public InteractEventWarpper(BlockPos pos,EnumFacing face){
		this.pos = pos;
		this.face = face;
	}
	
}