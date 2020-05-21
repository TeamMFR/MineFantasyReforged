package minefantasy.mfr.item.gadget;

import minefantasy.mfr.api.crafting.ISalvageDrop;
import minefantasy.mfr.api.crafting.engineer.IBombComponent;
import minefantasy.mfr.item.ItemComponentMFR;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.List;

public class ItemBombComponent extends ItemComponentMFR implements IBombComponent, ISalvageDrop  {
    private static HashMap<String, Item> components = new HashMap<String, Item>();

    private byte tier;
    private String type;

    public ItemBombComponent(String name, String type, int tier) {
        this(name, 0, type, tier);
    }

    public ItemBombComponent(String name, int rarity, String type, int tier) {
        super(name, rarity);
        this.type = type;
        this.tier = (byte) tier;
        components.put(type + tier, this);
    }

    public static Item getBombComponent(String name, int tier) {
        return components.get(name + tier);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World world, List tooltip, ITooltipFlag flag) {
        tooltip.add(TextFormatting.GOLD + I18n.translateToLocal("bomb.component.name"));
        tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("bomb.component." + type));
    }

    @Override
    public String getComponentType() {
        return type;
    }

    @Override
    public byte getTier() {
        return tier;
    }

    @Override
    public boolean canSalvage(EntityPlayer user, ItemStack item) {
        if (getContainerItem() != null) {
            return false;
        }
        return true;
    }
}
