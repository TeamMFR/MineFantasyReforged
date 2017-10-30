package minefantasy.mf2.block.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.block.tileentity.TileEntityBombPress;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBombPress extends BlockContainer {
    public static int bpress_RI = 108;
    private Random rand = new Random();

    public BlockBombPress() {
        super(Material.iron);
        GameRegistry.registerBlock(this, "MF_BombPress");
        setBlockName("bombPress");
        this.setStepSound(Block.soundTypeMetal);
        this.setHardness(5F);
        this.setResistance(2F);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabMF.tabUtil);
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase user, ItemStack item) {
        int direction = MathHelper.floor_double(user.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        world.setBlockMetadataWithNotify(x, y, z, direction, 2);
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset,
                                    float yOffset, float zOffset) {
        if (!ResearchLogic.hasInfoUnlocked(user, KnowledgeListMF.bombs)) {
            if (world.isRemote)
                user.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("knowledge.unknownUse")));
            return false;
        }
        TileEntityBombPress tile = getTile(world, x, y, z);
        if (tile != null) {
            tile.use(user);
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityBombPress();
    }

    private TileEntityBombPress getTile(World world, int x, int y, int z) {
        return (TileEntityBombPress) world.getTileEntity(x, y, z);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return Blocks.anvil.getIcon(0, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {

    }

    @Override
    public int getRenderType() {
        return bpress_RI;
    }
}