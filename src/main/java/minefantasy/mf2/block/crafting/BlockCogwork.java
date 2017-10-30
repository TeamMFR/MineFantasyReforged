package minefantasy.mf2.block.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.PowerArmour;
import minefantasy.mf2.api.helpers.ToolHelper;
import minefantasy.mf2.block.list.BlockListMF;
import minefantasy.mf2.entity.EntityCogwork;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCogwork extends BlockDirectional {
    @SideOnly(Side.CLIENT)
    private IIcon toptex;
    @SideOnly(Side.CLIENT)
    private IIcon facetex;
    private String name;
    private boolean isMain = false;

    public BlockCogwork(String name, boolean helmet) {
        super(Material.circuits);
        this.isMain = helmet;
        GameRegistry.registerBlock(this, "block" + name);
        this.name = name;
        this.setTickRandomly(true);
        this.name = name;
        this.setCreativeTab(CreativeTabMF.tabGadget);
        this.setBlockTextureName("minefantasy2:metal/" + name.toLowerCase());
        setBlockName(name);
        this.setHardness(1F);
        this.setResistance(5F);
        this.setLightOpacity(0);
    }

    /**
     * Gets the block's texture. Args: side, meta
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.toptex
                : (side == 0 ? this.toptex
                : (meta == 2 && side == 2 ? this.facetex
                : (meta == 3 && side == 5 ? this.facetex
                : (meta == 0 && side == 3 ? this.facetex
                : (meta == 1 && side == 4 ? this.facetex : this.blockIcon)))));
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float f1,
                                    float f2) {
        if (!world.isRemote && ToolHelper.getCrafterTool(player.getHeldItem()).equalsIgnoreCase("spanner")) {
            return tryBuild(player, world, x, y, z);
        }
        return false;
    }

    public boolean tryBuild(EntityPlayer builder, World world, int x, int y, int z) {
        if (isMain && PowerArmour.isStationBlock(world, x, y + 2, z)
                && world.getBlock(x, y - 1, z) == BlockListMF.cogwork_legs
                && world.getBlock(x, y + 1, z) == BlockListMF.cogwork_helm) {
            if (!world.isRemote) {
                world.setBlock(x, y, z, getBlockById(0), 0, 2);
                world.setBlock(x, y - 1, z, getBlockById(0), 0, 2);
                world.setBlock(x, y + 1, z, getBlockById(0), 0, 2);

                EntityCogwork suit = new EntityCogwork(world);
                int m = world.getBlockMetadata(x, y, z);
                int angle = getAngleFor(builder);
                suit.setLocationAndAngles(x + 0.5D, y - 0.95D, z + 0.5D, angle, 0.0F);
                suit.rotationYaw = suit.rotationYawHead = suit.prevRotationYaw = suit.prevRotationYawHead = angle;
                world.spawnEntityInWorld(suit);

                world.notifyBlockChange(x, y, z, getBlockById(0));
                world.notifyBlockChange(x, y - 1, z, getBlockById(0));
                world.notifyBlockChange(x, y + 1, z, getBlockById(0));
            }

        }
        return false;
    }

    private int getAngleFor(EntityPlayer user) {
        int l = MathHelper.floor_double(user.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
        return l * 90;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * Called when the block is placed in the world.
     */
    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase user, ItemStack placed) {
        int l = MathHelper.floor_double(user.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, l, 2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {
        this.facetex = p_149651_1_.registerIcon(this.getTextureName() + "_face");
        this.toptex = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
    }
}