package minefantasy.mf2.item.gadget;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.archery.IAmmo;
import minefantasy.mf2.api.crafting.ISpecialSalvage;
import minefantasy.mf2.entity.EntityMine;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.mechanics.BombDispenser;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemMine extends Item implements ISpecialSalvage, IAmmo {
    private static final String powderNBT = "MineFantasy_PowderType";
    private static final String fuseNBT = "MineFantasy_FuseType";
    private static final String fillingNBT = "MineFantasy_ExplosiveType";
    private static final String casingNBT = "MineFantasy_CaseType";
    private IIcon[] mines = new IIcon[4];

    public ItemMine(String name) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabMF.tabGadget);
        setTextureName("minefantasy2:Other/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, new BombDispenser());
    }

    public static void setFuse(ItemStack item, byte fuse) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setByte(fuseNBT, fuse);
    }

    public static byte getFuse(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        if (nbt.hasKey(fuseNBT)) {
            return nbt.getByte(fuseNBT);
        }
        return (byte) 0;
    }

    public static void setPowder(ItemStack item, byte powder) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setByte(powderNBT, powder);
    }

    public static byte getPowder(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        if (nbt.hasKey(powderNBT)) {
            return nbt.getByte(powderNBT);
        }
        return (byte) 0;
    }

    /**
     * 0 = Basic 1 = Shrapnel 2 = Fire
     */
    public static void setFilling(ItemStack item, byte filling) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setByte(fillingNBT, filling);
    }

    public static byte getFilling(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        if (nbt.hasKey(fillingNBT)) {
            return nbt.getByte(fillingNBT);
        }
        return (byte) 0;
    }

    /**
     * 0 = Ceramic 1 = Iron
     */
    public static void setCasing(ItemStack item, byte casing) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setByte(casingNBT, casing);
    }

    public static byte getCasing(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        if (nbt.hasKey(casingNBT)) {
            return nbt.getByte(casingNBT);
        }
        return (byte) 0;
    }

    public static NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound())
            item.setTagCompound(new NBTTagCompound());
        return item.getTagCompound();
    }

    @Override
    public EnumAction getItemUseAction(ItemStack item) {
        return EnumAction.block;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        if (!user.isSwingInProgress) {
            world.playSoundEffect(user.posX, user.posY + 1.5D, user.posZ, "fire.ignite", 1.0F,
                    itemRand.nextFloat() * 0.4F + 0.8F);
            user.setItemInUse(item, getMaxItemUseDuration(item));
        }
        return item;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        return 35;
    }

    @Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer user) {
        world.playSoundEffect(user.posX, user.posY + 1.5, user.posZ, "random.click", 1.0F, 1.0F);
        user.swingItem();
        if (!user.capabilities.isCreativeMode) {
            --item.stackSize;
        }

        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityMine(world, user).setType(getFilling(item), getCasing(item),
                    getFuse(item), getPowder(item)));
        }

        return item;
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean fullInfo) {
        super.addInformation(item, user, list, fullInfo);

        EnumExplosiveType fill = EnumExplosiveType.getType(getFilling(item));
        EnumCasingType casing = EnumCasingType.getType(getCasing(item));
        EnumFuseType fuse = EnumFuseType.getType(getFuse(item));
        EnumPowderType powder = EnumPowderType.getType(getPowder(item));

        int damage = (int) (fill.damage * casing.damageModifier * powder.damageModifier);
        float range = fill.range * casing.rangeModifier * powder.rangeModifier;
        float fusetime = fuse.time / 20F;

        list.add(StatCollector.translateToLocal("bomb.case." + casing.name + ".name"));
        list.add(StatCollector.translateToLocal("bomb.powder." + powder.name + ".name"));
        list.add(StatCollector.translateToLocal("bomb.fuse." + fuse.name + ".name"));
        list.add("");
        list.add(StatCollector.translateToLocalFormatted("bomb.fusetime.name", fusetime));
        list.add(StatCollector.translateToLocal("bomb.damage.name") + ": " + damage);
        list.add(StatCollector.translateToLocalFormatted("bomb.range.metric.name", range));
    }

    @Override
    public String getUnlocalizedName(ItemStack item) {
        EnumExplosiveType type = EnumExplosiveType.getType(getFilling(item));
        return "item.mine_" + type.name;
    }

    public ItemStack createMine(byte casing, byte filling, byte fuse, byte powder, int stackSize) {
        return ItemBomb.createExplosive(this, casing, filling, fuse, powder, stackSize);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(createMine((byte) 0, (byte) 0, (byte) 0, (byte) 0, 1));
        list.add(createMine((byte) 0, (byte) 1, (byte) 0, (byte) 0, 1));
        list.add(createMine((byte) 0, (byte) 2, (byte) 0, (byte) 0, 1));

        list.add(createMine((byte) 1, (byte) 0, (byte) 0, (byte) 0, 1));
        list.add(createMine((byte) 1, (byte) 1, (byte) 0, (byte) 0, 1));
        list.add(createMine((byte) 1, (byte) 2, (byte) 0, (byte) 0, 1));

        list.add(createMine((byte) 2, (byte) 0, (byte) 0, (byte) 0, 1));
        list.add(createMine((byte) 2, (byte) 1, (byte) 0, (byte) 0, 1));
        list.add(createMine((byte) 2, (byte) 2, (byte) 0, (byte) 0, 1));

        list.add(createMine((byte) 3, (byte) 0, (byte) 0, (byte) 0, 1));
        list.add(createMine((byte) 3, (byte) 1, (byte) 0, (byte) 0, 1));
        list.add(createMine((byte) 3, (byte) 2, (byte) 0, (byte) 0, 1));
    }

    // TODO Icons
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(byte type) {
        return mines[type];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.itemIcon = mines[0] = reg.registerIcon("minefantasy2:Other/mine_ceramic");
        mines[1] = reg.registerIcon("minefantasy2:Other/mine_iron");
        mines[2] = reg.registerIcon("minefantasy2:Other/mine_obsidian");
        mines[3] = reg.registerIcon("minefantasy2:Other/mine_crystal");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack item) {
        int type = getCasing(item);
        return mines[type];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack item, int layer) {
        if (layer > 0) {
            int type = getFilling(item);
            if (type != 0) {
                return ToolListMF.bomb_custom.icons[type - 1];
            }
        }
        return getIconIndex(item);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack item) {
        if (getFilling(item) >= 2 || getCasing(item) >= 2) {
            return EnumRarity.uncommon;
        }
        return EnumRarity.common;
    }

    @Override
    public Object[] getSalvage(ItemStack item) {
        return new Object[]{ItemBombComponent.getBombComponent("minecase", getCasing(item)),
                ItemBombComponent.getBombComponent("fuse", getFuse(item)),
                ItemBombComponent.getBombComponent("powder", getPowder(item)),
                ItemBombComponent.getBombComponent("filling", getFilling(item)),};
    }

    @Override
    public String getAmmoType(ItemStack arrow) {
        return "mine";
    }
}