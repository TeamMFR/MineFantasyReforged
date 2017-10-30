package minefantasy.mf2.item.gadget;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.item.list.CreativeTabMF;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemSpyglass extends Item implements IScope {
    public IIcon[] icons = new IIcon[3];

    public ItemSpyglass() {
        this.setCreativeTab(CreativeTabMF.tabGadget);
        setTextureName("minefantasy2:Other/spyglass_small");
        GameRegistry.registerItem(this, "spyglass", MineFantasyII.MODID);
        this.setUnlocalizedName("spyglass");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack item) {
        return EnumAction.bow;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        if (user.isSneaking()) {
            world.playSoundEffect(user.posX, user.posY + 1.5D, user.posZ, "random.click", 1.0F, 1.5F);
            toggleMode(item);
            user.swingItem();
        } else {
            user.setItemInUse(item, getMaxItemUseDuration(item));
        }
        return item;
    }

    private void toggleMode(ItemStack item) {
        int dam = item.getItemDamage();
        item.setItemDamage(dam == 2 ? 0 : dam + 1);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        return Integer.MAX_VALUE;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon("minefantasy2:Other/spyglass_small");
        icons[1] = reg.registerIcon("minefantasy2:Other/spyglass_medium");
        icons[2] = reg.registerIcon("minefantasy2:Other/spyglass_long");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int id) {
        if (id > 2) {
            id = 2;
        }
        return icons[id];
    }

    @Override
    public float getZoom(ItemStack item) {
        int id = item.getItemDamage();
        return 0.5F + (0.25F * id);
    }
}
