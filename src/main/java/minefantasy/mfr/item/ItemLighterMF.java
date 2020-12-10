package minefantasy.mfr.item;

import minefantasy.mfr.item.ItemBaseMFR;
import minefantasy.mfr.proxy.IClientRegister;
import minefantasy.mfr.util.ModelLoaderHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.GameRegistry;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.tool.ILighter;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLighterMF extends ItemBaseMFR implements ILighter {
    private float chance;

    public ItemLighterMF(String name, float chance, int uses) {
        super(name);
        setMaxDamage(uses);
        this.chance = chance;
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabMFR.tabGadget);
    }

    // 0 for N/A -1 for fail, 1 for succeed
    public static int tryUse(ItemStack held, EntityPlayer user) {
        if (held.isEmpty())
            return 0;

        if (held.getItem() instanceof ItemFlintAndSteel) {
            return 1;
        }
        if (held.getItem() instanceof ILighter) {
            ILighter lighter = (ILighter) held.getItem();
            return (lighter.canLight() && user.getRNG().nextFloat() < lighter.getChance()) ? 1 : -1;
        }
        return 0;
    }

    @Override
    public boolean canLight() {
        return true;
    }

    @Override
    public double getChance() {
        return chance;
    }

    public boolean onItemUse(ItemStack item, EntityPlayer user, World world, BlockPos pos, EnumFacing face) {

        if (!user.canPlayerEdit(pos, face, item)) {
            return false;
        } else {
            boolean success = user.getRNG().nextFloat() < chance;
            if (world.isAirBlock(pos)) {
                world.playSound(user, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.AMBIENT, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
                if (success) {
                    world.setBlockState(pos, (IBlockState) Blocks.FIRE);
                }
            }
            if (success) {
                item.damageItem(1, user);
            }
            return true;
        }
    }
}
