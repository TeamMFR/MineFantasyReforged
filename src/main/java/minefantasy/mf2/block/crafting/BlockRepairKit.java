package minefantasy.mf2.block.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.helpers.CustomToolHelper;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRepairKit extends Block {
    protected float repairLevel;
    protected float successRate;
    protected float breakChance;
    protected IIcon top, side, bottom;
    protected boolean isOrnate = false;
    protected float repairLevelEnchant = 0.0F;
    private String type;
    private Random rand = new Random();

    public BlockRepairKit(String name, float repairLevel, float rate, float breakChance) {
        super(Material.cloth);

        this.repairLevel = repairLevel;
        this.successRate = rate;
        this.breakChance = breakChance;
        this.type = name;
        setBlockBounds(1F / 16F, 0F, 1F / 16F, 15F / 16F, 6F / 16F, 15F / 16F);

        this.setBlockTextureName("minefantasy2:processor/" + "repair_" + name + "+top");
        name = "repair_" + name;
        GameRegistry.registerBlock(this, ItemBlockRepairKit.class, name);
        setBlockName(name);
        this.setStepSound(Block.soundTypeCloth);
        this.setHardness(1F);
        this.setResistance(0F);
        this.setLightOpacity(0);
        this.setCreativeTab(CreativeTabMF.tabGadget);
    }

    public BlockRepairKit setOrnate(float enc) {
        repairLevelEnchant = enc;
        isOrnate = true;
        return this;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.top : (side == 0 ? this.bottom : this.side);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.side = reg.registerIcon("minefantasy2:processor/repair_" + type + "_side");
        this.top = reg.registerIcon("minefantasy2:processor/repair_" + type + "_top");
        this.bottom = reg.registerIcon("minefantasy2:processor/repair_" + type + "_base");
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer user, int side, float xOffset,
                                    float yOffset, float zOffset) {
        if (world.isRemote) {
            return true;
        }
        ItemStack held = user.getHeldItem();
        // held.getItem().isRepairable() Was used but new MF tools disable this to avoid
        // vanilla repairs
        if (held != null && canRepair(held) && (!held.isItemEnchanted() || isOrnate)) {
            if (rand.nextFloat() < successRate) {
                boolean broken = rand.nextFloat() < breakChance;

                float lvl = held.isItemEnchanted() ? repairLevelEnchant : repairLevel;
                int repairAmount = (int) (held.getMaxDamage() * lvl);
                held.setItemDamage(Math.max(0, held.getItemDamage() - repairAmount));
                world.playAuxSFX(broken ? 1020 : 1021, x, y, z, 0);

                if (broken) {
                    world.playSoundEffect(x + 0.5D, y + 0.3D, z + 0.5D, "random.break", 1.0F, 1.0F);
                    world.setBlockToAir(x, y, z);
                }
                return true;
            } else {
                world.playSoundEffect(x + 0.5D, y + 0.3D, z + 0.5D, "step.cloth", 0.5F, 0.5F);
            }
            return true;
        }
        return false;
    }

    private boolean canRepair(ItemStack held) {
        if (held == null)
            return false;
        if (held.getItem().isDamageable() && CustomToolHelper.getCustomPrimaryMaterial(held) != null)// Custom Tool
        {
            return held.isItemDamaged();
        }
        return held.getItem().isRepairable();
    }
}
