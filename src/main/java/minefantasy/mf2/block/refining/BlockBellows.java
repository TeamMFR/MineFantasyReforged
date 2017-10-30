package minefantasy.mf2.block.refining;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.block.tileentity.TileEntityBellows;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * @author Anonymous Productions
 * <p>
 * Sources are provided for educational reasons. though small bits of
 * code, or methods can be used in your own creations.
 */
public class BlockBellows extends BlockContainer {
    public static int bellows_RI = 105;

    private Random rand = new Random();

    public BlockBellows() {
        super(Material.wood);
        GameRegistry.registerBlock(this, "MF_Bellows");
        setBlockName("bellows");
        this.setHardness(1F);
        this.setResistance(0.5F);
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
    public IIcon getIcon(int side, int meta) {
        return Blocks.planks.getIcon(side, 0);
    }

    @SideOnly(Side.CLIENT)
    public int getRenderType() {
        return bellows_RI;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase user, ItemStack item) {
        int direction = MathHelper.floor_double(user.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        world.setBlockMetadataWithNotify(x, y, z, direction, 2);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1,
                                    float f2) {
        TileEntityBellows bellows = (TileEntityBellows) world.getTileEntity(x, y, z);
        if (bellows != null) {
            bellows.interact(player, 2F);
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBellows();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
    }
}
