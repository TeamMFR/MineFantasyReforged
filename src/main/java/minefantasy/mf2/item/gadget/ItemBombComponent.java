package minefantasy.mf2.item.gadget;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.crafting.ISalvageDrop;
import minefantasy.mf2.api.crafting.engineer.IBombComponent;
import minefantasy.mf2.item.ItemComponentMF;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import java.util.HashMap;
import java.util.List;

public class ItemBombComponent extends ItemComponentMF implements IBombComponent, ISalvageDrop {
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
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean fullInfo) {
        list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("bomb.component.name"));
        list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("bomb.component." + type));
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
