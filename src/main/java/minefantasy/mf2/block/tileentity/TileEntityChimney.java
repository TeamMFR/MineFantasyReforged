package minefantasy.mf2.block.tileentity;

import minefantasy.mf2.api.refine.ISmokeCarrier;
import minefantasy.mf2.api.refine.SmokeMechanics;
import minefantasy.mf2.block.refining.BlockChimney;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class TileEntityChimney extends TileEntity implements ISmokeCarrier {
    public Block maskBlock = Blocks.air;
    public int maskMeta = 0;
    public int ticksExisted;
    public int ticksExistedTemp;
    public int lastSharedInt = 0;
    protected int smokeStorage;
    private int isIndirect = -1;
    private Random rand = new Random();

    @Override
    public void updateEntity() {
        super.updateEntity();
        ++ticksExisted;
        ++ticksExistedTemp;
        if (lastSharedInt > 0)
            --lastSharedInt;

        /*
         * TODO Custom Tex if(ticksExistedTemp == 20) {
         * MineFantasyII.debugMsg("Chimney Loaded R = " + worldObj.isRemote); sync(); }
         */
        if (smokeStorage > 0) {
            if (!isPipeChimney()) {
                SmokeMechanics.emitSmokeFromCarrier(worldObj, xCoord, yCoord, zCoord, this, 5);
            } else if (tryShareSmoke()) {
                lastSharedInt = 5;
            }
        }
        if (!worldObj.isRemote && smokeStorage > getMaxSmokeStorage() && rand.nextInt(500) == 0) {
            worldObj.newExplosion(null, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 2F, false, true);
        }
    }

    public boolean isPipeChimney() {
        if (worldObj != null) {
            Block block = worldObj.getBlock(xCoord, yCoord, zCoord);
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
        TileEntity tile = worldObj.getTileEntity(x + xCoord, y + yCoord, z + zCoord);
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
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        nbt.setInteger("ticksExisted", ticksExisted);
        nbt.setInteger("StoredSmoke", smokeStorage);
        NBTTagList savedItems = new NBTTagList();

        nbt.setInteger("BlockID", Block.getIdFromBlock(maskBlock));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        ticksExisted = nbt.getInteger("ticksExisted");
        smokeStorage = nbt.getInteger("StoredSmoke");
        setBlock(nbt.getInteger("BlockID"));
    }

    public void setBlock(int id) {
        setBlock(id, 0);
    }

    public void setBlock(int id, int subId) {
        /*
         * TODO Custom Tex maskMeta = subId; Block newblock = Block.getBlockById(id);
         * if(newblock != null) { maskBlock = newblock; } if(worldObj != null &&
         * worldObj.isRemote) { worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord,
         * zCoord, xCoord, yCoord, zCoord); }
         */
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

    public void sync() {
        /*
         * TODO Custom Tex MineFantasyII.debugMsg("Block For Chimney = " +
         * maskBlock.getUnlocalizedName() + " R = " + worldObj.isRemote);
         * if(!worldObj.isRemote) { MineFantasyII.debugMsg("Syncing Chimney " + xCoord +
         * ", " + yCoord + ", " + zCoord); List<EntityPlayer> players =
         * ((WorldServer)worldObj).playerEntities; for(int i = 0; i < players.size();
         * i++) { EntityPlayer player = players.get(i);
         * ((WorldServer)worldObj).getEntityTracker().func_151248_b(player, new
         * ChimneyPacket(this).generatePacket()); } }
         * MineFantasyII.debugMsg("Syncing Render " + xCoord + ", " + yCoord + ", " +
         * zCoord); worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord,
         * xCoord, yCoord, zCoord);
         */
    }

    public BlockChimney getActiveBlock() {
        if (worldObj == null)
            return null;

        Block block = worldObj.getBlock(xCoord, yCoord, zCoord);

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
        Block block = worldObj.getBlock(x + xCoord, y + yCoord, z + zCoord);
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

    public boolean canAccept(ForgeDirection fd) {
        return canAccept(fd.offsetX, fd.offsetY, fd.offsetZ);
    }
}
