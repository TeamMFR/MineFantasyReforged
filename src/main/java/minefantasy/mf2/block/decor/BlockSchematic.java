package minefantasy.mf2.block.decor;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

public class BlockSchematic extends Block {
    public IIcon[] icons = new IIcon[6];

    public BlockSchematic(String name) {
        super(Material.cloth);

        GameRegistry.registerBlock(this, name);
        setBlockName(name);
        setBlockTextureName("minefantasy2:meta/" + name);
        setBlockBounds(0F, 0F, 0F, 1F, 0F, 1F);
    }

    public static boolean useSchematic(ItemStack item, World world, EntityPlayer user,
                                       MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;

            if (movingobjectposition.sideHit == 0) {
                --j;
            }

            if (movingobjectposition.sideHit == 1) {
                ++j;
            }

            if (movingobjectposition.sideHit == 2) {
                --k;
            }

            if (movingobjectposition.sideHit == 3) {
                ++k;
            }

            if (movingobjectposition.sideHit == 4) {
                --i;
            }

            if (movingobjectposition.sideHit == 5) {
                ++i;
            }

            if (user.canPlayerEdit(i, j, k, movingobjectposition.sideHit, item)) {
                return placeSchematic(item.getItemDamage(), user, item, user.worldObj, i, j, k);
            }
        }
        return false;
    }

    public static boolean placeSchematic(int meta, EntityPlayer user, ItemStack item, World world, int x, int y,
                                         int z) {
        if (world.isAirBlock(x, y, z) && canBuildOn(world, x, y - 1, z)) {
            world.setBlock(x, y, z, BlockListMF.schematic_general, Math.max(0, meta - 4), 2);
            return true;
        }
        return false;
    }

    public static boolean canBuildOn(World world, int x, int y, int z) {
        return world.isSideSolid(x, y, z, ForgeDirection.UP);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public Item getItemDropped(int meta, Random rand, int i) {
        return ComponentListMF.artefacts;
    }

    @Override
    public int damageDropped(int meta) {
        return meta + 4;
    }

    @Override
    public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[Math.min(meta, icons.length - 1)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        for (int i = 0; i < icons.length; i++) {
            this.icons[i] = reg.registerIcon("minefantasy2:meta/schematic_" + (i + 1));
        }
    }
}
