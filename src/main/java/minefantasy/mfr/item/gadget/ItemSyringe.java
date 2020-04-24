package minefantasy.mfr.item.gadget;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import minefantasy.mfr.MineFantasyReborn;
import minefantasy.mfr.init.CreativeTabMFR;
import minefantasy.mfr.init.SoundsMFR;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemSyringe extends ItemPotion {
    private static final Map potionMap = new LinkedHashMap();

    public ItemSyringe() {
        super();
        this.setMaxStackSize(16);
        String name = "syringe";
        setRegistryName(name);
        setUnlocalizedName(MineFantasyReborn.MOD_ID + "." + name);
        GameRegistry.findRegistry(Item.class).register(this);
        this.setCreativeTab(CreativeTabMFR.tabGadget);
        this.setUnlocalizedName(name);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap map = HashMultimap.create();
        map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
                new AttributeModifier(UUID.fromString(slot.getName()), "Weapon modifier", 0, 0));

        return map;
    }

    @Override
    public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase user) {
        World world = user.world;

        target.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1.0F, 2.0F);
        target.playSound(SoundsMFR.BLADE_METAL, 1.0F, 1.5F);
        if (!world.isRemote) {
            apply(target, item);
        }

        if (user instanceof EntityPlayer && !((EntityPlayer) user).capabilities.isCreativeMode) {
            item.shrink(1);

            if (item.getCount() <= 0) {
                user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ToolListMFR.syringe_empty));
            } else {
                if (!((EntityPlayer) user).inventory.addItemStackToInventory(new ItemStack(ToolListMFR.syringe_empty))) {
                    user.entityDropItem(new ItemStack(ToolListMFR.syringe_empty), 0F);
                }
            }
        }

        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer user, EnumHand hand) {
        ItemStack item = user.getHeldItem(hand);
        if (user.isSwingInProgress) {
            return ActionResult.newResult(EnumActionResult.PASS, item);
        }
        user.setActiveHand(user.swingingHand);

        if (!user.capabilities.isCreativeMode) {
            item.shrink(1);
        }

        user.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1.0F, 2.0F);
        user.playSound(SoundsMFR.BLADE_METAL, 1.0F, 1.5F);
        if (!world.isRemote) {
            apply(user, item);
        }

        if (!user.capabilities.isCreativeMode) {
            if (item.getCount() <= 0) {
                return ActionResult.newResult(EnumActionResult.PASS,new ItemStack(ToolListMFR.syringe_empty));
            }

            if (!user.inventory.addItemStackToInventory(new ItemStack(ToolListMFR.syringe_empty))) {
                user.entityDropItem(new ItemStack(ToolListMFR.syringe_empty), 0F);
            }
        }

        return ActionResult.newResult(EnumActionResult.PASS, item);
    }

    private void apply(EntityLivingBase target, ItemStack item) {
        List list = PotionUtils.getEffectsFromStack(item);

        if (list != null) {
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                PotionEffect potioneffect = (PotionEffect) iterator.next();
                target.addPotionEffect(new PotionEffect(potioneffect));
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack item) {
        if (item.getItemDamage() == 0) {
            return I18n.translateToLocal("item.syringe_empty.name");
        } else {
            String s = "";

            List list = PotionUtils.getEffectsFromStack(item);
            String s1;

            if (list != null && !list.isEmpty()) {
                s1 = ((PotionEffect) list.get(0)).getEffectName();
                s1 = s1 + ".postfix";
                return s + I18n.translateToLocal(s1).trim();
            } else {
                s1 = PotionUtils.getPotionFromItem(item).toString();
                return I18n.translateToLocal(s1).trim() + " " + super.getItemStackDisplayName(item);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
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
                    }
                }
            }
        }
    }
}