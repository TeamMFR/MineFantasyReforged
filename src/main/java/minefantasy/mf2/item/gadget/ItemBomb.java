package minefantasy.mf2.item.gadget;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.api.archery.IAmmo;
import minefantasy.mf2.api.crafting.ISpecialSalvage;
import minefantasy.mf2.entity.EntityBomb;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import minefantasy.mf2.mechanics.BombDispenser;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemBomb extends Item implements ISpecialSalvage, IAmmo {
    private static final String powderNBT = "MineFantasy_PowderType";
    private static final String fuseNBT = "MineFantasy_FuseType";
    private static final String fillingNBT = "MineFantasy_ExplosiveType";
    private static final String casingNBT = "MineFantasy_CaseType";
    public IIcon[] icons = new IIcon[2];
    private IIcon[] bombs = new IIcon[4];

    public ItemBomb(String name) {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabMF.tabGadget);
        setTextureName("minefantasy2:Other/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, new BombDispenser());
    }

    public static void setSticky(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setBoolean("stickyBomb", true);
    }

    public static void setFuse(ItemStack item, byte fuse) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setByte(fuseNBT, fuse);
    }

    public static byte getFuse(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        if (item.getItem() instanceof ItemBomb) {
            return ((ItemBomb) item.getItem()).getItemFuse(nbt.getByte(fuseNBT));
        }
        return nbt.getByte(fuseNBT);
    }

    public static void setPowder(ItemStack item, byte powder) {
        NBTTagCompound nbt = getNBT(item);
        nbt.setByte(powderNBT, powder);
    }

    public static byte getPowder(ItemStack item) {
        NBTTagCompound nbt = getNBT(item);
        if (item.getItem() instanceof ItemBomb) {
            return ((ItemBomb) item.getItem()).getItemPowder(nbt.getByte(powderNBT));
        }
        return nbt.getByte(powderNBT);
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
        if (item.getItem() instanceof ItemBomb) {
            return ((ItemBomb) item.getItem()).getItemFilling(nbt.getByte(fillingNBT));
        }
        return nbt.getByte(fillingNBT);
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
        if (item.getItem() instanceof ItemBomb) {
            return ((ItemBomb) item.getItem()).getItemCasing(nbt.getByte(casingNBT));
        }
        return nbt.getByte(casingNBT);
    }

    public static NBTTagCompound getNBT(ItemStack item) {
        if (!item.hasTagCompound())
            item.setTagCompound(new NBTTagCompound());
        return item.getTagCompound();
    }

    public static ItemStack createExplosive(Item item, byte casing, byte filling, byte fuse, byte powder, int stackSize,
                                            boolean sticky) {
        ItemStack bomb = new ItemStack(item, stackSize);
        setFilling(bomb, filling);
        setCasing(bomb, casing);
        setFuse(bomb, fuse);
        setPowder(bomb, powder);
        if (sticky) {
            setSticky(bomb);
        }
        return bomb;
    }

    public static ItemStack createExplosive(Item item, byte casing, byte filling, byte fuse, byte powder,
                                            int stackSize) {
        return createExplosive(item, casing, filling, fuse, powder, stackSize, false);
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
            world.playSoundEffect(user.posX, user.posY + 1.5, user.posZ, "game.tnt.primed", 1.0F, 1.0F);
            user.setItemInUse(item, getMaxItemUseDuration(item));
        }
        return item;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack item) {
        return 15;
    }

    @Override
    public ItemStack onEaten(ItemStack item, World world, EntityPlayer user) {
        user.swingItem();
        if (!user.capabilities.isCreativeMode) {
            --item.stackSize;
        }

        if (!world.isRemote) {
            EntityBomb bomb = new EntityBomb(world, user).setType(getFilling(item), getCasing(item), getFuse(item),
                    getPowder(item));
            world.spawnEntityInWorld(bomb);
            if (item.hasTagCompound() && item.getTagCompound().hasKey("stickyBomb")) {
                bomb.getEntityData().setBoolean("stickyBomb", true);
            }
        }
        return item;
    }

    @Override
    public void addInformation(ItemStack item, EntityPlayer user, List list, boolean fullInfo) {
        super.addInformation(item, user, list, fullInfo);

        if (item.hasTagCompound() && item.getTagCompound().hasKey("stickyBomb")) {
            list.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("bomb.case.sticky")
                    + EnumChatFormatting.GRAY);
        }
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
        return "item.bomb_" + type.name;
    }

    public ItemStack createBomb(byte casing, byte filling, byte fuse, byte powder, int stackSize) {
        return ItemBomb.createExplosive(this, casing, filling, fuse, powder, stackSize);
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        list.add(createBomb((byte) 0, (byte) 0, (byte) 0, (byte) 0, 1));
        list.add(createBomb((byte) 0, (byte) 1, (byte) 0, (byte) 0, 1));
        list.add(createBomb((byte) 0, (byte) 2, (byte) 0, (byte) 0, 1));

        list.add(createBomb((byte) 1, (byte) 0, (byte) 0, (byte) 0, 1));
        list.add(createBomb((byte) 1, (byte) 1, (byte) 0, (byte) 0, 1));
        list.add(createBomb((byte) 1, (byte) 2, (byte) 0, (byte) 0, 1));

        list.add(createBomb((byte) 2, (byte) 0, (byte) 0, (byte) 0, 1));
        list.add(createBomb((byte) 2, (byte) 1, (byte) 0, (byte) 0, 1));
        list.add(createBomb((byte) 2, (byte) 2, (byte) 0, (byte) 0, 1));

        list.add(createBomb((byte) 3, (byte) 0, (byte) 0, (byte) 0, 1));
        list.add(createBomb((byte) 3, (byte) 1, (byte) 0, (byte) 0, 1));
        list.add(createBomb((byte) 3, (byte) 2, (byte) 0, (byte) 0, 1));
    }

    // TODO Icons
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(byte type) {
        if (type < 0) {
            return ToolListMF.bomb_crude.getIcon(type);
        }
        return bombs[type];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        icons[0] = reg.registerIcon("minefantasy2:Other/bomb_icon_shrapnel");
        icons[1] = reg.registerIcon("minefantasy2:Other/bomb_icon_fire");

        this.itemIcon = bombs[0] = reg.registerIcon("minefantasy2:Other/bomb_ceramic");
        bombs[1] = reg.registerIcon("minefantasy2:Other/bomb_iron");
        bombs[2] = reg.registerIcon("minefantasy2:Other/bomb_obsidian");
        bombs[3] = reg.registerIcon("minefantasy2:Other/bomb_crystal");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack item) {
        int type = getCasing(item);
        return bombs[type];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderPasses(int metadata) {
        return 3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack item, int layer) {
        boolean sticky = item.hasTagCompound() && item.getTagCompound().hasKey("stickyBomb");
        if (layer == 0 && sticky) {
            return Items.slime_ball.getIconFromDamage(0);
        }
        if (layer > (sticky ? 1 : 0)) {
            int type = getFilling(item);
            if (type != 0) {
                return icons[type - 1];
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
        return new Object[]{ItemBombComponent.getBombComponent("bombcase", getCasing(item)),
                ItemBombComponent.getBombComponent("fuse", getFuse(item)),
                ItemBombComponent.getBombComponent("powder", getPowder(item)),
                ItemBombComponent.getBombComponent("filling", getFilling(item)),};
    }

    public byte getItemFuse(byte value) {
        return value;
    }

    public byte getItemFilling(byte value) {
        return value;
    }

    public byte getItemCasing(byte value) {
        return value;
    }

    public byte getItemPowder(byte value) {
        return value;
    }

    @Override
    public String getAmmoType(ItemStack arrow) {
        return "mine";
    }
}