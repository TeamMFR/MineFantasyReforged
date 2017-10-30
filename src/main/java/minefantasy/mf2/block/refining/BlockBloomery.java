package minefantasy.mf2.block.refining;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.block.tileentity.TileEntityBloomery;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.tool.ItemLighterMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBloomery extends BlockContainer {
    public static int bloomery_RI = 109;
    public IIcon bottomTex;
    public IIcon sideTex;
    private Random rand = new Random();

    public BlockBloomery() {
        super(Material.rock);
        GameRegistry.registerBlock(this, "MF_Bloomery");
        setBlockName("bloomery");
        this.setStepSound(Block.soundTypeStone);
        this.setHardness(8F);
        this.setResistance(10F);
        this.setCreativeTab(CreativeTabMF.tabUtil);
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
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBloomery();
    }

    private TileEntityBloomery getTile(IBlockAccess world, int x, int y, int z) {
        return (TileEntityBloomery) world.getTileEntity(x, y, z);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntityBloomery tile = getTile(world, x, y, z);

        if (tile != null) {
            for (int i1 = 0; i1 < (tile.getSizeInventory() - 1); ++i1)// breaking does not retrieve the result
            {
                ItemStack itemstack = tile.getStackInSlot(i1);

                if (itemstack != null) {
                    float f = this.rand.nextFloat() * 0.8F + 0.1F;
                    float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                    float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                    while (itemstack.stackSize > 0) {
                        int j1 = this.rand.nextInt(21) + 10;

                        if (j1 > itemstack.stackSize) {
                            j1 = itemstack.stackSize;
                        }

                        itemstack.stackSize -= j1;
                        EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2,
                                new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

                        if (itemstack.hasTagCompound()) {
                            entityitem.getEntityItem()
                                    .setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                        }

                        float f3 = 0.05F;
                        entityitem.motionX = (float) this.rand.nextGaussian() * f3;
                        entityitem.motionY = (float) this.rand.nextGaussian() * f3 + 0.2F;
                        entityitem.motionZ = (float) this.rand.nextGaussian() * f3;
                        world.spawnEntityInWorld(entityitem);
                    }
                }
            }

            world.func_147453_f(x, y, z, block);
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset,
                                    float yOffset, float zOffset) {
        TileEntityBloomery tile = getTile(world, x, y, z);
        if (tile != null) {
            ItemStack held = user.getHeldItem();

            // Hammer
            if (!user.isSwingInProgress && tile.tryHammer(user)) {
                return true;
            }
            // Light
            int l = ItemLighterMF.tryUse(held, user);
            if (!tile.isActive && !tile.hasBloom()) {
                if (held != null && l != 0) {
                    user.playSound("fire.ignite", 1.0F, 1.0F);
                    if (world.isRemote)
                        return true;
                    if (l == 1 && tile.light(user)) {
                        held.damageItem(1, user);
                    }
                    return true;
                }
            }
            // GUI
            if (!world.isRemote && !tile.isActive && !tile.hasBloom()) {
                user.openGui(MineFantasyII.instance, 0, world, x, y, z);
            }
        }
        return true;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer user) {
        TileEntityBloomery tile = getTile(world, x, y, z);
        if (tile != null) {
            if (user.swingProgress <= 0.1F) {
                tile.tryHammer(user);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return BlockListMF.crucible.getIcon(side, 0);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return bloomery_RI;
    }

}
