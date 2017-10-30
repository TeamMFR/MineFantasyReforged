package minefantasy.mf2.block.crafting;

import minefantasy.mf2.api.helpers.PowerArmour;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.block.basic.BasicBlockMF;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockFrame extends BasicBlockMF {
    public static final float offset = 0.28125F;
    public boolean isCogworkHolder = false;

    public BlockFrame(String name) {
        this(name, null);
    }

    public BlockFrame(String name, Object drop) {
        super(name, Material.iron, drop);
        setBlockTextureName("minefantasy2:metal/" + name);
        this.setCreativeTab(CreativeTabMF.tabGadget);
        this.setHardness(1.0F);
        this.setResistance(3.0F);
    }

    public BlockFrame setCogworkHolder() {
        isCogworkHolder = true;
        this.setCreativeTab(null);
        return this;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public void setBlockBoundsForItemRender() {
        if (isCogworkHolder) {
            this.setBlockBounds(0F, 0F, 0F, 1F, 1F - offset, 1F);
            return;
        }
        this.setBlockBounds(offset, offset, offset, 1 - offset, 1 - offset, 1 - offset);
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        if (isCogworkHolder) {
            this.setBlockBounds(0F, 0F, 0F, 1F, 1F - offset, 1F);
            return;
        }
        boolean mz = this.canInteract(world, x, y, z - 1);
        boolean pz = this.canInteract(world, x, y, z + 1);
        boolean mx = this.canInteract(world, x - 1, y, z);
        boolean px = this.canInteract(world, x + 1, y, z);
        boolean my = this.canInteract(world, x, y - 1, z, true);
        boolean py = this.canInteract(world, x, y + 1, z);

        float xstart = offset;
        float xend = 1 - offset;
        float zstart = offset;
        float zend = 1 - offset;
        float ystart = offset;
        float yend = 1 - offset;

        if (mz) {
            zstart = 0.0F;
        }

        if (pz) {
            zend = 1.0F;
        }

        if (mx) {
            xstart = 0.0F;
        }

        if (px) {
            xend = 1.0F;
        }

        if (my) {
            ystart = 0.0F;
        }

        if (py) {
            yend = 1.0F;
        }

        this.setBlockBounds(xstart, ystart, zstart, xend, yend, zend);
    }

    public boolean canInteract(IBlockAccess world, int x, int y, int z) {
        return canInteract(world, x, y, z, false);
    }

    public boolean canInteract(IBlockAccess world, int x, int y, int z, boolean floor) {
        if (floor && world.getBlock(x, y, z).isSideSolid(world, x, y, z, ForgeDirection.UP)) {
            return true;
        }
        return world.getBlock(x, y, z) instanceof BlockFrame;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1,
                                    float f2) {
        if (ToolHelper.getCrafterTool(player.getHeldItem()).equalsIgnoreCase("spanner")) {
            return tryBuild(player, world, x, y, z);
        }
        return false;
    }

    private boolean tryBuild(EntityPlayer player, World world, int x, int y, int z) {
        if (PowerArmour.isBasicStationFrame(world, x, y, z)
                && (player.capabilities.isCreativeMode || player.inventory.hasItem(ComponentListMF.cogwork_pulley))) {
            if (!player.capabilities.isCreativeMode) {
                player.inventory.consumeInventoryItem(ComponentListMF.cogwork_pulley);
            }
            if (!world.isRemote) {
                world.setBlock(x, y, z, BlockListMF.cogwork_builder, 0, 2);
            }
            return true;
        }
        return false;
    }
}
