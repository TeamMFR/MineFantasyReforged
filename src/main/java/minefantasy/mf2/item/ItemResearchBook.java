package minefantasy.mf2.item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.knowledge.ResearchLogic;
import minefantasy.mf2.api.rpg.RPGElements;
import minefantasy.mf2.item.list.CreativeTabMF;
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
        setCreativeTab(CreativeTabMF.tabGadget);
        GameRegistry.registerItem(this, "MF_ResearchBook", MineFantasyII.MODID);
        this.setUnlocalizedName("infobook");
        this.setTextureName("minefantasy2:Other/research_book");
        setContainerItem(this);
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        return EnumRarity.uncommon;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed.
     * Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        if (!world.isRemote) {
            ResearchLogic.syncData(user);
            RPGElements.syncAll(user);
        }
        user.openGui(MineFantasyII.instance, 1, world, 0, -1, 0);
        return item;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean fullInfo) {
        super.addInformation(item, user, list, fullInfo);
    }
}
