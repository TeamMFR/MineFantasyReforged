package minefantasy.mfr.tile;

import minefantasy.mfr.api.refine.ISmokeCarrier;
import minefantasy.mfr.api.refine.SmokeMechanics;
import minefantasy.mfr.block.BlockChimney;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

import java.util.Random;

public class TileEntityChimney extends TileEntity implements ISmokeCarrier, ITickable {
	public Block maskBlock = Blocks.AIR;
	public int ticksExisted;
	public int ticksExistedTemp;
	public int lastSharedInt = 0;
	protected int smokeStorage;
	private int isIndirect = -1;
	private final Random rand = new Random();

	@Override
	public void update() {
		++ticksExisted;
		++ticksExistedTemp;
		if (lastSharedInt > 0)
			--lastSharedInt;

		if (smokeStorage > 0) {
			if (!isPipeChimney()) {
				SmokeMechanics.emitSmokeFromCarrier(world, pos, this, 5);
			} else if (tryShareSmoke()) {
				lastSharedInt = 5;
			}
		}
		if (!world.isRemote && smokeStorage > getMaxSmokeStorage() && rand.nextInt(500) == 0) {
			world.newExplosion(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 2F, false, true);
		}
	}

	public boolean isPipeChimney() {
		if (world != null) {
			Block block = world.getBlockState(pos).getBlock();
			return block instanceof BlockChimney && ((BlockChimney) block).isPipe();
		}
		return false;
	}

	private boolean tryShareSmoke() {
		if (!isPipeChimney())
			return false;
		if (tryPassTo(0, 1, 0, true, false)) {
			return true;// Up First
		}
		if (tryPassTo(-1, 0, 0, false, true) || tryPassTo(1, 0, 0, false, true) || tryPassTo(0, 0, -1, false, true)
				|| tryPassTo(0, 0, 1, false, true)) {
			return true;// Sides
		}
		if (this.getMaxSmokeStorage() - this.getSmokeValue() <= 5) {
			return tryPassTo(0, -1, 0, false, false);// Down last only when nearly full
		}
		return false;
	}

	private boolean tryPassTo(int x, int y, int z, boolean priority, boolean sideways) {
		TileEntity tile = world.getTileEntity(pos.add(x, y, z));
		if (tile != null && tile instanceof TileEntityChimney) {
			TileEntityChimney carrier = (TileEntityChimney) tile;
			boolean canPass = !sideways || carrier.canAcceptSideways();

			int smoke = carrier.getSmokeValue();
			int max = carrier.getMaxSmokeStorage();
			int room_left = max - smoke;
			int limit = priority ? max : this.getSmokeValue();// When going up, it fills all, sideways follows
			// concentration gradient
			if (canPass && carrier.lastSharedInt <= 0 && room_left > 0 && smoke < limit) {
				int pass = Math.min(room_left, 5);
				carrier.setSmokeValue(smoke + pass);
				this.smokeStorage -= pass;
				return true;
			}
		}

		return false;
	}

	private boolean canAcceptSideways() {
		BlockChimney block = this.getActiveBlock();
		if (block != null) {
			return block.isWideChimney() || block.isPipe();
		}
		return false;
	}

	@Override
	public int getSmokeValue() {
		return smokeStorage;
	}

	@Override
	public void setSmokeValue(int smoke) {
		smokeStorage = smoke;
	}

	@Override
	public int getMaxSmokeStorage() {
		if (this.blockType != null && blockType instanceof BlockChimney) {
			return ((BlockChimney) blockType).size;
		}
		return 5;
	}

	public BlockChimney getActiveBlock() {
		if (world == null)
			return null;

		Block block = world.getBlockState(pos).getBlock();

		if (block != null && block instanceof BlockChimney) {
			return (BlockChimney) block;
		}
		return null;
	}

	public boolean isIndirect() {
		BlockChimney block = getActiveBlock();
		return block != null && block.isIndirect;
	}

	@Override
	public boolean canAbsorbIndirect() {
		if (isIndirect == -1) {
			isIndirect = isIndirect() ? 1 : 0;
		}
		return isIndirect == 1;
	}

	public boolean canAccept(int x, int y, int z) {
		Block block = world.getBlockState(pos.add(x, y, z)).getBlock();
		if (block instanceof BlockChimney) {
			if (x == 0 && z == 0)// Not sideways
			{
				return true;
			} else {
				return ((BlockChimney) block).isPipe() || ((BlockChimney) block).isWideChimney();
			}
		}
		return false;
	}

	public boolean canAccept(EnumFacing facing) {
		return canAccept(facing.getFrontOffsetX(), facing.getFrontOffsetY(), facing.getFrontOffsetZ());
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setInteger("ticksExisted", ticksExisted);
		nbt.setInteger("StoredSmoke", smokeStorage);

		return nbt;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		ticksExisted = nbt.getInteger("ticksExisted");
		smokeStorage = nbt.getInteger("StoredSmoke");
	}
}
