package minefantasy.mfr.item.gadget;

import minefantasy.mfr.api.crafting.ISpecialSalvage;
import minefantasy.mfr.entity.EntityArrowMFR;
import minefantasy.mfr.item.archery.ArrowType;
import minefantasy.mfr.item.archery.ItemArrowMFR;
import minefantasy.mfr.init.ComponentListMFR;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.ToolListMFR;
import minefantasy.mfr.material.BaseMaterialMFR;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.util.List;

public class ItemExplodingArrow extends ItemArrowMFR implements ISpecialSalvage {

    public ItemExplodingArrow() {
        super("exploding_arrow", 1, BaseMaterialMFR.iron.getToolConversion(), ArrowType.EXPLOSIVE);
        setCreativeTab(CreativeTabMFR.tabGadget);
        setMaxStackSize(16);
    }

    public static ItemStack createBombArrow(byte powder, byte filling) {
        return createBombArrow(ToolListMFR.exploding_arrow, powder, filling);
    }

    public static ItemStack createBombArrow(Item design, byte powder, byte filling) {
        ItemStack arrow = new ItemStack(design);

        ItemBomb.setFilling(arrow, filling);
        ItemBomb.setPowder(arrow, powder);

        return arrow;
    }

    @Override
    public EntityArrowMFR getFiredArrow(EntityArrowMFR instance, ItemStack arrow) {
        instance = super.getFiredArrow(instance, arrow);
        instance.canBePickedUp = 0;
        return instance.setBombStats(ItemBomb.getPowder(arrow), ItemBomb.getFilling(arrow));
    }

    @Override
    public void addInformation(ItemStack item, World world, List list, ITooltipFlag flag) {
        super.addInformation(item, world, list, flag);

        if (item.hasTagCompound() && item.getTagCompound().hasKey("stickyBomb")) {
            list.add(TextFormatting.GREEN + I18n.translateToLocal("bomb.case.sticky")
                    + TextFormatting.GRAY);
        }
        EnumExplosiveType fill = EnumExplosiveType.getType(ItemBomb.getFilling(item));
        EnumPowderType powder = EnumPowderType.getType(ItemBomb.getPowder(item));

        int damage = (int) (fill.damage * powder.damageModifier * 0.5F);
        float range = fill.range * powder.rangeModifier * 0.5F;

        list.add(I18n.translateToLocal(ToolListMFR.bomb_custom.getUnlocalizedName(item) + ".name"));
        list.add(I18n.translateToLocal("bomb.powder." + powder.name + ".name"));
        list.add("");
        list.add(I18n.translateToLocal("bomb.damage.name") + ": " + damage);
        list.add(I18n.translateToLocalFormatted("bomb.range.metric.name", range));
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (byte pdr = 0; pdr < 2; pdr++) {
            for (byte fill = 0; fill < 3; fill++) {
                items.add(createBombArrow(pdr, fill));
            }
        }
    }

    @Override
    public Object[] getSalvage(ItemStack item) {
        return new Object[]{ComponentListMFR.bomb_casing_arrow,
                ItemBombComponent.getBombComponent("powder", ItemBomb.getPowder(item)),
                ItemBombComponent.getBombComponent("filling", ItemBomb.getFilling(item)),};
    }
}
