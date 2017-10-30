package minefantasy.mf2.item.gadget;

import minefantasy.mf2.api.crafting.ISpecialSalvage;
import minefantasy.mf2.entity.EntityArrowMF;
import minefantasy.mf2.item.archery.ArrowType;
import minefantasy.mf2.item.archery.ItemArrowMF;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.material.BaseMaterialMF;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemExplodingBolt extends ItemArrowMF implements ISpecialSalvage {

    public ItemExplodingBolt() {
        super("exploding_bolt", 1, BaseMaterialMF.iron.getToolConversion(), ArrowType.EXPLOSIVEBOLT);
        setCreativeTab(CreativeTabMF.tabGadget);
        setAmmoType("bolt");
        setMaxStackSize(20);
    }

    public static ItemStack createBombArrow(byte powder, byte filling) {
        ItemStack arrow = new ItemStack(ToolListMF.exploding_bolt);

        ItemBomb.setFilling(arrow, filling);
        ItemBomb.setPowder(arrow, powder);

        return arrow;
    }

    @Override
    public EntityArrowMF getFiredArrow(EntityArrowMF instance, ItemStack arrow) {
        instance = super.getFiredArrow(instance, arrow);
        instance.canBePickedUp = 0;
        return instance.setBombStats(ItemBomb.getPowder(arrow), ItemBomb.getFilling(arrow));
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean fullInfo) {
        super.addInformation(item, user, list, fullInfo);

        EnumExplosiveType fill = EnumExplosiveType.getType(ItemBomb.getFilling(item));
        EnumPowderType powder = EnumPowderType.getType(ItemBomb.getPowder(item));

        int damage = (int) (fill.damage * powder.damageModifier * 0.5F);
        float range = fill.range * powder.rangeModifier * 0.5F;

        list.add(StatCollector.translateToLocal(ToolListMF.bomb_custom.getUnlocalizedName(item) + ".name"));
        list.add(StatCollector.translateToLocal("bomb.powder." + powder.name + ".name"));
        list.add("");
        list.add(StatCollector.translateToLocal("bomb.damage.name") + ": " + damage);
        list.add(StatCollector.translateToLocalFormatted("bomb.range.metric.name", range));
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (byte pdr = 0; pdr < 2; pdr++) {
            for (byte fill = 0; fill < 3; fill++) {
                list.add(createBombArrow(pdr, fill));
            }
        }
    }

    @Override
    public Object[] getSalvage(ItemStack item) {
        return new Object[]{ComponentListMF.bomb_casing_bolt,
                ItemBombComponent.getBombComponent("powder", ItemBomb.getPowder(item)),
                ItemBombComponent.getBombComponent("filling", ItemBomb.getFilling(item)),};
    }
}
