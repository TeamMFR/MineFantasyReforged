package minefantasy.mf2.item.food;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

public class ItemMultiFood extends ItemFoodMF {
    private IIcon[] icons;
    private int bites = 6;

    public ItemMultiFood(String name, int bites, int hunger, float saturation, int rarity) {
        this(name, bites, hunger, saturation, false, rarity);
    }

    public ItemMultiFood(String name, int bites, int hunger, float saturation, boolean meat, int rarity) {
        super(name, hunger, saturation, meat, rarity);
        setMaxStackSize(1);
        this.bites = bites;
        setMaxDamage(bites - 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage) {
        int index = MathHelper.clamp_int(damage, 0, bites - 1);
        return this.icons[index];
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public boolean isItemTool(ItemStack item) {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.icons = new IIcon[bites];

        for (int i = 0; i < bites; ++i) {
            this.icons[i] = register.registerIcon(this.getIconString() + "_" + i);
        }
    }
}
