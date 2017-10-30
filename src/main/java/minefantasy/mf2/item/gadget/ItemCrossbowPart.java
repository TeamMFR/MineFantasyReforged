package minefantasy.mf2.item.gadget;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.engineer.ICrossbowPart;
import minefantasy.mf2.item.ItemComponentMF;
import minefantasy.mf2.item.list.ComponentListMF;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemCrossbowPart extends ItemComponentMF implements ICrossbowPart {
    public IIcon modelIcon;
    private int tier;
    private String type, partname;
    private int capacity = 0, durability = 50;
    private float power, spread, recoil, speed, bash, zoom;
    private boolean isHandle = false;

    public ItemCrossbowPart(String name, String type) {
        this(name, type, ICrossbowPart.components.size(), name);
    }

    public ItemCrossbowPart(String name, String type, int tier) {
        this(name, type, tier, name);
    }

    public ItemCrossbowPart(String name, String type, int tier, String model) {
        super(name, 0);
        this.setFull3D();
        this.type = type;
        this.tier = tier;
        this.partname = model;
        MineFantasyAPI.registerCrossbowPart(this);
    }

    public static ICrossbowPart getPart(String type, int id) {
        return components.get(type + id);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean fullInfo) {
        list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("crossbow.component.name"));
        list.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("crossbow.component." + type));
        list.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal(getUnlocalizedName() + ".desc"));
    }

    @Override
    public String getComponentType() {
        return type;
    }

    @Override
    public int getID() {
        return tier;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon() {
        return modelIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        super.registerIcons(reg);
        modelIcon = reg.registerIcon("minefantasy2:gun/" + type + "/" + partname);
    }

    @Override
    public String getUnlocalisedName() {
        if (this == ComponentListMF.cross_arms_basic) {
            return null;
        }
        return "crosspart." + type + "." + partname;
    }

    public ItemCrossbowPart addPower(float power) {
        this.power = power;
        return this;
    }

    public ItemCrossbowPart setScope(float zoom) {
        this.zoom = zoom;
        return this;
    }

    public ItemCrossbowPart addSpread(float spread) {
        this.spread = spread;
        return this;
    }

    public ItemCrossbowPart addRecoil(float recoil) {
        this.recoil = recoil;
        return this;
    }

    public ItemCrossbowPart addSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public ItemCrossbowPart addBash(float bash) {
        this.bash = bash;
        return this;
    }

    public ItemCrossbowPart addCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public ItemCrossbowPart addDurability(int durability) {
        this.durability = durability;
        return this;
    }

    public ItemCrossbowPart setHandCrossbow(boolean flag) {
        this.isHandle = flag;
        return this;
    }

    @Override
    public float getModifier(String type) {
        if (type.equalsIgnoreCase("power"))
            return power;
        if (type.equalsIgnoreCase("spread"))
            return spread;
        if (type.equalsIgnoreCase("recoil"))
            return recoil;
        if (type.equalsIgnoreCase("speed"))
            return speed;
        if (type.equalsIgnoreCase("capacity"))
            return capacity;
        if (type.equalsIgnoreCase("bash"))
            return bash;
        if (type.equalsIgnoreCase("zoom"))
            return zoom;
        if (type.equalsIgnoreCase("durability"))
            return durability;
        return 0F;
    }

    @Override
    public boolean makesSmallWeapon() {
        return isHandle;
    }
}
