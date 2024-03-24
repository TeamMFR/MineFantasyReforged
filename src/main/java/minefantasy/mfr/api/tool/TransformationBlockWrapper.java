package minefantasy.mfr.api.tool;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.BlockPos;

public class TransformationBlockWrapper {
	private ItemStack tool;
	private BlockPos pos;
	private IBlockState state;
	private Integer progress;
	private Integer maxProgress;
	private String displayName;

	public TransformationBlockWrapper(ItemStack tool, BlockPos pos, IBlockState state, int progress, int maxProgress,
			String displayName) {
		this.tool = tool;
		this.pos = pos;
		this.state = state;
		this.progress = progress;
		this.maxProgress = maxProgress;
		this.displayName = displayName;
	}

	public int getProgressMetre(double i) {
		return (int) Math.ceil(i / this.maxProgress * this.progress);
	}

	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("pos", NBTUtil.createPosTag(this.pos));
		nbt.setTag("state", NBTUtil.writeBlockState(new NBTTagCompound(), this.state));
		nbt.setTag("tool", tool.serializeNBT());
		nbt.setInteger("progress", this.progress);
		nbt.setInteger("maxProgress", this.maxProgress);
		nbt.setString("displayName", this.displayName);
		return nbt;
	}

	public static TransformationBlockWrapper deserializeNBT(NBTTagCompound nbt) {
		ItemStack tagTool = new ItemStack(nbt.getCompoundTag("tool"));
		BlockPos tagPos = NBTUtil.getPosFromTag(nbt.getCompoundTag("pos"));
		IBlockState tagState = NBTUtil.readBlockState(nbt.getCompoundTag("state"));
		int tagProgress = nbt.getInteger("progress");
		int tagMaxProgress = nbt.getInteger("maxProgress");
		String displayName = nbt.getString("displayName");
		return new TransformationBlockWrapper(tagTool, tagPos, tagState, tagProgress, tagMaxProgress, displayName);
	}

	public static boolean checkTransformationBlock(TransformationBlockWrapper transformationBlock, IBlockState state, BlockPos pos) {
		return transformationBlock.getState() == state && transformationBlock.getPos().equals(pos);
	}

	public ItemStack getTool() {
		return tool;
	}

	public void setTool(ItemStack tool) {
		this.tool = tool;
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public IBlockState getState() {
		return state;
	}

	public void setState(IBlockState state) {
		this.state = state;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}

	public Integer getMaxProgress() {
		return maxProgress;
	}

	public void setMaxProgress(Integer maxProgress) {
		this.maxProgress = maxProgress;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
