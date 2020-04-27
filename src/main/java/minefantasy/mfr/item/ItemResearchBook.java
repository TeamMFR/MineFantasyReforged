package minefantasy.mfr.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.api.knowledge.ResearchLogic;
import minefantasy.mfr.api.rpg.RPGElements;
import minefantasy.mfr.init.CreativeTabMFR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemResearchBook extends Item {
    public ItemResearchBook() {
        super();
        setMaxStackSize(1);
        setCreativeTab(CreativeTabMFR.tabGadget);
        setRegistryName("MF_ResearchBook");
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + "infobook");
        GameRegistry.findRegistry(Item.class).register(this);
        setContainerItem(this);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return EnumRarity.UNCOMMON;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed.
     * Args: itemStack, world, entityPlayer
     * @return
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick( World world, EntityPlayer user, EnumHand hand) {
        if (!world.isRemote) {
            ResearchLogic.syncData(user);
            RPGElements.syncAll(user);
        }
        user.openGui(MineFantasyReborn.instance, 1, world, 0, -1, 0);
        return ActionResult.newResult(EnumActionResult.PASS, user.getHeldItem(hand));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag fullInfo) {
        super.addInformation(item, world, list, fullInfo);
    }
}
