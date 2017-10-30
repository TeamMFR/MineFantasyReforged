package minefantasy.mf2.item.gadget;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import minefantasy.mf2.MineFantasyII;
import minefantasy.mf2.item.list.CreativeTabMF;
import minefantasy.mf2.item.list.ToolListMF;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ItemSyringe extends ItemPotion {
    private static final Map potionMap = new LinkedHashMap();
    private IIcon icon, fill;

    public ItemSyringe() {
        super();
        this.setMaxStackSize(16);
        String name = "syringe";
        this.setCreativeTab(CreativeTabMF.tabGadget);
        setTextureName("minefantasy2:Other/" + name);
        GameRegistry.registerItem(this, name, MineFantasyII.MODID);
        this.setUnlocalizedName(name);
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack item) {
        Multimap map = HashMultimap.create();
        map.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(field_111210_e, "Weapon modifier", 0, 0));

        return map;
    }

    @Override
    public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase user) {
        World world = user.worldObj;

        target.playSound("random.drink", 1.0F, 2.0F);
        target.playSound("minefantasy2:weapon.hit.blade.metal", 1.0F, 1.5F);
        if (!world.isRemote) {
            apply(target, item);
        }

        if (user instanceof EntityPlayer && !((EntityPlayer) user).capabilities.isCreativeMode) {
            --item.stackSize;

            if (item.stackSize <= 0) {
                user.setCurrentItemOrArmor(0, new ItemStack(ToolListMF.syringe_empty));
            } else {
                if (!((EntityPlayer) user).inventory.addItemStackToInventory(new ItemStack(ToolListMF.syringe_empty))) {
                    user.entityDropItem(new ItemStack(ToolListMF.syringe_empty), 0F);
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer user) {
        if (user.isSwingInProgress) {
            return item;
        }
        user.swingItem();

        if (!user.capabilities.isCreativeMode) {
            --item.stackSize;
        }

        user.playSound("random.drink", 1.0F, 2.0F);
        user.playSound("minefantasy2:weapon.hit.blade.metal", 1.0F, 1.5F);
        if (!world.isRemote) {
            apply(user, item);
        }

        if (!user.capabilities.isCreativeMode) {
            if (item.stackSize <= 0) {
                return new ItemStack(ToolListMF.syringe_empty);
            }

            if (!user.inventory.addItemStackToInventory(new ItemStack(ToolListMF.syringe_empty))) {
                user.entityDropItem(new ItemStack(ToolListMF.syringe_empty), 0F);
            }
        }

        return item;
    }

    private void apply(EntityLivingBase target, ItemStack item) {
        List list = this.getEffects(item);

        if (list != null) {
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                PotionEffect potioneffect = (PotionEffect) iterator.next();
                target.addPotionEffect(new PotionEffect(potioneffect));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.icon = reg.registerIcon(this.getIconString());
        this.fill = reg.registerIcon(this.getIconString() + "_" + "fill");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_) {
        return icon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_) {
        return p_77618_2_ == 0 ? fill : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
    }

    @Override
    public String getItemStackDisplayName(ItemStack item) {
        if (item.getItemDamage() == 0) {
            return StatCollector.translateToLocal("item.syringe_empty.name");
        } else {
            String s = "";

            List list = Items.potionitem.getEffects(item);
            String s1;

            if (list != null && !list.isEmpty()) {
                s1 = ((PotionEffect) list.get(0)).getEffectName();
                s1 = s1 + ".postfix";
                return s + StatCollector.translateToLocal(s1).trim();
            } else {
                s1 = PotionHelper.func_77905_c(item.getItemDamage());
                return StatCollector.translateToLocal(s1).trim() + " " + super.getItemStackDisplayName(item);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        int sub;

        if (potionMap.isEmpty()) {
            for (int i = 0; i <= 15; ++i) {
                for (sub = 0; sub <= 0; ++sub) {
                    int k;

                    if (sub == 0) {
                        k = i | 8192;
                    } else {
                        k = i | 16384;
                    }

                    for (int l = 0; l <= 2; ++l) {
                        int i1 = k;

                        if (l != 0) {
                            if (l == 1) {
                                i1 = k | 32;
                            } else if (l == 2) {
                                i1 = k | 64;
                            }
                        }

                        List list1 = PotionHelper.getPotionEffects(i1, false);

                        if (list1 != null && !list1.isEmpty() && !ItemPotion.isSplash(i1)) {
                            potionMap.put(list1, Integer.valueOf(i1));
                        }
                    }
                }
            }
        }

        Iterator iterator = potionMap.values().iterator();

        while (iterator.hasNext()) {
            sub = ((Integer) iterator.next()).intValue();
            list.add(new ItemStack(item, 1, sub));
        }
    }
}