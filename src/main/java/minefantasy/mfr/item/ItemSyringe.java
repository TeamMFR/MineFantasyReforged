package minefantasy.mfr.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import minefantasy.mfr.init.MineFantasySounds;
import minefantasy.mfr.init.MineFantasyTabs;
import minefantasy.mfr.init.ToolListMFR;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSyringe extends ItemBaseMFR {

    public ItemSyringe(String name ) {
        super(name);
        this.setMaxStackSize(16);
        this.setCreativeTab(MineFantasyTabs.tabGadget);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        if (slot != EntityEquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(slot, stack);
        }

        Multimap<String, AttributeModifier> map = HashMultimap.create();
        map.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0, 0));

        return map;
    }

    @Override
    public boolean hitEntity(ItemStack item, EntityLivingBase target, EntityLivingBase user) {
        World world = user.world;

        target.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1.0F, 2.0F);
        target.playSound(MineFantasySounds.BLADE_METAL, 1.0F, 1.5F);
        if (!world.isRemote) {
            apply(target, item);
        }

        if (user instanceof EntityPlayer && !((EntityPlayer) user).capabilities.isCreativeMode) {
            item.shrink(1);

            if (item.getCount() <= 0) {
                user.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ToolListMFR.SYRINGE_EMPTY));
            } else {
                if (!((EntityPlayer) user).inventory.addItemStackToInventory(new ItemStack(ToolListMFR.SYRINGE_EMPTY))) {
                    user.entityDropItem(new ItemStack(ToolListMFR.SYRINGE_EMPTY), 0F);
                }
            }
        }

        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSwingInProgress) {
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        }
        player.setActiveHand(hand);

        if (!player.capabilities.isCreativeMode) {
            stack.shrink(1);
        }

        player.playSound(SoundEvents.ENTITY_GENERIC_DRINK, 1.0F, 2.0F);
        player.playSound(MineFantasySounds.BLADE_METAL, 1.0F, 1.5F);
        if (!world.isRemote) {
            apply(player, stack);
        }

        if (!player.capabilities.isCreativeMode) {
            if (stack.getCount() <= 0) {
                return ActionResult.newResult(EnumActionResult.SUCCESS, new ItemStack(ToolListMFR.SYRINGE_EMPTY));
            }

            if (!player.inventory.addItemStackToInventory(new ItemStack(ToolListMFR.SYRINGE_EMPTY))) {
                player.entityDropItem(new ItemStack(ToolListMFR.SYRINGE_EMPTY), 0F);
            }
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }

    private void apply(EntityLivingBase target, ItemStack item) {
        List<PotionEffect> list = PotionUtils.getEffectsFromStack(item);

        for (PotionEffect potion : list) {
            target.addPotionEffect(new PotionEffect(potion));
        }
    }

    public String getItemStackDisplayName(ItemStack stack) {
        String potion = I18n.format(PotionUtils.getPotionFromItem(stack).getNamePrefixed("potion.effect."));
        return potion + " " + super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (!isInCreativeTab(tab)) {
            return;
        }
        for (PotionType potiontype : PotionType.REGISTRY) {
            if (potiontype != PotionTypes.EMPTY && potiontype != PotionTypes.MUNDANE && potiontype != PotionTypes.AWKWARD && potiontype != PotionTypes.WATER && potiontype != PotionTypes.THICK) {
                items.add(PotionUtils.addPotionToItemStack(new ItemStack(this), potiontype));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return super.hasEffect(stack) || !PotionUtils.getEffectsFromStack(stack).isEmpty();
    }
}